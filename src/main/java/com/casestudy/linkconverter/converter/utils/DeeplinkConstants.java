package com.casestudy.linkconverter.converter.utils;


import java.util.regex.Pattern;

/**
 * Constants used for building and parsing deep links and URLs.
 */
public final class DeeplinkConstants {

    //  URL TO DEEPLINK

    // Deep‐link basics
    public static final String DEEPLINK_PREFIX        = "ty://?Page=";
    public static final String PAGE_PRODUCT           = "Product";
    public static final String PAGE_SEARCH            = "Search";
    public static final String PAGE_HOME              = "Home";

    // URL sanitization
    public static final String WHITESPACE_REGEX       = "\\s+";
    public static final Pattern WHITESPACE_PATTERN    = Pattern.compile(WHITESPACE_REGEX);

    // Single-character hyphen for splitting path segment
    public static final String HYPHEN = "-";

    // Path markers
    public static final String PRODUCT_PATH_SEGMENT   = "-p-";
    public static final String SEARCH_PATH_SEGMENT    = "/sr";

    // ID‐extraction helpers
    public static final String PATH_SPLIT_REGEX       = "[/?]";
    public static final String DIGITS_REGEX           = "\\d+";
    public static final Pattern TRAILING_ID_PATTERN   =
            Pattern.compile(".*/[^/]+" + "-" + DIGITS_REGEX + "(/.*)?$");

    // Query‐param keys
    public static final String PARAM_BOUTIQUE_ID      = "boutiqueId";
    public static final String PARAM_MERCHANT_ID      = "merchantId";

    // Deep‐link query parts
    public static final String DEEPLINK_PARAM_CONTENT_ID  = "&ContentId=";
    public static final String DEEPLINK_PARAM_CAMPAIGN_ID = "&CampaignId=";
    public static final String DEEPLINK_PARAM_MERCHANT_ID = "&MerchantId=";

    // SearchPageConverter constants
    public static final String SEARCH_QUERY_PARAM_KEY        = "q";
    public static final String PARAMETER_DELIMITER           = "&";
    public static final String KEY_VALUE_DELIMITER           = "=";
    public static final int    KEY_VALUE_SPLIT_LIMIT         = 2;
    public static final String EMPTY_STRING                  = "";
    public static final String DEEPLINK_PARAM_SEARCH_QUERY   = "&Query=";

    // DEEPLINK TO URL

    // Base Url
    public static final String BASE_WEB_URL               = "https://www.trendyol.com";
    // Product URL prefix
    public static final String PRODUCT_URL_PATH_PREFIX    = "/brand/name" + PRODUCT_PATH_SEGMENT;

    // Search Url Template */
    public static final String SEARCH_URL_PATH            = "/sr?q=";

    // Deep link params
    public static final String DL_PARAM_PAGE              = "Page";
    public static final String DL_PARAM_CONTENT_ID        = "ContentId";
    public static final String DL_PARAM_CAMPAIGN_ID       = "CampaignId";
    public static final String DL_PARAM_MERCHANT_ID       = "MerchantId";
    public static final String DL_PARAM_SEARCH_QUERY      = "Query";

    // Delimiter between path and query in a URI
    public static final String QUERY_DELIMITER        = "?";

    // Cache name for URL conversions
    public static final String URL_CONVERSION_CACHE = "urlConversionCache";

}
