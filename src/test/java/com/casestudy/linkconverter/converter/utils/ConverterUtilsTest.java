package com.casestudy.linkconverter.converter.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConverterUtilsTest {

    @Test
    @DisplayName("sanitizeUrl should remove all whitespace")
    void testSanitizeUrl_removesWhitespace() {
        String input = "  http:// example . com / path \n ";
        String expected = "http://example.com/path";
        assertEquals(expected, ConverterUtils.sanitizeUrl(input));
    }

    @Test
    @DisplayName("parseQueryParams should return empty map for null, empty, or blank")
    void testParseQueryParams_emptyInputs() {
        assertTrue(ConverterUtils.parseQueryParams(null).isEmpty());
        assertTrue(ConverterUtils.parseQueryParams("").isEmpty());
        assertTrue(ConverterUtils.parseQueryParams("   ").isEmpty());
    }

    @Test
    @DisplayName("parseQueryParams should parse single and multiple parameters")
    void testParseQueryParams_parsesParameters() {
        Map<String, String> single = ConverterUtils.parseQueryParams("a=1");
        assertEquals(Map.of("a", "1"), single);

        Map<String, String> multiple = ConverterUtils.parseQueryParams("a=1&b=two&c=3");
        assertEquals(3, multiple.size());
        assertEquals("1", multiple.get("a"));
        assertEquals("two", multiple.get("b"));
        assertEquals("3", multiple.get("c"));
    }

    @Test
    @DisplayName("parseQueryParams should ignore malformed pairs")
    void testParseQueryParams_ignoresMalformedPairs() {
        Map<String, String> result = ConverterUtils.parseQueryParams("a=1&badpair&c=3=");
        assertEquals(Map.of("a", "1"), Collections.singletonMap("a", "1"));
        assertFalse(result.containsKey("badpair"));
    }

    @Test
    @DisplayName("encode should URL-encode values using UTF-8")
    void testEncode_encodesSpecialCharacters() {
        String raw = "a b&c=d";
        String expected = URLEncoder.encode(raw, StandardCharsets.UTF_8);
        assertEquals(expected, ConverterUtils.encode(raw));
    }

    @Test
    @DisplayName("homeDeepLink should return base deep link for home page")
    void testHomeDeepLink() {
        String expected = DeeplinkConstants.DEEPLINK_PREFIX + DeeplinkConstants.PAGE_HOME;
        assertEquals(expected, ConverterUtils.homeDeepLink());
    }

    @Test
    @DisplayName("buildDeepLinkSingleParam should construct correct deep link")
    void testBuildDeepLinkSingleParam() {
        String page = "Product";
        String param = "&ContentId=";
        String value = "123";
        String expected = DeeplinkConstants.DEEPLINK_PREFIX + page + param + ConverterUtils.encode(value);
        assertEquals(expected, ConverterUtils.buildDeepLinkSingleParam(page, param, value));
    }

    @Test
    @DisplayName("buildDeepLinkBuilder should return a StringBuilder initialized correctly")
    void testBuildDeepLinkBuilder() {
        String page = "Search";
        String param = "&Query=";
        String value = "test";
        StringBuilder builder = ConverterUtils.buildDeepLinkBuilder(page, param, value);
        String expectedStart = DeeplinkConstants.DEEPLINK_PREFIX + page + param + ConverterUtils.encode(value);
        assertEquals(expectedStart, builder.toString());
        // Further appending works
        builder.append("&extra=1");
        assertTrue(builder.toString().endsWith("&extra=1"));
    }

    @Test
    @DisplayName("extractQuery should return substring after '?' or empty if none")
    void testExtractQuery_variousInputs() {
        String urlWithQuery = "scheme://host/path?param1=1&param2=two";
        assertEquals("param1=1&param2=two", ConverterUtils.extractQuery(urlWithQuery));

        String urlWithoutQuery = "scheme://host/path";
        assertEquals("", ConverterUtils.extractQuery(urlWithoutQuery));

        String urlWithMultipleQuestion = "a?b?c";
        assertEquals("b?c", ConverterUtils.extractQuery(urlWithMultipleQuestion));
    }

}