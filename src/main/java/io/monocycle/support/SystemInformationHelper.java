package io.monocycle.support;

import io.monocycle.agent.model.CpuLoadInfo;
import io.monocycle.agent.model.MemInfo;
import io.monocycle.agent.model.MountPointInfo;
import io.monocycle.agent.model.ServerInfo;
import io.monocycle.exception.AgentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.SysInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemInformationHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemInformationHelper.class);

	private static final String[] IGNORE_DEVICES = { "none", "udev", "sysfs", "devpts", "binfmt_misc", "systemd", "gvfsd-fuse" };

	private static final String[] IGNORE_SYSTYPES = { "proc", "tmpfs" };

	private static final Sigar sigar = new Sigar();

	public static ServerInfo getServerInfo() {

		try {

			LOGGER.debug("Gathering server info...");

			SysInfo sysInfo = new SysInfo();
			sysInfo.gather(sigar);

			ServerInfo serverInfo = new ServerInfo(sigar.getNetInfo().getHostName(), sysInfo.getVendorName(), sysInfo.getDescription());

			LOGGER.debug("Server info: {}", serverInfo);

			return serverInfo;

		} catch (SigarException e) {
			throw new AgentException("Error reading server info", e);
		}

	}

	public static CpuLoadInfo getCpuLoadInfo() {

		LOGGER.debug("Getting CPU load info...");

		try {

			CpuPerc cpuPerc = sigar.getCpuPerc();
			CpuLoadInfo cpu = new CpuLoadInfo(cpuPerc.getCombined() * 100);

			LOGGER.debug("CPU load info: {}", cpu);

			return cpu;

		} catch (SigarException e) {
			throw new AgentException("Error reading cpu load info", e);
		}

	}

	public static CpuLoadInfo[] getCpusLoadInfo() {

		LOGGER.debug("Getting all CPU cores load info...");

		try {

			CpuPerc[] cpuPercs = sigar.getCpuPercList();
			CpuLoadInfo[] cpus = new CpuLoadInfo[cpuPercs.length];

			for (int i = 0; i < cpuPercs.length; i++) {
				cpus[i] = new CpuLoadInfo(cpuPercs[i].getCombined() * 100);
			}

			LOGGER.debug("CPU cores load info: {}", Arrays.toString(cpus));

			return cpus;

		} catch (SigarException e) {
			throw new AgentException("Error reading CPU cores load info", e);
		}

	}

	public static MountPointInfo[] getMountPointsInfo() {

		LOGGER.debug("Getting mount points info...");

		try {

			FileSystem[] fileSystems = sigar.getFileSystemList();
			List<MountPointInfo> mountPoints = new ArrayList<MountPointInfo>();

			for (FileSystem fileSystem : fileSystems) {

				try {

					if (fileSystem.getDevName() == null || equalsAny(fileSystem.getDevName(), IGNORE_DEVICES)) {
						continue;
					}

					if (fileSystem.getSysTypeName() == null || equalsAny(fileSystem.getSysTypeName(), IGNORE_SYSTYPES)) {
						continue;
					}

					FileSystemUsage usage = sigar.getFileSystemUsage(fileSystem.getDirName());

					mountPoints.add(new MountPointInfo(fileSystem.getDevName(), fileSystem.getDirName(), usage.getTotal(), usage.getUsed(), usage
							.getAvail()));

					LOGGER.debug("Mount points info: {}", mountPoints);

				} catch (SigarException e) {
					throw new AgentException("Error reading mount point info: " + fileSystem.getDevName() + " -> " + fileSystem.getDirName(), e);
				}
			}

			return mountPoints.toArray(new MountPointInfo[0]);

		} catch (SigarException e) {
			throw new AgentException("Error reading mount points info", e);
		}

	}

	public static MemInfo getMemInfo() {

		LOGGER.debug("Getting memory info...");

		try {

			Mem mem = sigar.getMem();
			Swap swap = sigar.getSwap();

			MemInfo memInfo = new MemInfo(mem.getTotal(), mem.getActualUsed(), mem.getActualFree(), swap.getTotal() - mem.getTotal(), swap.getUsed()
					- mem.getActualUsed(), swap.getFree());

			LOGGER.debug("Memory info: {}", memInfo);

			return memInfo;

		} catch (SigarException e) {
			throw new AgentException("Error reading memory info", e);
		}

	}

	private static boolean equalsAny(String str, String[] strs) {
		
		for (String s : strs) {
			if (s.equals(str)) {
				return true;
			}
		}

		return false;
		
	}

}