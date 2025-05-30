package com.casestudy.linkconverter.common.config;

import static org.junit.jupiter.api.Assertions.*;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.junit.jupiter.api.Test;


class OpenApiConfigTest {

    @Test
    void openApiInfo() {

        // Given
        OpenAPIDefinition openAPIDefinition = OpenApiConfig.class.getAnnotation(OpenAPIDefinition.class);

        // Then
        assertEquals("1.0.0", openAPIDefinition.info().version());
        assertEquals("linkconverter", openAPIDefinition.info().title());
        assertEquals("Case Study - Link Converter" +
                        " (Java 21, Spring Boot, MySql, JUnit, Docker, Kubernetes, Prometheus, Grafana, Github Actions (CI/CD) ) ",
                openAPIDefinition.info().description());

    }

    @Test
    void contactInfo() {

        // Given
        Info info = OpenApiConfig.class.getAnnotation(OpenAPIDefinition.class).info();
        Contact contact = info.contact();

        // Then
        assertEquals("Sercan Noyan Germiyanoğlu", contact.name());
        assertEquals("https://github.com/Rapter1990/linkconverter", contact.url());

    }

}