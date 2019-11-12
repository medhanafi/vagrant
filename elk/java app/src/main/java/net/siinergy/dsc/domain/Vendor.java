package net.siinergy.dsc.domain;


import java.util.List;

import com.google.gson.Gson;

public class Vendor {
	private String vendor_name;
	private List<Product> vendor_products;

	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public List<Product> getVendor_products() {
		return vendor_products;
	}

	public void setVendor_products(List<Product> vendor_products) {
		this.vendor_products = vendor_products;
	}

	@Override
	public String toString() {
		Gson gson = new Gson(); 
		return gson.toJson(this); 
	}
}
