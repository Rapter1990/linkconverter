package com.casestudy.linkconverter.converter.controller;

import com.casestudy.linkconverter.base.AbstractRestControllerTest;
import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.dto.request.ConversionRequest;
import com.casestudy.linkconverter.converter.model.dto.response.ConversionResponse;
import com.casestudy.linkconverter.converter.model.mapper.ConversionToConversionResponseMapper;
import com.casestudy.linkconverter.converter.service.UrlConversionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;;

class UrlConversionControllerTest extends AbstractRestControllerTest {

    @MockitoBean
    UrlConversionService urlConversionService;

    @Mock
    private ConversionToConversionResponseMapper conversionToConversionResponseMapper;


    @Test
    @DisplayName("POST /api/v1/convert/url → 200 with converted deep link")
    void convertUrl_returnsCustomResponseAndInvokesServiceAndMapper() throws Exception {

        // Given
        final String originalUrl = "https://example.com/path";
        final Conversion conversion = Conversion.builder()
                .originalUrl(originalUrl)
                .deeplink("ty://?Page=Home")
                .build();

        final ConversionResponse conversionResponse = ConversionResponse.builder()
                .originalUrl(conversion.getOriginalUrl())
                .deeplink(conversion.getDeeplink())
                .build();

        final ConversionRequest req = ConversionRequest.builder()
                .url(originalUrl)
                .build();

        // When
        when(urlConversionService.convert(originalUrl)).thenReturn(conversion);
        when(conversionToConversionResponseMapper.mapToResponse(conversion)).thenReturn(conversionResponse);

        // Then
        mockMvc.perform(post("/api/v1/convert/url")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.response.originalUrl").value(originalUrl))
                .andExpect(jsonPath("$.response.deeplink").value("ty://?Page=Home"));

        // Verify
        verify(urlConversionService).convert(originalUrl);
        verifyNoMoreInteractions(urlConversionService, conversionToConversionResponseMapper);

    }

    @Test
    @DisplayName("POST /api/v1/convert/deeplink → 200 with converted web URL")
    void convertDeeplink_returnsCustomResponseAndInvokesServiceAndMapper() throws Exception {

        // Given
        final String deepLink = "ty://?Page=Search&Query=term";
        final Conversion conversion = Conversion.builder()
                .originalUrl(deepLink)
                .deeplink("https://example.com/search?term")
                .build();

        final ConversionResponse conversionResponse = ConversionResponse.builder()
                .originalUrl(conversion.getOriginalUrl())
                .deeplink(conversion.getDeeplink())
                .build();

        final ConversionRequest req = ConversionRequest.builder()
                .url(deepLink)
                .build();

        // When
        when(urlConversionService.convertDeepLink(deepLink)).thenReturn(conversion);
        when(conversionToConversionResponseMapper.mapToResponse(conversion)).thenReturn(conversionResponse);

        // Then
        mockMvc.perform(post("/api/v1/convert/deeplink")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.response.originalUrl").value(deepLink))
                .andExpect(jsonPath("$.response.deeplink").value("https://example.com/search?term"));

        // Verify
        verify(urlConversionService).convertDeepLink(deepLink);
        verifyNoMoreInteractions(urlConversionService, conversionToConversionResponseMapper);

    }

}