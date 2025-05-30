package com.casestudy.linkconverter.converter.factory.deeplinktourl;

import com.casestudy.linkconverter.converter.exception.DeepLinkConversionException;
import com.casestudy.linkconverter.converter.utils.ConverterUtils;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;

import java.util.Map;

/**
 * Converter for product deep links into product page web URLs.
 */
public class ProductWebUrlConverter implements DeepLinkToUrlConverter {

    /**
     * Convert a product deep link into its corresponding web URL.
     * <p>
     * Ensures ContentId is present and appends optional boutique
     * and merchant IDs if provided.
     * </p>
     *
     * @param deeplink the product deep link to convert
     * @return the full product page web URL
     * @throws DeepLinkConversionException if the contentId parameter is missing
     */
    @Override
    public String convert(String deeplink) {
        // 1) sanitize + pull query
        String sanitized = ConverterUtils.sanitizeUrl(deeplink);
        String query     = ConverterUtils.extractQuery(sanitized);
        Map<String,String> params = ConverterUtils.parseQueryParams(query);

        // 2) required: ContentId
        String contentId = params.get(DeeplinkConstants.DL_PARAM_CONTENT_ID);
        if (contentId == null) {
            throw new DeepLinkConversionException(
                    "Missing " + DeeplinkConstants.DL_PARAM_CONTENT_ID + " in " + deeplink
            );
        }

        // 3) build base URL
        StringBuilder url = new StringBuilder()
                .append(DeeplinkConstants.BASE_WEB_URL)
                .append(DeeplinkConstants.PRODUCT_URL_PATH_PREFIX)
                .append(contentId);

        // 4) optional boutiqueId → ?boutiqueId=…
        if (params.containsKey(DeeplinkConstants.DL_PARAM_CAMPAIGN_ID)) {
            url.append(DeeplinkConstants.QUERY_DELIMITER)
                    .append(DeeplinkConstants.PARAM_BOUTIQUE_ID)
                    .append(DeeplinkConstants.KEY_VALUE_DELIMITER)
                    .append(params.get(DeeplinkConstants.DL_PARAM_CAMPAIGN_ID));
        }

        // 5) optional merchantId → &merchantId=…
        if (params.containsKey(DeeplinkConstants.DL_PARAM_MERCHANT_ID)) {
            boolean hasQuestion = url.indexOf(DeeplinkConstants.QUERY_DELIMITER) >= 0;
            url.append(hasQuestion
                            ? DeeplinkConstants.PARAMETER_DELIMITER
                            : DeeplinkConstants.QUERY_DELIMITER)
                    .append(DeeplinkConstants.PARAM_MERCHANT_ID)
                    .append(DeeplinkConstants.KEY_VALUE_DELIMITER)
                    .append(params.get(DeeplinkConstants.DL_PARAM_MERCHANT_ID));
        }

        return url.toString();
    }

}