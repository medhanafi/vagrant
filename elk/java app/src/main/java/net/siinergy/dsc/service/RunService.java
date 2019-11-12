package net.siinergy.dsc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.siinergy.dsc.domain.Cpe;
import net.siinergy.dsc.domain.Cve;

@Service
public class RunService {
	Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UnZipFile zipfile;
	@Autowired
	DataParser parser;

	@Autowired
	ElasticService elkService;

	 @Scheduled(cron = "0/2 * * * * ?")
	public void download() throws IOException {
		long startTime = System.currentTimeMillis();
		
		
//		String cve_zipFile = zipfile
//				.downloadFile("https://nvd.nist.gov/feeds/json/cve/1.0/nvdcve-1.0-modified.json.zip", "dsc_cve.zip");
//		zipfile.unZipFile(cve_zipFile, "");
//		String cpe_zipFile = zipfile.downloadFile(
//				"https://nvd.nist.gov/feeds/xml/cpe/dictionary/official-cpe-dictionary_v2.3.xml.zip", "dsc_cpe.zip");
//		zipfile.unZipFile(cpe_zipFile, "");

		//List<Cpe> cpes = parser.parsCpe("official-cpe-dictionary_v2.3.xml");

		//List<Cve> cves = parser.parsCve("nvdcve-1.0-modified.json", cpes);

		// index
		
		elkService.index(new Cve());
//		for (Cve cve : cves) {
//			LOGGER.info("index document : {}", cve.toString());
//			elkService.index(cve);
//		}

		long estimatedTime = System.currentTimeMillis() - startTime;

		LOGGER.info("=== Elapsed time : {} ===", estimatedTime);

		//LOGGER.info("=== End process {} cves are indexed ===", cves.size());
	}
}
