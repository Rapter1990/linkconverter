package com.casestudy.linkconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class named {@link LinkconverterApplication} to start the Link Converter application.
 * Enables caching support, and scheduling.
 */
@SpringBootApplication
@EnableScheduling
public class LinkconverterApplication {

	/**
	 * Main method to start the Spring Boot application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(LinkconverterApplication.class, args);
	}

}
