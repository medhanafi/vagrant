package net.siinergy.dsc.domain;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.xcontent.XContentBuilder;

import com.google.gson.Gson;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Cve {

	private String cve_id;
	private String cve_description;
	private Vendor cve_affects_vendor;
	private Impact impact;

	private List<Reference> cve_references = new ArrayList<>();
	private List<Cpe> cve_cpe_matchs = new ArrayList<>();
	private List<String> cve_cpe_by_vendor= new ArrayList<>();
	
	private LocalDateTime publishedDate;
	private LocalDateTime lastModifiedDate;
	private LocalDateTime cve_index_date;
	
	public String getCve_id() {
		return cve_id;
	}

	public void setCve_id(String cve_id) {
		this.cve_id = cve_id;
	}

	public Vendor getCve_affects_vendor() {
		return cve_affects_vendor;
	}

	public void setCve_affects_vendor(Vendor cve_affects_vendor) {
		this.cve_affects_vendor = cve_affects_vendor;
	}

	public String getCve_description() {
		return cve_description;
	}

	public void setCve_description(String cve_description) {
		this.cve_description = cve_description;
	}

	public List<Reference> getCve_references() {
		return cve_references;
	}

	public void setCve_references(List<Reference> cve_references) {
		this.cve_references = cve_references;
	}

	public List<Cpe> getCve_cpe_matchs() {
		return cve_cpe_matchs;
	}

	public void setCve_cpe_matchs(List<Cpe> cve_cpe_matchs) {
		this.cve_cpe_matchs = cve_cpe_matchs;
	}

	
	public List<String> getCve_cpe_by_vendor() {
		return cve_cpe_by_vendor;
	}

	public void setCve_cpe_by_vendor(List<String> list) {
		this.cve_cpe_by_vendor = list;
	}

	public Impact getImpact() {
		return impact;
	}

	public void setImpact(Impact impact) {
		this.impact = impact;
	}

	public LocalDateTime getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDateTime publishedDate) {
		this.publishedDate = publishedDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public LocalDateTime getCve_index_date() {
		return cve_index_date;
	}

	public void setCve_index_date(LocalDateTime cve_index_date) {
		this.cve_index_date = cve_index_date;
	}

	public XContentBuilder toJson() throws IOException {
		return jsonBuilder().startObject().field("cve_id", this.getCve_id())
				.field("cve_affects_vendor", this.getCve_affects_vendor())
				.field("cve_description", this.getCve_description())
				.field("cve_references", this.getCve_references())
				.field("cve_cpe_matchs", this.getCve_cpe_matchs())
				.field("cve_cpe_by_vendor", this.getCve_cpe_by_vendor())
				.endObject();
	}

	@Override
	public String toString() {
		Gson gson = new Gson(); 
		return gson.toJson(this); 
	}
}
