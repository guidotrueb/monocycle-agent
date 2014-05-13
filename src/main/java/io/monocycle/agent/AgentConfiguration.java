package io.monocycle.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AgentConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgentConfiguration.class);
	
	@Value("${monocycle.server.address}")
	private String serverAddress;

	public String getServerAddress() {
		return serverAddress;
	}

	public String getCollectorEndpoint(String hostname) {

		StringBuilder url = new StringBuilder();

		url.append(getServerAddress());
		url.append("/collector/");
		url.append(hostname);

		LOGGER.debug("getCollectorEndpoint URL: {}", url.toString());
		
		return url.toString();

	}

	public String getServerUpdateEndpoint(String hostname) {

		StringBuilder url = new StringBuilder();

		url.append(getServerAddress());
		url.append("/servers/");
		url.append(hostname);
		url.append("/update");
		
		LOGGER.debug("getServerUpdateEndpoint URL: {}", url.toString());

		return url.toString();

	}

}
