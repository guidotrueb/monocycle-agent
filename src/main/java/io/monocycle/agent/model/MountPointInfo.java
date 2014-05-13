package io.monocycle.agent.model;

import org.springframework.util.StringUtils;

public class MountPointInfo {

	private String device;

	private String mountPoint;

	private Long total;

	private Long used;

	private Long available;

	public MountPointInfo() {
		// Default Constructor
	}

	public MountPointInfo(String device, String mountPoint, Long total, Long used, Long available) {
		this.device = device;
		this.mountPoint = mountPoint;
		this.total = total;
		this.used = used;
		this.available = available;
	}

	public String getKey() {
		// TODO Horrible code, switch to regex? Only works on POSIX?
		String key = "dev_" + device + "_mp_" + mountPoint;
		return StringUtils.delete(StringUtils.replace(StringUtils.replace(key, "/", "-"), "\\", "-"), ":");
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getMountPoint() {
		return mountPoint;
	}

	public void setMountPoint(String mountPoint) {
		this.mountPoint = mountPoint;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getUsed() {
		return used;
	}

	public void setUsed(Long used) {
		this.used = used;
	}

	public Long getAvailable() {
		return available;
	}

	public void setAvailable(Long available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return getClass().getName() + " {\n\tdevice: " + device + "\n\tmountPoint: " + mountPoint + "\n\ttotal: " + total + "\n\tused: " + used
				+ "\n\tavailable: " + available + "\n}";
	}

}
