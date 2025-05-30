package com.casestudy.linkconverter.converter.factory.urltodeeplink;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.exception.DeepLinkConversionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class UrlToDeepLinkConverterFactoryTest extends AbstractBaseServiceTest {

    @InjectMocks
    private UrlToDeepLinkConverterFactory urlToDeepLinkConverterFactory;

    @Test
    @DisplayName("getConverter throws DeepLinkConversionException for malformed URL")
    void getConverter_malformedUrl_throwsException() {
        String badUrl = "http://[:::bad-url]";
        DeepLinkConversionException ex = assertThrows(
                DeepLinkConversionException.class,
                () -> urlToDeepLinkConverterFactory.getConverter(badUrl)
        );
        assertTrue(ex.getMessage().contains("Malformed URL"));
    }

    @Test
    @DisplayName("getConverter returns ProductPageConverter for URLs containing product segment")
    void getConverter_productPath_returnsProductConverter() {
        String url = "https://www.trendyol.com/brand/name-p-1234";
        UrlConverter converter = urlToDeepLinkConverterFactory.getConverter(url);
        assertTrue(converter instanceof ProductPageConverter,
                "Expected ProductPageConverter for product path");
    }

    @Test
    @DisplayName("getConverter returns ProductPageConverter for trailing numeric ID")
    void getConverter_trailingId_returnsProductConverter() {
        String url = "https://www.trendyol.com/some-path-5678";
        UrlConverter converter = urlToDeepLinkConverterFactory.getConverter(url);
        assertTrue(converter instanceof ProductPageConverter,
                "Expected ProductPageConverter for trailing numeric ID");
    }

    @Test
    @DisplayName("getConverter returns SearchPageConverter for URLs containing search segment")
    void getConverter_searchPath_returnsSearchConverter() {
        String url = "https://www.trendyol.com/sr?q=laptop";
        UrlConverter converter = urlToDeepLinkConverterFactory.getConverter(url);
        assertTrue(converter instanceof SearchPageConverter,
                "Expected SearchPageConverter for search path");
    }

    @Test
    @DisplayName("getConverter returns DefaultPageConverter for other URLs")
    void getConverter_otherPaths_returnsDefaultConverter() {
        String url = "https://www.trendyol.com/other/page";
        UrlConverter converter = urlToDeepLinkConverterFactory.getConverter(url);
        assertTrue(converter instanceof DefaultPageConverter,
                "Expected DefaultPageConverter for non-product, non-search paths");
    }

}