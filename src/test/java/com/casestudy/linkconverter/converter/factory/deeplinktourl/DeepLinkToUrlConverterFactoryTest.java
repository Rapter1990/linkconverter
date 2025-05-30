package com.casestudy.linkconverter.converter.factory.deeplinktourl;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class DeepLinkToUrlConverterFactoryTest extends AbstractBaseServiceTest {

    @InjectMocks
    private DeepLinkToUrlConverterFactory deepLinkToUrlConverterFactory;

    @Test
    @DisplayName("No Page parameter → DefaultWebUrlConverter")
    void whenNoPageParam_thenDefaultConverter() {
        String deeplink = "ty://?";
        var converter = deepLinkToUrlConverterFactory.getConverter(deeplink);
        assertTrue(converter instanceof DefaultWebUrlConverter);
        assertEquals(DeeplinkConstants.BASE_WEB_URL, converter.convert(deeplink));
    }

    @Test
    @DisplayName("Page=Product → ProductWebUrlConverter")
    void whenPageProduct_thenProductConverter() {
        String contentId = "456";
        String deeplink = "ty://?Page=Product&ContentId=" + contentId;
        var converter = deepLinkToUrlConverterFactory.getConverter(deeplink);
        assertTrue(converter instanceof ProductWebUrlConverter);

        String expected = DeeplinkConstants.BASE_WEB_URL
                + DeeplinkConstants.PRODUCT_URL_PATH_PREFIX
                + contentId;
        assertEquals(expected, converter.convert(deeplink));
    }

    @Test
    @DisplayName("Product converter includes optional boutique and merchant IDs")
    void whenProductHasOptionalParams_thenIncludeThem() {
        String contentId = "789";
        String boutiqueId = "BID";
        String merchantId = "MID";
        String deeplink = String.join("&",
                "ty://?Page=Product",
                "ContentId=" + contentId,
                "CampaignId=" + boutiqueId,
                "MerchantId=" + merchantId
        );
        var converter = deepLinkToUrlConverterFactory.getConverter(deeplink);
        assertTrue(converter instanceof ProductWebUrlConverter);

        StringBuilder expected = new StringBuilder()
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
                .append(merchantId);

        assertEquals(expected.toString(), converter.convert(deeplink));
    }

    @Test
    @DisplayName("Page=Search → SearchWebUrlConverter")
    void whenPageSearch_thenSearchConverter() {
        String term = "laptop";
        String deeplink = "ty://?Page=Search&Query=" + term;
        var converter = deepLinkToUrlConverterFactory.getConverter(deeplink);
        assertTrue(converter instanceof SearchWebUrlConverter);

        String expected = DeeplinkConstants.BASE_WEB_URL
                + DeeplinkConstants.SEARCH_URL_PATH
                + term;
        assertEquals(expected, converter.convert(deeplink));
    }

}