package io.monocycle.agent.collector;

import io.monocycle.agent.AgentConfiguration;
import io.monocycle.agent.api.DataPoint;
import io.monocycle.agent.api.DoubleDataHolder;
import io.monocycle.agent.api.Server;
import io.monocycle.agent.api.ServerSummary;
import io.monocycle.agent.api.ServerSummary.MountPointSummary;
import io.monocycle.agent.api.StringDataHolder;
import io.monocycle.agent.model.CpuLoadInfo;
import io.monocycle.agent.model.MemInfo;
import io.monocycle.agent.model.MountPointInfo;
import io.monocycle.agent.model.ServerInfo;
import io.monocycle.support.SystemInformationHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope("singleton")
public class ServerInfoCollector {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerInfoCollector.class);

	@Autowired
	private AgentConfiguration configuration;

	@PostConstruct
	public void initialize() {
		
		LOGGER.info("Initializing ServerInfoCollector...");
		
		RestTemplate restTemplate = new RestTemplate();

		ServerInfo serverInfo = SystemInformationHelper.getServerInfo();

		Server server = new Server();
		server.setHostname(serverInfo.getHostname());

		ServerSummary summary = new ServerSummary();

		summary.setVendorName(serverInfo.getVendorName());
		summary.setVendorDescription(serverInfo.getVendorDescription());

		summary.setCoreCount(SystemInformationHelper.getCpusLoadInfo().length);
		summary.setTotalMemory(SystemInformationHelper.getMemInfo().getMemTotal());
		summary.setTotalSwap(SystemInformationHelper.getMemInfo().getSwapTotal());

		server.setServerSummary(summary);

		summary.setMountPoints(new HashSet<MountPointSummary>());

		for (MountPointInfo mountPointInfo : SystemInformationHelper.getMountPointsInfo()) {
			summary.getMountPoints().add(new MountPointSummary(mountPointInfo.getDevice(), mountPointInfo.getMountPoint()));
		}

		LOGGER.info("Registering Agent...");
		
		restTemplate.postForObject(configuration.getServerUpdateEndpoint(server.getHostname()), server, String.class);
		
		LOGGER.info("ServerInfoCollector successfully initialized");

	}
	

	@Scheduled(cron = "0 * * * * *")
	public void sendCollectedData() {

		LOGGER.debug("Sending DataPoints...");
		
		RestTemplate restTemplate = new RestTemplate();

		ServerInfo serverInfo = SystemInformationHelper.getServerInfo();
		CpuLoadInfo cpuLoadInfo = SystemInformationHelper.getCpuLoadInfo();
		CpuLoadInfo[] cpusLoadInfo = SystemInformationHelper.getCpusLoadInfo();
		MountPointInfo[] mountPointsInfo = SystemInformationHelper.getMountPointsInfo();
		MemInfo memInfo = SystemInformationHelper.getMemInfo();

		List<DataPoint> dataPoints = new ArrayList<DataPoint>();

		DataPoint cpuDataPoint = new DataPoint("cpu");
		cpuDataPoint.addValue("cpu", new DoubleDataHolder(cpuLoadInfo.getUsage()));

		for (int i = 0; i < cpusLoadInfo.length; i++) {
			cpuDataPoint.addValue("cpu_" + i, new DoubleDataHolder(cpusLoadInfo[i].getUsage()));
		}
		
		LOGGER.debug("Adding cpu datapoints: {}", cpuDataPoint);

		dataPoints.add(cpuDataPoint);

		for (MountPointInfo mountPointInfo : mountPointsInfo) {
			DataPoint mountPointDataPoint = new DataPoint("disk");

			mountPointDataPoint.addValue("device", new StringDataHolder(mountPointInfo.getDevice()));
			mountPointDataPoint.addValue("mount_point", new StringDataHolder(mountPointInfo.getMountPoint()));
			mountPointDataPoint.addValue("used", new DoubleDataHolder(mountPointInfo.getUsed().doubleValue()));
			mountPointDataPoint.addValue("total", new DoubleDataHolder(mountPointInfo.getTotal().doubleValue()));
			mountPointDataPoint.addValue("available", new DoubleDataHolder(mountPointInfo.getAvailable().doubleValue()));

			LOGGER.debug("Adding disk datapoint: {}", mountPointDataPoint);
			
			dataPoints.add(mountPointDataPoint);
		}

		DataPoint memoryDataPoint = new DataPoint("memory");

		memoryDataPoint.addValue("memory_used", new DoubleDataHolder((double) memInfo.getMemUsed()));
		memoryDataPoint.addValue("memory_available", new DoubleDataHolder((double) memInfo.getMemAvailable()));
		memoryDataPoint.addValue("memory_total", new DoubleDataHolder((double) memInfo.getMemTotal()));

		memoryDataPoint.addValue("swap_used", new DoubleDataHolder((double) memInfo.getSwapUsed()));
		memoryDataPoint.addValue("swap_available", new DoubleDataHolder((double) memInfo.getSwapAvailable()));
		memoryDataPoint.addValue("swap_total", new DoubleDataHolder((double) memInfo.getSwapTotal()));

		LOGGER.debug("Adding memory datapoint: {}", memoryDataPoint);
		
		dataPoints.add(memoryDataPoint);

		restTemplate.postForObject(configuration.getCollectorEndpoint(serverInfo.getHostname()), dataPoints, String.class);
		
		LOGGER.debug("DataPoints sent successfully...");

	}

}
