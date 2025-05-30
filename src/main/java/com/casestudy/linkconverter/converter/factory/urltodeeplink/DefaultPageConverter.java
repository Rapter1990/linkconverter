package com.casestudy.linkconverter.converter.factory.urltodeeplink;

import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;

/**
 * Default converter that returns the application home deep link
 * when no specific converter applies.
 */
public class DefaultPageConverter implements UrlConverter {

    /**
     * Return the deep link for the home page.
     *
     * @param url the input URL (ignored)
     * @return the home deep link
     */
    @Override
    public String convert(String url) {
        return DeeplinkConstants.DEEPLINK_PREFIX + DeeplinkConstants.PAGE_HOME;
    }

}
