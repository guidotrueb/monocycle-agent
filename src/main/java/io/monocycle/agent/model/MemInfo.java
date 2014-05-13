package io.monocycle.agent.model;

public class MemInfo {

	private Long memTotal;

	private Long memUsed;

	private Long memAvailable;

	private Long swapTotal;

	private Long swapUsed;

	private Long swapAvailable;

	public MemInfo() {
		// Default Constructor
	}

	public MemInfo(Long memTotal, Long memUsed, Long memAvailable, Long swapTotal, Long swapUsed, Long swapAvailable) {
		this.memTotal = memTotal;
		this.memUsed = memUsed;
		this.memAvailable = memAvailable;
		this.swapTotal = swapTotal;
		this.swapUsed = swapUsed;
		this.swapAvailable = swapAvailable;
	}

	public void setMemTotal(Long memTotal) {
		this.memTotal = memTotal;
	}

	public void setMemUsed(Long memUsed) {
		this.memUsed = memUsed;
	}

	public void setSwapTotal(Long swapTotal) {
		this.swapTotal = swapTotal;
	}

	public void setSwapUsed(Long swapUsed) {
		this.swapUsed = swapUsed;
	}

	public Long getMemTotal() {
		return memTotal;
	}

	public Long getMemUsed() {
		return memUsed;
	}

	public Long getSwapTotal() {
		return swapTotal;
	}

	public Long getSwapUsed() {
		return swapUsed;
	}

	public Long getMemAvailable() {
		return memAvailable;
	}

	public void setMemAvailable(Long memAvailable) {
		this.memAvailable = memAvailable;
	}

	public Long getSwapAvailable() {
		return swapAvailable;
	}

	public void setSwapAvailable(Long swapAvailable) {
		this.swapAvailable = swapAvailable;
	}

	@Override
	public String toString() {
		return getClass().getName() + " {\n\tmemTotal: " + memTotal + "\n\tmemUsed: " + memUsed + "\n\tmemAvailable: " + memAvailable
				+ "\n\tswapTotal: " + swapTotal + "\n\tswapUsed: " + swapUsed + "\n\tswapAvailable: " + swapAvailable + "\n}";
	}

}
