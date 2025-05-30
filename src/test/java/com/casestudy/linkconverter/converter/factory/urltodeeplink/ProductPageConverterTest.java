package com.casestudy.linkconverter.converter.factory.urltodeeplink;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.utils.ConverterUtils;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class ProductPageConverterTest extends AbstractBaseServiceTest {

    @InjectMocks
    private ProductPageConverter productPageConverter;

    @Test
    @DisplayName("convert returns home deep link when no content ID present")
    void convert_noContentId_returnsHomeDeepLink() {
        String url = "https://www.example.com/category";
        String expected = ConverterUtils.homeDeepLink();
        assertEquals(expected, productPageConverter.convert(url));
    }

    @Test
    @DisplayName("convert extracts ContentId from '-p-' segment")
    void convert_withProductSegment_extractsContentId() {
        String contentId = "1234";
        String url = "https://www.trendyol.com/brand-name-p-" + contentId;
        String expected = "ty://?Page=Product&ContentId=" + contentId;
        assertEquals(expected, productPageConverter.convert(url));
    }

    @Test
    @DisplayName("convert extracts trailing numeric ContentId")
    void convert_withTrailingId_extractsContentId() {
        String id = "5678";
        String url = "https://www.trendyol.com/some-path-" + id;
        String expected = "ty://?Page=Product&ContentId=" + id;
        assertEquals(expected, productPageConverter.convert(url));
    }

    @Test
    @DisplayName("convert includes boutiqueId when provided")
    void convert_withBoutiqueId_includesCampaignParam() {
        String contentId = "789";
        String boutiqueId = "BID";
        String url = String.format(
                "https://www.trendyol.com/item-p-%s?%s=%s",
                contentId,
                DeeplinkConstants.PARAM_BOUTIQUE_ID,
                boutiqueId
        );

        String actual = productPageConverter.convert(url);
        String expected = new StringBuilder()
                .append("ty://?Page=Product&ContentId=").append(contentId)
                .append("&CampaignId=").append(ConverterUtils.encode(boutiqueId))
                .toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convert includes merchantId when provided")
    void convert_withMerchantId_includesMerchantParam() {
        String contentId = "321";
        String merchantId = "MID";
        String url = String.format(
                "https://www.trendyol.com/item-p-%s?%s=%s",
                contentId,
                DeeplinkConstants.PARAM_MERCHANT_ID,
                merchantId
        );

        String actual = productPageConverter.convert(url);
        String expected = new StringBuilder()
                .append("ty://?Page=Product&ContentId=").append(contentId)
                .append("&MerchantId=").append(ConverterUtils.encode(merchantId))
                .toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convert includes both boutiqueId and merchantId in order")
    void convert_withBothOptionalParams_includesBothParams() {
        String contentId = "999";
        String boutiqueId = "BID";
        String merchantId = "MID";
        String url = String.format(
                "https://www.trendyol.com/item-p-%s?%s=%s&%s=%s",
                contentId,
                DeeplinkConstants.PARAM_BOUTIQUE_ID, boutiqueId,
                DeeplinkConstants.PARAM_MERCHANT_ID, merchantId
        );

        String actual = productPageConverter.convert(url);
        String expected = new StringBuilder()
                .append("ty://?Page=Product&ContentId=").append(contentId)
                .append("&CampaignId=").append(ConverterUtils.encode(boutiqueId))
                .append("&MerchantId=").append(ConverterUtils.encode(merchantId))
                .toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convert returns home deep link on URISyntaxException")
    void convert_malformedUrl_returnsHomeDeepLink() {
        String badUrl = "://not a url";
        String expected = ConverterUtils.homeDeepLink();
        assertEquals(expected, productPageConverter.convert(badUrl));
    }

}