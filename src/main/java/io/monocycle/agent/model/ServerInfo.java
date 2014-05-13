package io.monocycle.agent.model;

public class ServerInfo {

	private String hostname;

	private String vendorName;

	private String vendorDescription;

	public ServerInfo() {
		// Default Constructor
	}

	public ServerInfo(String hostname, String vendorName, String vendorDescription) {
		this.hostname = hostname;
		this.vendorName = vendorName;
		this.vendorDescription = vendorDescription;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	@Override
	public String toString() {
		return getClass().getName() + " {\n\thostname: " + hostname + "\n\tvendorName: " + vendorName + "\n\tvendorDescription: " + vendorDescription
				+ "\n}";
	}

}
