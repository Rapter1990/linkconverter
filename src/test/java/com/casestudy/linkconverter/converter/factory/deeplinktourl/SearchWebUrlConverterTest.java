package com.casestudy.linkconverter.converter.factory.deeplinktourl;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class SearchWebUrlConverterTest extends AbstractBaseServiceTest {

    @InjectMocks
    private SearchWebUrlConverter searchWebUrlConverter;

    @Test
    @DisplayName("convert should build correct URL for given search term")
    void convert_withSearchTerm_returnsSearchUrlWithTerm() {

        String term = "laptop";
        String deeplink = "ty://?Page=Search&Query=" + term;

        String expected = DeeplinkConstants.BASE_WEB_URL
                + DeeplinkConstants.SEARCH_URL_PATH
                + term;
        assertEquals(expected, searchWebUrlConverter.convert(deeplink));

    }

    @Test
    @DisplayName("convert should handle missing Query param and default to empty term")
    void convert_withoutQueryParam_returnsSearchUrlWithEmptyTerm() {
        String deeplink = "ty://?Page=Search";

        String expected = DeeplinkConstants.BASE_WEB_URL
                + DeeplinkConstants.SEARCH_URL_PATH;
        assertEquals(expected, searchWebUrlConverter.convert(deeplink));

    }

    @Test
    @DisplayName("convert should sanitize whitespace in query value")
    void convert_withWhitespaceInTerm_stripsWhitespace() {

        String rawTerm = " lap top ";
        String deeplink = "ty://?Page=Search&Query=" + rawTerm;

        String sanitizedTerm = "laptop";
        String expected = DeeplinkConstants.BASE_WEB_URL
                + DeeplinkConstants.SEARCH_URL_PATH
                + sanitizedTerm;
        assertEquals(expected, searchWebUrlConverter.convert(deeplink));

    }

    @Test
    @DisplayName("convert should return base search URL when no query part at all")
    void convert_noQueryPart_returnsBaseSearchUrl() {
        String deeplink = "ty://?";

        String expected = DeeplinkConstants.BASE_WEB_URL
                + DeeplinkConstants.SEARCH_URL_PATH;
        assertEquals(expected, searchWebUrlConverter.convert(deeplink));

    }

}