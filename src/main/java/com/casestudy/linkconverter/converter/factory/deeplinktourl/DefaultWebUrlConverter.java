package com.casestudy.linkconverter.converter.factory.deeplinktourl;

import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;

/**
 * Default converter that returns the base web URL when no specific page is identified.
 */
public class DefaultWebUrlConverter implements DeepLinkToUrlConverter {

    /**
     * Return the application's base web URL.
     *
     * @param deeplink the deep link (ignored)
     * @return the base web URL
     */
    @Override
    public String convert(String deeplink) {
        return DeeplinkConstants.BASE_WEB_URL;
    }

}
