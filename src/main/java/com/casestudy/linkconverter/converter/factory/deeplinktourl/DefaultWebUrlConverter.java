package com.casestudy.linkconverter.converter.factory.deeplinktourl;

import com.casestudy.linkconverter.converter.factory.DeepLinkToUrlConverter;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;

public class DefaultWebUrlConverter implements DeepLinkToUrlConverter {

    @Override
    public String convert(String dl) {
        return DeeplinkConstants.BASE_WEB_URL;
    }

}
