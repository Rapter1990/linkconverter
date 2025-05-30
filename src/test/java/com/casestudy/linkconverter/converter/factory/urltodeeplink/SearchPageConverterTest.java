package com.casestudy.linkconverter.converter.factory.urltodeeplink;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.utils.ConverterUtils;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class SearchPageConverterTest extends AbstractBaseServiceTest {

    @InjectMocks
    private SearchPageConverter searchPageConverter;

    @Test
    @DisplayName("convert builds deep link with provided search term")
    void convert_withQueryParam_returnsDeepLinkWithTerm() {
        String term = "laptop";
        String url = "https://www.example.com/sr?q=" + term;

        String expected = ConverterUtils.buildDeepLinkSingleParam(
                DeeplinkConstants.PAGE_SEARCH,
                DeeplinkConstants.DEEPLINK_PARAM_SEARCH_QUERY,
                term
        );
        assertEquals(expected, searchPageConverter.convert(url));
    }

    @Test
    @DisplayName("convert uses empty term when no query param present")
    void convert_withoutQueryParam_returnsDeepLinkWithEmptyTerm() {
        String url = "https://www.example.com/sr";
        String expected = ConverterUtils.buildDeepLinkSingleParam(
                DeeplinkConstants.PAGE_SEARCH,
                DeeplinkConstants.DEEPLINK_PARAM_SEARCH_QUERY,
                DeeplinkConstants.EMPTY_STRING
        );
        assertEquals(expected, searchPageConverter.convert(url));
    }

    @Test
    @DisplayName("convert strips whitespace in URL and query term")
    void convert_withWhitespace_removesWhitespace() {
        String rawTerm = " lap top ";
        String url = " https://www.trendyol.com/sr?q=" + rawTerm + " ";
        // sanitizeUrl will remove all whitespace, giving "ty://?Page=Search&Query=laptop"
        String sanitizedTerm = "laptop";
        String expected = ConverterUtils.buildDeepLinkSingleParam(
                DeeplinkConstants.PAGE_SEARCH,
                DeeplinkConstants.DEEPLINK_PARAM_SEARCH_QUERY,
                sanitizedTerm
        );
        assertEquals(expected, searchPageConverter.convert(url));
    }

    @Test
    @DisplayName("convert returns home deep link on URISyntaxException")
    void convert_malformedUrl_returnsHomeDeepLink() {
        String badUrl = "://not a valid uri";
        String expected = ConverterUtils.homeDeepLink();
        assertEquals(expected, searchPageConverter.convert(badUrl));
    }

}