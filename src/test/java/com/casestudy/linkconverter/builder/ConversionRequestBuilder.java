package com.casestudy.linkconverter.builder;

import com.casestudy.linkconverter.converter.model.dto.request.ConversionRequest;

public class ConversionRequestBuilder extends BaseBuilder<ConversionRequest> {

    public ConversionRequestBuilder() {
        super(ConversionRequest.class);
    }

    public ConversionRequestBuilder withValidFields() {
        return this.withUrl("https://www.example.com");
    }

    public ConversionRequestBuilder withUrl(String url) {
        data.setUrl(url);
        return this;
    }

}
