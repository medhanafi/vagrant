package net.siinergy.dsc.domain;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class CveDTO {

	private String cve_id;
	private String cve_description;
	private String cve_affects_vendor_name;
	private String cve_affects_vendor_product_name;
	private String cve_affects_vendor_product_version_value;
	private String cve_affects_vendor_product_version_affected;
					
	private String cve_impact_cvss_vectorString;
	private String cve_impact_cvss_authentication;
	private String cve_impact_cvss_confidentialityImpact;
	private String cve_impact_cvss_integrityImpact;
	private String cve_impact_cvss_availabilityImpact;
	private String cve_impact_cvss_baseScore;
	private String cve_impact_severity;
	private String cve_impact_impactScore;

	private List<Reference> cve_references = new ArrayList<>();
	private List<Cpe> cve_cpe_matchs = new ArrayList<>();
	private List<String> cve_cpe_by_vendor = new ArrayList<>();

	

	public String getCve_id() {
		return cve_id;
	}



	public void setCve_id(String cve_id) {
		this.cve_id = cve_id;
	}



	public String getCve_description() {
		return cve_description;
	}



	public void setCve_description(String cve_description) {
		this.cve_description = cve_description;
	}



	public String getCve_affects_vendor_name() {
		return cve_affects_vendor_name;
	}



	public void setCve_affects_vendor_name(String cve_affects_vendor_name) {
		this.cve_affects_vendor_name = cve_affects_vendor_name;
	}



	public String getCve_affects_vendor_product_name() {
		return cve_affects_vendor_product_name;
	}



	public void setCve_affects_vendor_product_name(String cve_affects_vendor_product_name) {
		this.cve_affects_vendor_product_name = cve_affects_vendor_product_name;
	}



	public String getCve_affects_vendor_product_version_value() {
		return cve_affects_vendor_product_version_value;
	}



	public void setCve_affects_vendor_product_version_value(String cve_affects_vendor_product_version_value) {
		this.cve_affects_vendor_product_version_value = cve_affects_vendor_product_version_value;
	}



	public String getCve_affects_vendor_product_version_affected() {
		return cve_affects_vendor_product_version_affected;
	}



	public void setCve_affects_vendor_product_version_affected(String cve_affects_vendor_product_version_affected) {
		this.cve_affects_vendor_product_version_affected = cve_affects_vendor_product_version_affected;
	}



	public String getCve_impact_cvss_vectorString() {
		return cve_impact_cvss_vectorString;
	}



	public void setCve_impact_cvss_vectorString(String cve_impact_cvss_vectorString) {
		this.cve_impact_cvss_vectorString = cve_impact_cvss_vectorString;
	}



	public String getCve_impact_cvss_authentication() {
		return cve_impact_cvss_authentication;
	}



	public void setCve_impact_cvss_authentication(String cve_impact_cvss_authentication) {
		this.cve_impact_cvss_authentication = cve_impact_cvss_authentication;
	}



	public String getCve_impact_cvss_confidentialityImpact() {
		return cve_impact_cvss_confidentialityImpact;
	}



	public void setCve_impact_cvss_confidentialityImpact(String cve_impact_cvss_confidentialityImpact) {
		this.cve_impact_cvss_confidentialityImpact = cve_impact_cvss_confidentialityImpact;
	}



	public String getCve_impact_cvss_integrityImpact() {
		return cve_impact_cvss_integrityImpact;
	}



	public void setCve_impact_cvss_integrityImpact(String cve_impact_cvss_integrityImpact) {
		this.cve_impact_cvss_integrityImpact = cve_impact_cvss_integrityImpact;
	}



	public String getCve_impact_cvss_availabilityImpact() {
		return cve_impact_cvss_availabilityImpact;
	}



	public void setCve_impact_cvss_availabilityImpact(String cve_impact_cvss_availabilityImpact) {
		this.cve_impact_cvss_availabilityImpact = cve_impact_cvss_availabilityImpact;
	}



	public String getCve_impact_cvss_baseScore() {
		return cve_impact_cvss_baseScore;
	}



	public void setCve_impact_cvss_baseScore(String cve_impact_cvss_baseScore) {
		this.cve_impact_cvss_baseScore = cve_impact_cvss_baseScore;
	}



	public String getCve_impact_severity() {
		return cve_impact_severity;
	}



	public void setCve_impact_severity(String cve_impact_severity) {
		this.cve_impact_severity = cve_impact_severity;
	}



	public String getCve_impact_impactScore() {
		return cve_impact_impactScore;
	}



	public void setCve_impact_impactScore(String cve_impact_impactScore) {
		this.cve_impact_impactScore = cve_impact_impactScore;
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



	public void setCve_cpe_by_vendor(List<String> cve_cpe_by_vendor) {
		this.cve_cpe_by_vendor = cve_cpe_by_vendor;
	}



	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
