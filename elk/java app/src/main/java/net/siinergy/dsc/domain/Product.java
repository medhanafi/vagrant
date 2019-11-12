package net.siinergy.dsc.domain;


import java.util.List;
import com.google.gson.Gson;

public class Product {
	private String product_name;
	private List<Version> product_versions;
	

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public List<Version> getProduct_versions() {
		return product_versions;
	}

	public void setProduct_versions(List<Version> product_versions) {
		this.product_versions = product_versions;
	}

	
	@Override
	public String toString() {
		Gson gson = new Gson(); 
		return gson.toJson(this); 
	}
}
