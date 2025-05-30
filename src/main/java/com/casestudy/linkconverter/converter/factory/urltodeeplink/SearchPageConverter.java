package com.casestudy.linkconverter.converter.factory.urltodeeplink;

import com.casestudy.linkconverter.converter.utils.ConverterUtils;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Converter for search result pages, transforming a web URL
 * into a search deep link.
 */
public class SearchPageConverter implements UrlConverter {

    /**
     * Convert a search page URL into a deep link.
     * <p>
     * Parses query parameters for the search term and builds
     * a deep link with the appropriate page and query fields.
     * </p>
     *
     * @param url the search page URL
     * @return the corresponding deep link, or home link on error
     */
    @Override
    public String convert(String url) {
        String sanitized = ConverterUtils.sanitizeUrl(url);

        try {
            URI uri = new URI(sanitized);
            Map<String,String> params = ConverterUtils.parseQueryParams(uri.getQuery());
            String term = params.getOrDefault(
                    DeeplinkConstants.SEARCH_QUERY_PARAM_KEY,
                    DeeplinkConstants.EMPTY_STRING
            );
            return ConverterUtils.buildDeepLinkSingleParam(
                    DeeplinkConstants.PAGE_SEARCH,
                    DeeplinkConstants.DEEPLINK_PARAM_SEARCH_QUERY,
                    term
            );

        } catch (URISyntaxException e) {
            return ConverterUtils.homeDeepLink();
        }
    }

}