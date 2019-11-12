package net.siinergy.dsc.domain;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Version {
	private String version_value;
	private String version_affected;

	public String getVersion_value() {
		return version_value;
	}

	public void setVersion_value(String version_value) {
		this.version_value = version_value;
	}

	public String getVersion_affected() {
		return version_affected;
	}

	public void setVersion_affected(String version_affected) {
		this.version_affected = version_affected;
	}

	public Map<String, String> toJson() throws IOException {
		Map<String, String> result = new HashMap<>();
		result.put("version_value", this.getVersion_value());
		result.put("version_affected", this.getVersion_affected());
		return result;

	}

	@Override
	public String toString() {
		Gson gson = new Gson(); 
		return gson.toJson(this); 
	}
	
	
}
