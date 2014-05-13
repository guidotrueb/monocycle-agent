package io.monocycle.agent.model;

import java.util.Arrays;
import java.util.Date;

public class SystemInfo {

	private Date timestamp;

	private CpuLoadInfo cpu;

	private CpuLoadInfo[] cpus;

	private MountPointInfo[] mountPoints;

	private MemInfo memInfo;

	public SystemInfo() {
		// Default Constructor
	}

	public SystemInfo(Date timestamp, CpuLoadInfo cpu, CpuLoadInfo[] cpus, MountPointInfo[] mountPoints, MemInfo memInfo) {
		this.timestamp = timestamp;
		this.cpu = cpu;
		this.cpus = cpus;
		this.mountPoints = mountPoints;
		this.memInfo = memInfo;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setCpus(CpuLoadInfo[] cpus) {
		this.cpus = cpus;
	}

	public void setMemInfo(MemInfo memInfo) {
		this.memInfo = memInfo;
	}

	public MountPointInfo[] getMountPoints() {
		return mountPoints;
	}

	public void setMountPoints(MountPointInfo[] mountPoints) {
		this.mountPoints = mountPoints;
	}

	public CpuLoadInfo getCpu() {
		return cpu;
	}

	public void setCpu(CpuLoadInfo cpu) {
		this.cpu = cpu;
	}

	public CpuLoadInfo[] getCpus() {
		return cpus;
	}

	public MemInfo getMemInfo() {
		return memInfo;
	}

	@Override
	public String toString() {
		return getClass().getName() + " {\n\ttimestamp: " + timestamp + "\n\tcpu: " + cpu + "\n\tcpus: " + Arrays.toString(cpus)
				+ "\n\tmountPoints: " + Arrays.toString(mountPoints) + "\n\tmemInfo: " + memInfo + "\n}";
	}

}