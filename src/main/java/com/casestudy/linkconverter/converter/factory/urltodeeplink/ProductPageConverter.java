package com.casestudy.linkconverter.converter.factory.urltodeeplink;

import com.casestudy.linkconverter.converter.factory.UrlConverter;
import com.casestudy.linkconverter.converter.utils.ConverterUtils;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class ProductPageConverter implements UrlConverter {

    @Override
    public String convert(String url) {
        String sanitized = ConverterUtils.sanitizeUrl(url);

        try {
            URI uri = new URI(sanitized);
            String contentId = extractContentId(uri.getPath());
            if (contentId == null) {
                return ConverterUtils.homeDeepLink();
            }

            // start with ty://?Page=Product&ContentId={id}
            var sb = ConverterUtils.buildDeepLinkBuilder(
                    DeeplinkConstants.PAGE_PRODUCT,
                    DeeplinkConstants.DEEPLINK_PARAM_CONTENT_ID,
                    contentId
            );

            // parse & append boutiqueId / merchantId if present
            Map<String,String> params = ConverterUtils.parseQueryParams(uri.getQuery());
            if (params.containsKey(DeeplinkConstants.PARAM_BOUTIQUE_ID)) {
                sb.append(DeeplinkConstants.DEEPLINK_PARAM_CAMPAIGN_ID)
                        .append(ConverterUtils.encode(
                                params.get(DeeplinkConstants.PARAM_BOUTIQUE_ID)
                        ));
            }
            if (params.containsKey(DeeplinkConstants.PARAM_MERCHANT_ID)) {
                sb.append(DeeplinkConstants.DEEPLINK_PARAM_MERCHANT_ID)
                        .append(ConverterUtils.encode(
                                params.get(DeeplinkConstants.PARAM_MERCHANT_ID)
                        ));
            }

            return sb.toString();

        } catch (URISyntaxException e) {
            return ConverterUtils.homeDeepLink();
        }
    }

    private String extractContentId(String path) {
        // unchanged from before…
        int idx = path.lastIndexOf(DeeplinkConstants.PRODUCT_PATH_SEGMENT);
        if (idx >= 0) {
            return path
                    .substring(idx + DeeplinkConstants.PRODUCT_PATH_SEGMENT.length())
                    .split(DeeplinkConstants.PATH_SPLIT_REGEX)[0];
        }

        int lastDash = path.lastIndexOf(DeeplinkConstants.HYPHEN);
        if (lastDash >= 0
                && lastDash < path.length() - DeeplinkConstants.HYPHEN.length()) {
            String tail = path
                    .substring(lastDash + DeeplinkConstants.HYPHEN.length())
                    .split(DeeplinkConstants.PATH_SPLIT_REGEX)[0];
            if (tail.matches(DeeplinkConstants.DIGITS_REGEX)) {
                return tail;
            }
        }
        return null;
    }
}