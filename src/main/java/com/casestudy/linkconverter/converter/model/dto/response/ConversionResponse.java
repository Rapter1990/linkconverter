package com.casestudy.linkconverter.converter.model.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse {

    private String originalUrl;
    private String deeplink;

}
