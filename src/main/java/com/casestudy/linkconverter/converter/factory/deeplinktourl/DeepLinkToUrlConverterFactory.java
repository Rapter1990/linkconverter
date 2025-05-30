package com.casestudy.linkconverter.converter.factory.deeplinktourl;

import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Factory for obtaining the appropriate {@link DeepLinkToUrlConverter} implementation
 * based on the page parameter extracted from the deep link.
 */
@Component
public class DeepLinkToUrlConverterFactory {

    /**
     * Determine and return a converter for the specified deep link.
     *
     * @param deeplink the deep link containing query parameters
     * @return a converter capable of handling the deep link's page type
     */
    public DeepLinkToUrlConverter getConverter(String deeplink) {
        // pull out the Page= value
        String afterQ = deeplink.substring(deeplink.indexOf(DeeplinkConstants.QUERY_DELIMITER) + 1);
        String page = Arrays.stream(afterQ.split(DeeplinkConstants.PARAMETER_DELIMITER ))
                .map(p -> p.split(DeeplinkConstants.KEY_VALUE_DELIMITER,DeeplinkConstants.KEY_VALUE_SPLIT_LIMIT))
                .filter(a->a[0].equals(DeeplinkConstants.DL_PARAM_PAGE))
                .map(a->a[1])
                .findFirst()
                .orElse(DeeplinkConstants.EMPTY_STRING);

        return switch(page) {
            case DeeplinkConstants.PAGE_PRODUCT -> new ProductWebUrlConverter();
            case DeeplinkConstants.PAGE_SEARCH  -> new SearchWebUrlConverter();
            default                             -> new DefaultWebUrlConverter();
        };
    }

}
