package com.casestudy.linkconverter.converter.service;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.factory.deeplinktourl.DeepLinkToUrlConverter;
import com.casestudy.linkconverter.converter.factory.deeplinktourl.DeepLinkToUrlConverterFactory;
import com.casestudy.linkconverter.converter.factory.urltodeeplink.UrlConverter;
import com.casestudy.linkconverter.converter.factory.urltodeeplink.UrlToDeepLinkConverterFactory;
import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.entity.UrlConversionEntity;
import com.casestudy.linkconverter.converter.model.mapper.UrlConversionEntityToConversionMapper;
import com.casestudy.linkconverter.converter.repository.UrlConversionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class UrlConversionServiceTest extends AbstractBaseServiceTest {

    @InjectMocks
    private UrlConversionService urlConversionService;

    @Mock
    private UrlToDeepLinkConverterFactory urlToDeepLinkConverterFactory;

    @Mock
    private DeepLinkToUrlConverterFactory deepLinkToUrlConverterFactory;

    @Mock
    private UrlConversionRepository urlConversionRepository;

    @Mock
    private UrlConversionEntityToConversionMapper urlConversionEntityToConversionMapper;

    @Mock
    private UrlConverter urlConverter;

    @Mock
    private DeepLinkToUrlConverter deepLinkToUrlConverter;

    @Test
    @DisplayName("convert() should convert URL, save correct entity, and return mapped Conversion")
    void convert_convertsUrl_andPersistsAndMaps() {

        // Given
        final String url = "https://example.com/path";
        final String deeplink = "ty://?Page=Home";

        final UrlConversionEntity savedEntity = UrlConversionEntity.builder()
                .requestUrl(url)
                .deeplink(deeplink)
                .build();

        final Conversion expected = Conversion.builder()
                .originalUrl(url)
                .deeplink(deeplink)
                .build();

        // When
        when(urlToDeepLinkConverterFactory.getConverter(url)).thenReturn(urlConverter);
        when(urlConverter.convert(url)).thenReturn(deeplink);
        when(urlConversionRepository.save(any(UrlConversionEntity.class))).thenReturn(savedEntity);
        when(urlConversionEntityToConversionMapper.mapFromEntity(savedEntity)).thenReturn(expected);

        // When
        final Conversion actual = urlConversionService.convert(url);

        // Then
        assertEquals(expected.getDeeplink(), actual.getDeeplink());
        assertEquals(expected.getOriginalUrl(), actual.getOriginalUrl());

        // Verify
        verify(urlToDeepLinkConverterFactory).getConverter(url);
        verify(urlConverter).convert(url);
        verify(urlConversionRepository).save(argThat(entity ->
                url.equals(entity.getRequestUrl()) &&
                        deeplink.equals(entity.getDeeplink())
        ));
        verifyNoMoreInteractions(
                urlToDeepLinkConverterFactory, urlConverter,
                urlConversionRepository, urlConversionEntityToConversionMapper
        );

    }

    @Test
    @DisplayName("convertDeepLink() should convert deep link, save correct entity, and return mapped Conversion")
    void convertDeepLink_convertsDeepLink_andPersistsAndMaps() {

        // Given
        final String deeplink = "ty://?Page=Search&Query=term";
        final String webUrl = "https://example.com/search?term";
        final UrlConversionEntity savedEntity = UrlConversionEntity.builder()
                .requestUrl(deeplink)
                .deeplink(webUrl)
                .build();
        final Conversion expected = Conversion.builder()
                .originalUrl(deeplink)
                .deeplink(webUrl)
                .build();

        // When
        when(deepLinkToUrlConverterFactory.getConverter(deeplink))
                .thenReturn(deepLinkToUrlConverter);
        when(deepLinkToUrlConverter.convert(deeplink)).thenReturn(webUrl);
        when(urlConversionRepository.save(any(UrlConversionEntity.class)))
                .thenReturn(savedEntity);
        when(urlConversionEntityToConversionMapper.mapFromEntity(savedEntity))
                .thenReturn(expected);

        // Then
        final Conversion actual = urlConversionService.convertDeepLink(deeplink);

        // Then
        assertEquals(expected.getDeeplink(), actual.getDeeplink());
        assertEquals(expected.getOriginalUrl(), actual.getOriginalUrl());

        // Verify
        verify(deepLinkToUrlConverterFactory).getConverter(deeplink);
        verify(deepLinkToUrlConverter).convert(deeplink);
        verify(urlConversionRepository).save(argThat(entity ->
                deeplink.equals(entity.getRequestUrl()) &&
                        webUrl.equals(entity.getDeeplink())
        ));
        verifyNoMoreInteractions(
                deepLinkToUrlConverterFactory, deepLinkToUrlConverter,
                urlConversionRepository, urlConversionEntityToConversionMapper
        );

    }

}