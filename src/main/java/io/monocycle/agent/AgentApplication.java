package io.monocycle.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableAsync
@EnableScheduling
public class AgentApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgentApplication.class);

	public static void main(String[] args) {

		LOGGER.debug("Starting Monocycle Agent...");

		SpringApplication.run(AgentApplication.class, args);

	}

}