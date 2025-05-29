package com.casestudy.linkconverter.converter.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public final class ConverterUtils {

    public static String sanitizeUrl(String url) {
        return DeeplinkConstants.WHITESPACE_PATTERN
                .matcher(url)
                .replaceAll(DeeplinkConstants.EMPTY_STRING);
    }

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

    public static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static String homeDeepLink() {
        return DeeplinkConstants.DEEPLINK_PREFIX
                + DeeplinkConstants.PAGE_HOME;
    }

    public static String buildDeepLinkSingleParam(String page, String paramConstant, String rawValue) {
        return new StringBuilder()
                .append(DeeplinkConstants.DEEPLINK_PREFIX)
                .append(page)
                .append(paramConstant)
                .append(encode(rawValue))
                .toString();
    }

    public static StringBuilder buildDeepLinkBuilder(String page, String paramConstant, String rawValue) {
        return new StringBuilder()
                .append(DeeplinkConstants.DEEPLINK_PREFIX)
                .append(page)
                .append(paramConstant)
                .append(encode(rawValue));
    }


    /**
     * Extract everything after the first “?”.
     * Returns empty string if “?” is missing.
     */
    public static String extractQuery(String sanitized) {
        int idx = sanitized.indexOf(DeeplinkConstants.QUERY_DELIMITER);
        if (idx < 0) return DeeplinkConstants.EMPTY_STRING;
        return sanitized.substring(idx + DeeplinkConstants.QUERY_DELIMITER.length());
    }

}
