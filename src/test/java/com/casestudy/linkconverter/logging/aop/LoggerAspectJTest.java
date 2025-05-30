package com.casestudy.linkconverter.logging.aop;

import static org.junit.jupiter.api.Assertions.*;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.exception.DeepLinkConversionException;
import com.casestudy.linkconverter.logging.entity.LogEntity;
import com.casestudy.linkconverter.logging.service.LogService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ch.qos.logback.classic.Level;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoggerAspectJTest extends AbstractBaseServiceTest {

    @InjectMocks
    private LoggerAspectJ loggerAspectJ;

    @Mock
    private LogService logService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private ServletRequestAttributes servletRequestAttributes;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Signature signature;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and set request attributes
        when(servletRequestAttributes.getRequest()).thenReturn(httpServletRequest);
        when(servletRequestAttributes.getResponse()).thenReturn(httpServletResponse);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        // Mock JoinPoint signature
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("testMethod");
        when(signature.getDeclaringTypeName()).thenReturn("LoggerAspectJ");
        when(signature.getDeclaringType()).thenReturn(LoggerAspectJ.class);
    }

    @Test
    @DisplayName("logAfterThrowing builds correct LogEntity and saves it")
    void logAfterThrowing_buildsCorrectLogEntity_andSaves() {

        // Given
        Exception ex = new DeepLinkConversionException("failure details");

        // When
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("/api/test"));
        when(httpServletRequest.getMethod()).thenReturn("PUT");
        when(signature.getName()).thenReturn("testMethod");

        // Then
        loggerAspectJ.logAfterThrowing(joinPoint, ex);

        // Verify
        verify(logService).saveLogToDatabase(argThat(log ->
                log.getEndpoint().endsWith("/api/test") &&
                        log.getMethod().equals("PUT") &&
                        log.getMessage().equals(ex.getMessage()) &&
                        log.getErrorType().equals(ex.getClass().getName()) &&
                        log.getStatus().name().equals(DeepLinkConversionException.STATUS.name()) &&
                        log.getOperation().equals("testMethod") &&
                        log.getResponse().equals(ex.getMessage())
        ));

    }

    @Test
    public void testLogAfterReturning() throws IOException {

        // When
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/api/test"));
        when(httpServletRequest.getMethod()).thenReturn("POST");
        when(httpServletResponse.getStatus()).thenReturn(HttpStatus.OK.value());
        when(signature.getName()).thenReturn("testMethod");
        when(joinPoint.getSignature()).thenReturn(signature);

        // Then
        loggerAspectJ.logAfterReturning(joinPoint, "test response");

        // Verify
        verify(logService, times(1)).saveLogToDatabase(any(LogEntity.class));

    }

    @Test
    public void testLogAfterReturning_WithJsonNode() throws IOException {

        // Given
        JsonNode jsonNode = new ObjectMapper().createObjectNode().put("key", "value");

        // When
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/api/test"));
        when(httpServletRequest.getMethod()).thenReturn("POST");
        when(httpServletResponse.getStatus()).thenReturn(HttpStatus.OK.value());

        // Then
        loggerAspectJ.logAfterReturning(joinPoint, jsonNode);

        // Verify
        verify(logService, times(1)).saveLogToDatabase(any(LogEntity.class));

    }

    @Test
    public void testLogAfterReturning_NoResponseAttributes() throws IOException {

        // Given
        RequestContextHolder.resetRequestAttributes();

        // When
        loggerAspectJ.logAfterReturning(mock(JoinPoint.class), "test response");

        // Then
        verify(logService, never()).saveLogToDatabase(any(LogEntity.class));

    }

    @Test
    public void testLogAfterReturning_SaveLogThrowsException() throws IOException {

        // When
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost/api/test"));
        when(httpServletRequest.getMethod()).thenReturn("POST");
        when(httpServletResponse.getStatus()).thenReturn(HttpStatus.OK.value());
        when(signature.getName()).thenReturn("testMethod");
        when(joinPoint.getSignature()).thenReturn(signature);
        doThrow(new RuntimeException("Database error")).when(logService).saveLogToDatabase(any(LogEntity.class));

        // Then
        assertDoesNotThrow(() -> loggerAspectJ.logAfterReturning(joinPoint, "test response"));

        // Verify
        verify(logService, times(1)).saveLogToDatabase(any(LogEntity.class));

    }

    @Test
    void testLogAfterThrowing_whenRequestAttributesAreNull_thenLogError() {

        // Given
        RequestContextHolder.resetRequestAttributes();
        Exception ex = new RuntimeException("Some error");

        // When
        loggerAspectJ.logAfterThrowing(joinPoint, ex);

        // Then
        Optional<String> logMessage = logTracker.checkMessage(Level.ERROR, "logAfterThrowing | Request Attributes are null!");
        assertTrue(logMessage.isPresent(), "Expected error log message not found.");
        assertEquals(logMessage.get(), "logAfterThrowing | Request Attributes are null!");

    }

    @Test
    public void testGetHttpStatusFromException_AllCases() {

        // Given a mapping between exception instances and their expected HTTP status values
        Map<Exception, String> testCases = new HashMap<>();
        testCases.put(new DeepLinkConversionException("Invalid password"), DeepLinkConversionException.STATUS.name());
        testCases.put(new Exception("Unknown exception"), HttpStatus.INTERNAL_SERVER_ERROR.name());

        // When & Then: using ReflectionTestUtils to call the private method getHttpStatusFromException
        testCases.forEach((exception, expectedStatus) -> {
            String actualStatus = (String) ReflectionTestUtils.invokeMethod(
                    loggerAspectJ,
                    "getHttpStatusFromException",
                    exception
            );
            assertEquals(expectedStatus, actualStatus,
                    "Failed for exception: " + exception.getClass().getSimpleName());
        });
    }

}