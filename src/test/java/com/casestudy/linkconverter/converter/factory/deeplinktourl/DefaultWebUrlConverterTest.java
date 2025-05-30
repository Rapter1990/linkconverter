package com.casestudy.linkconverter.converter.factory.deeplinktourl;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class DefaultWebUrlConverterTest extends AbstractBaseServiceTest {

    @InjectMocks
    private DefaultWebUrlConverter defaultWebUrlConverter;

    @Test
    @DisplayName("convert should always return the base web URL for any input")
    void convert_returnsBaseWebUrl_forAnyInput() {
        String baseUrl = DeeplinkConstants.BASE_WEB_URL;

        assertEquals(baseUrl, defaultWebUrlConverter.convert("ty://?Page=Unknown"));
        assertEquals(baseUrl, defaultWebUrlConverter.convert(""));
        assertEquals(baseUrl, defaultWebUrlConverter.convert("not a deeplink"));
        assertEquals(baseUrl, defaultWebUrlConverter.convert(null));

    }

}