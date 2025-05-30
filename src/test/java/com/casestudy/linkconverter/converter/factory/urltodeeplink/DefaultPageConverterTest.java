package com.casestudy.linkconverter.converter.factory.urltodeeplink;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class DefaultPageConverterTest extends AbstractBaseServiceTest {

    @InjectMocks
    private DefaultPageConverter defaultPageConverter;

    @Test
    @DisplayName("convert should always return the home deep link for a typical URL")
    void convert_typicalUrl_returnsHomeDeepLink() {
        String expected = DeeplinkConstants.DEEPLINK_PREFIX + DeeplinkConstants.PAGE_HOME;
        assertEquals(expected, defaultPageConverter.convert("https://www.example.com/product/123"));
    }

    @Test
    @DisplayName("convert should return home deep link for empty string")
    void convert_emptyString_returnsHomeDeepLink() {
        String expected = DeeplinkConstants.DEEPLINK_PREFIX + DeeplinkConstants.PAGE_HOME;
        assertEquals(expected, defaultPageConverter.convert(""));
    }

    @Test
    @DisplayName("convert should return home deep link for malformed URL")
    void convert_malformedUrl_returnsHomeDeepLink() {
        String expected = DeeplinkConstants.DEEPLINK_PREFIX + DeeplinkConstants.PAGE_HOME;
        assertEquals(expected, defaultPageConverter.convert("not-a-url?!"));
    }

    @Test
    @DisplayName("convert should return home deep link when input is null")
    void convert_nullInput_returnsHomeDeepLink() {
        String expected = DeeplinkConstants.DEEPLINK_PREFIX + DeeplinkConstants.PAGE_HOME;
        assertEquals(expected, defaultPageConverter.convert(null));
    }


}