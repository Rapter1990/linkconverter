package com.casestudy.linkconverter.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Sercan Noyan Germiyanoğlu",
                        url = "https://github.com/Rapter1990/linkconverter"
                ),
                description = "Case Study - Link Converter" +
                        " (Java 21, Spring Boot, MySql, JUnit, Docker, Kubernetes, Prometheus, Grafana, Github Actions (CI/CD) ) ",
                title = "linkconverter",
                version = "1.0.0"
        )
)
public class OpenApiConfig {

}
