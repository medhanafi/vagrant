package net.siinergy.dsc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.siinergy.dsc.domain.Cpe;
import net.siinergy.dsc.domain.Cve;
import net.siinergy.dsc.domain.Product;
import net.siinergy.dsc.domain.Reference;
import net.siinergy.dsc.domain.Vendor;
import net.siinergy.dsc.domain.Version;

@Service
public class DataParser {

	@Autowired
	ElasticService elkService;
	Logger LOGGER =LoggerFactory.getLogger(this.getClass());
	
	public List<Cpe> parsCpe(String path) {
		LOGGER.info("Start reading CPE file data {}", path);
		String responseData = this.dataReader(path);
		responseData = responseData.replace("<</", "</").replaceAll("<([^\\w/\\?])", "&lt;$1");
		Document document = Jsoup.parseBodyFragment(responseData);
		return this.pars(document);
	}

	private String dataReader(String path) {
		List<String> filsLine = new ArrayList<>();
		try {
			filsLine = Files.readAllLines(new File(path).toPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (String str : filsLine) {
			sb.append(str);
		}

		return sb.toString();
	}

	private List<Cpe> pars(Document document) {
		LOGGER.info("Start pars CPE Document ...");
		Elements els = document.getElementsByTag("cpe-list");
		List<Cpe> listCpe = new ArrayList<>();
		if (els != null && !els.isEmpty()) {
			Cpe cpe = new Cpe();
			Elements elsItem = els.get(0).getElementsByTag("cpe-item");
			for (Element el : elsItem) {
				cpe.setCpe_item(el.attr("name"));
				cpe.setCpe_title(el.getElementsByTag("title").get(0).text());
				cpe.setCpe_23(el.getElementsByTag("cpe-23:cpe23-item").get(0).attr("name"));
				listCpe.add(cpe);
				LOGGER.info("CPE {} are parsed", cpe.getCpe_title());
			}
		}
		return listCpe;
	}

	public List<Cve> parsCve(String path, List<Cpe> cpes) throws IOException {
		LOGGER.info("Start reading CVE data {} ... ", path);
		String responseData = this.dataReader(path);

		final ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNodeBase = mapper.readTree(responseData);

		JsonNode rootNodes = jsonNodeBase.get("CVE_Items");

		List<Cve> listCve = new ArrayList<>();
		
		for (JsonNode jsonNode : rootNodes) {
			JsonNode cveNode = jsonNode.get("cve");
			Cve cve = new Cve();
			cve.setCve_id(cveNode.get("CVE_data_meta").get("ID").textValue());
			JsonNode affect_vendor = cveNode.get("affects").get("vendor").get("vendor_data");
			
			LOGGER.info("Start prsing CVE {} ... ", cve.getCve_id());
			Vendor vendor = new Vendor();
			if (affect_vendor != null && affect_vendor.findValue("vendor_name") != null) {
				vendor.setVendor_name(affect_vendor.findValue("vendor_name").textValue());
				for (JsonNode nodAV : affect_vendor) {
					JsonNode nodev_product = nodAV.get("product").get("product_data");
					List<Product> listProduct = new ArrayList<>();
					for (JsonNode product_data : nodev_product) {
						Product product = new Product();
						product.setProduct_name(product_data.findValue("product_name").textValue());

						JsonNode version_data = product_data.get("version").get("version_data");
						List<Version> listVersion = new ArrayList<>();
						
						for (JsonNode versionNode : version_data) {
							Version version = new Version();
							version.setVersion_value(versionNode.findValue("version_value").textValue());
							version.setVersion_affected(versionNode.findValue("version_affected").textValue());
							listVersion.add(version);
						}
						product.setProduct_versions(listVersion);
						listProduct.add(product);

					}
					vendor.setVendor_products(listProduct);

				}
			}
			cve.setCve_affects_vendor(vendor);
			cve.setCve_cpe_by_vendor(getcpe_ByVendor(vendor));

			String cve_description = cveNode.get("description").get("description_data").findValue("value").textValue();
			cve.setCve_description(cve_description);

			List<Reference> cve_references = new ArrayList<>();
			JsonNode referenceNode = cveNode.get("references").get("reference_data");
			for (JsonNode nodRef : referenceNode) {
				Reference ref = new Reference();
				ref.setName(nodRef.findValue("name").textValue());
				ref.setUrl(nodRef.findValue("url").textValue());
				cve_references.add(ref);
			}
			cve.setCve_references(cve_references);

			
			
			
			List<Cpe> cve_cpe_matchs = new ArrayList<>();
			List<JsonNode> cpeMatchNode = jsonNode.get("configurations").get("nodes").findValues("cpe_match");
			Cpe cpeToMatch = new Cpe();
			for (JsonNode cpeMatch : cpeMatchNode) {
				cpeToMatch.setVulnerable(cpeMatch.findValue("vulnerable").asBoolean());
				cpeToMatch.setCpe_23(cpeMatch.findValue("cpe23Uri").textValue());
				LOGGER.info("Pars CPE CVE Match {} ... ", cpeToMatch.getCpe_23());
				cve_cpe_matchs.add(cpeToMatch);
				
				LOGGER.info("Start finding CPE Match {} ... ", cpeToMatch.getCpe_23());
				
				Cpe relatedCpe = this.findCpeMatch(cpes, cpeToMatch);
				if (relatedCpe != null) {
					cve_cpe_matchs.add(relatedCpe);
				} else {
					// pas de correspondance CPE
					// il fau faire l'invers pour trouver le bon cpe via Vendor

					Cpe relatedCpeByVendor = this.findCpeMatchByVendor(cpes, cve.getCve_cpe_by_vendor());
					if (relatedCpeByVendor != null)
						cve_cpe_matchs.add(relatedCpe);
				}
			}

			cve.setCve_cpe_matchs(cve_cpe_matchs);

			
			//impact
			//List<JsonNode> cve_impact = jsonNode.get("impact").get("nodes").findValues("cpe_match");
			
			
			LOGGER.info("index document : {}", cve.toString());
			elkService.index(cve);
			
			listCve.add(cve);
		}

		return listCve;
	}

	private Cpe findCpeMatchByVendor(List<Cpe> cpes, List<String> cve_cpe_by_vendor) {
		for (String cpeToMatch : cve_cpe_by_vendor) {
			for (Cpe cpe : cpes) {
				if (cpe.getCpe_23().contains(cpeToMatch)) {
					return cpe;
				}
			}
		}
		return null;
	}

	private Cpe findCpeMatch(List<Cpe> cpes, Cpe cpeToMatch) {
		for (Cpe cpe : cpes) {
			cpe.setVulnerable(cpeToMatch.isVulnerable());
			if (isWFN(cpe.getCpe_23(), cpeToMatch.getCpe_23())) {
				return cpe;
			}
		}
		return null;
	}

	private boolean isWFN(String cpe_23, String cpe_23_toMatch) {
		Objects.requireNonNull(cpe_23);
		Objects.requireNonNull(cpe_23_toMatch);
		if (cpe_23.equals(cpe_23_toMatch)) {
			return true;
		}
		return false;
	}

	private List<String> getcpe_ByVendor(Vendor vendor) {
		List<String> cpe_byVendor = new ArrayList<>();
		List<Product> products = vendor.getVendor_products();

		String vendorName = vendor.getVendor_name();
		if (products!=null) {
			for (Product product : products) {
				String productName = product.getProduct_name();

				for (Version version : product.getProduct_versions()) {
					StringBuilder sb = new StringBuilder();
					sb.append(vendorName).append(":").append(productName).append(":")
							.append(version.getVersion_value());
					cpe_byVendor.add(sb.toString());
				}
			}
		}
		return cpe_byVendor;
	}

}
