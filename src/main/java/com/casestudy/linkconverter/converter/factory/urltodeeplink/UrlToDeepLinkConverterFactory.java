package com.casestudy.linkconverter.converter.factory.urltodeeplink;

import com.casestudy.linkconverter.converter.exception.DeepLinkConversionException;
import com.casestudy.linkconverter.converter.factory.UrlConverter;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class UrlToDeepLinkConverterFactory {
    public UrlConverter getConverter(String url) {

        // 1) remove any whitespace or line-breaks
        String sanitized = DeeplinkConstants.WHITESPACE_PATTERN
                .matcher(url)
                .replaceAll("");

        URI uri;
        try {
            uri = URI.create(sanitized);
        } catch (IllegalArgumentException e) {
            throw new DeepLinkConversionException("Malformed URL: " + url, e);
        }

        // 2) grab the path (URI.create throws unchecked on bad syntax)
        String path = uri.getPath().toLowerCase();

        // 3) decide which converter to use
        String category = (path.contains(DeeplinkConstants.PRODUCT_PATH_SEGMENT)
                || DeeplinkConstants.TRAILING_ID_PATTERN.matcher(path).matches())
                ? DeeplinkConstants.PAGE_PRODUCT
                : path.contains(DeeplinkConstants.SEARCH_PATH_SEGMENT)
                ? DeeplinkConstants.PAGE_SEARCH
                : DeeplinkConstants.PAGE_HOME;

        // 4) switch to return the proper converter
        return switch (category) {
            case DeeplinkConstants.PAGE_PRODUCT -> new ProductPageConverter();
            case DeeplinkConstants.PAGE_SEARCH  -> new SearchPageConverter();
            default                             -> new DefaultPageConverter();
        };

    }

}