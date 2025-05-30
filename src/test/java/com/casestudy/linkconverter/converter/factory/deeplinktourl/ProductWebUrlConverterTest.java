package com.casestudy.linkconverter.converter.factory.deeplinktourl;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.exception.DeepLinkConversionException;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class ProductWebUrlConverterTest extends AbstractBaseServiceTest {

    @InjectMocks
    private ProductWebUrlConverter productWebUrlConverter;

    @Test
    @DisplayName("convert throws DeepLinkConversionException when missing ContentId")
    void convert_missingContentId_throwsException() {
        String deeplink = "ty://?Page=Product&CampaignId=BID&MerchantId=MID";
        DeepLinkConversionException ex = assertThrows(
                DeepLinkConversionException.class,
                () -> productWebUrlConverter.convert(deeplink)
        );
        assertTrue(ex.getMessage().contains("Missing ContentId"));
    }

    @Test
    @DisplayName("convert builds URL with only ContentId")
    void convert_withContentId_onlyContentId() {
        String contentId = "123";
        String deeplink = "ty://?Page=Product&ContentId=" + contentId;
        String expected = DeeplinkConstants.BASE_WEB_URL
                + DeeplinkConstants.PRODUCT_URL_PATH_PREFIX
                + contentId;
        assertEquals(expected, productWebUrlConverter.convert(deeplink));
    }

    @Test
    @DisplayName("convert includes boutiqueId when provided")
    void convert_withCampaignId_includesBoutiqueParam() {
        String contentId = "456";
        String boutiqueId = "BID";
        String deeplink = String.join("&",
                "ty://?Page=Product",
                "ContentId=" + contentId,
                "CampaignId=" + boutiqueId
        );
        String actual = productWebUrlConverter.convert(deeplink);

        String expected = new StringBuilder()
                .append(DeeplinkConstants.BASE_WEB_URL)
                .append(DeeplinkConstants.PRODUCT_URL_PATH_PREFIX)
                .append(contentId)
                .append(DeeplinkConstants.QUERY_DELIMITER)
                .append(DeeplinkConstants.PARAM_BOUTIQUE_ID)
                .append(DeeplinkConstants.KEY_VALUE_DELIMITER)
                .append(boutiqueId)
                .toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convert includes merchantId when provided without boutiqueId")
    void convert_withMerchantId_includesMerchantParam() {
        String contentId = "789";
        String merchantId = "MID";
        String deeplink = String.join("&",
                "ty://?Page=Product",
                "ContentId=" + contentId,
                "MerchantId=" + merchantId
        );
        String actual = productWebUrlConverter.convert(deeplink);

        String expected = new StringBuilder()
                .append(DeeplinkConstants.BASE_WEB_URL)
                .append(DeeplinkConstants.PRODUCT_URL_PATH_PREFIX)
                .append(contentId)
                .append(DeeplinkConstants.QUERY_DELIMITER)
                .append(DeeplinkConstants.PARAM_MERCHANT_ID)
                .append(DeeplinkConstants.KEY_VALUE_DELIMITER)
                .append(merchantId)
                .toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convert includes both boutiqueId and merchantId when provided")
    void convert_withBothOptionalParams_includesBoth() {
        String contentId = "321";
        String boutiqueId = "BID";
        String merchantId = "MID";
        String deeplink = String.join("&",
                "ty://?Page=Product",
                "ContentId=" + contentId,
                "CampaignId=" + boutiqueId,
                "MerchantId=" + merchantId
        );
        String actual = productWebUrlConverter.convert(deeplink);

        String expected = new StringBuilder()
                .append(DeeplinkConstants.BASE_WEB_URL)
                .append(DeeplinkConstants.PRODUCT_URL_PATH_PREFIX)
                .append(contentId)
                .append(DeeplinkConstants.QUERY_DELIMITER)
                .append(DeeplinkConstants.PARAM_BOUTIQUE_ID)
                .append(DeeplinkConstants.KEY_VALUE_DELIMITER)
                .append(boutiqueId)
                .append(DeeplinkConstants.PARAMETER_DELIMITER)
                .append(DeeplinkConstants.PARAM_MERCHANT_ID)
                .append(DeeplinkConstants.KEY_VALUE_DELIMITER)
                .append(merchantId)
                .toString();

        assertEquals(expected, actual);
    }

}