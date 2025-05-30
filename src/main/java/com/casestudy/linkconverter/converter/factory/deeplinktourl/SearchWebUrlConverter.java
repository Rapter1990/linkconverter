package com.casestudy.linkconverter.converter.factory.deeplinktourl;

import com.casestudy.linkconverter.converter.utils.ConverterUtils;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;

import java.util.Map;

/**
 * Converter for search deep links into search page web URLs.
 */
public class SearchWebUrlConverter implements DeepLinkToUrlConverter {

    /**
     * Convert a search deep link into its corresponding web URL.
     * <p>
     * Extracts the search term parameter and appends it to the base search path.
     * </p>
     *
     * @param deeplink the search deep link to convert
     * @return the full search page web URL
     */
    @Override
    public String convert(String deeplink) {
        // 1) sanitize + pull query
        String sanitized = ConverterUtils.sanitizeUrl(deeplink);
        String query     = ConverterUtils.extractQuery(sanitized);
        Map<String,String> params = ConverterUtils.parseQueryParams(query);

        // 2) lookup term (defaults to "")
        String term = params.getOrDefault(
                DeeplinkConstants.DL_PARAM_SEARCH_QUERY,
                DeeplinkConstants.EMPTY_STRING
        );

        // 3) build search URL
        return new StringBuilder()
                .append(DeeplinkConstants.BASE_WEB_URL)
                .append(DeeplinkConstants.SEARCH_URL_PATH)
                .append(term)
                .toString();

    }

}