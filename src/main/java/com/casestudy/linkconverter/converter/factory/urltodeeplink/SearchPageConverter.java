package com.casestudy.linkconverter.converter.factory.urltodeeplink;

import com.casestudy.linkconverter.converter.factory.UrlConverter;
import com.casestudy.linkconverter.converter.utils.ConverterUtils;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class SearchPageConverter implements UrlConverter {

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