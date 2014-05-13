package io.monocycle.agent.model;

public class CpuLoadInfo {

	private double usage;

	public CpuLoadInfo() {
		// Default Constructor
	}

	public CpuLoadInfo(double usage) {
		this.usage = usage;
	}

	public double getUsage() {
		return usage;
	}

	public void setUsage(double usage) {
		this.usage = usage;
	}

	@Override
	public String toString() {
		return getClass().getName() + " {\n\tusage: " + usage + "\n}";
	}

}
