package net.siinergy.dsc.domain;


public class Cpe {
	private String cpe_23;
	private String cpe_item;
	private String cpe_title;
	private boolean vulnerable;

	public String getCpe_23() {
		return cpe_23;
	}

	public void setCpe_23(String cpe_23) {
		this.cpe_23 = cpe_23;
	}

	public String getCpe_item() {
		return cpe_item;
	}

	public void setCpe_item(String cpe_item) {
		this.cpe_item = cpe_item;
	}

	public String getCpe_title() {
		return cpe_title;
	}

	public void setCpe_title(String cpe_title) {
		this.cpe_title = cpe_title;
	}

	public boolean isVulnerable() {
		return vulnerable;
	}

	public void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}

}
