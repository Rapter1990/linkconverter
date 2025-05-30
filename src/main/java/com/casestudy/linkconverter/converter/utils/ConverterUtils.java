package com.casestudy.linkconverter.converter.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility methods for sanitizing and parsing URLs and deep links.
 */

public final class ConverterUtils {

    /**
     * Remove all whitespace characters from the input URL.
     *
     * @param url the raw URL string
     * @return the URL without whitespace or line breaks
     */
    public static String sanitizeUrl(String url) {
        return DeeplinkConstants.WHITESPACE_PATTERN
                .matcher(url)
                .replaceAll(DeeplinkConstants.EMPTY_STRING);
    }

    /**
     * Parse a query string into a map of parameter names and values.
     *
     * @param query the raw query string (after '?')
     * @return a map of query parameters or empty map if none
     */
    public static Map<String,String> parseQueryParams(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyMap();
        }
        return Arrays.stream(query.split(DeeplinkConstants.PARAMETER_DELIMITER))
                .map(pair -> pair.split(
                        DeeplinkConstants.KEY_VALUE_DELIMITER,
                        DeeplinkConstants.KEY_VALUE_SPLIT_LIMIT
                ))
                .filter(arr -> arr.length == DeeplinkConstants.KEY_VALUE_SPLIT_LIMIT)
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    /**
     * URL-encode a value using UTF-8.
     *
     * @param value the raw parameter value
     * @return the encoded value
     */
    public static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    /**
     * Build the application's home deep link.
     *
     * @return the home-page deep link string
     */
    public static String homeDeepLink() {
        return DeeplinkConstants.DEEPLINK_PREFIX
                + DeeplinkConstants.PAGE_HOME;
    }

    /**
     * Build a deep link string with a single query parameter.
     *
     * @param page     the deep link page identifier (e.g. "Product", "Search")
     * @param param    the deep link parameter constant including its leading '='
     * @param rawValue the raw parameter value to encode and append
     * @return the full deep link URL as a String
     */
    public static String buildDeepLinkSingleParam(String page, String param, String rawValue) {
        return new StringBuilder()
                .append(DeeplinkConstants.DEEPLINK_PREFIX)
                .append(page)
                .append(param)
                .append(encode(rawValue))
                .toString();
    }

    /**
     * Build a {@link StringBuilder} pre-populated with the deep link prefix,
     * page, parameter, and encoded value, for further customization.
     *
     * @param page     the deep link page identifier
     * @param param    the deep link parameter constant including its leading '='
     * @param rawValue the raw parameter value to encode and append
     * @return a {@link StringBuilder} initialized for deep link construction
     */

    public static StringBuilder buildDeepLinkBuilder(String page, String param, String rawValue) {
        return new StringBuilder()
                .append(DeeplinkConstants.DEEPLINK_PREFIX)
                .append(page)
                .append(param)
                .append(encode(rawValue));
    }


    /**
     * Extract the query part of a URL or deep link (after '?').
     *
     * @param sanitized the input string without whitespace
     * @return the query string or empty if none
     */
    public static String extractQuery(String sanitized) {
        int idx = sanitized.indexOf(DeeplinkConstants.QUERY_DELIMITER);
        if (idx < 0) return DeeplinkConstants.EMPTY_STRING;
        return sanitized.substring(idx + DeeplinkConstants.QUERY_DELIMITER.length());
    }

}
