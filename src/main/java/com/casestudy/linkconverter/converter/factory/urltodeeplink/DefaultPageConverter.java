package com.casestudy.linkconverter.converter.factory.urltodeeplink;

import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;

public class DefaultPageConverter implements UrlConverter {
    @Override
    public String convert(String url) {
        return DeeplinkConstants.DEEPLINK_PREFIX + DeeplinkConstants.PAGE_HOME;
    }
}
