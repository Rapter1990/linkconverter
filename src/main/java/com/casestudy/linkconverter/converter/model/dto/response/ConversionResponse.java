package com.casestudy.linkconverter.converter.model.dto.response;

import lombok.*;

/**
 * Response DTO for URL conversion operations.
 * <p>
 * Returns the original URL and its corresponding deep link.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse {

    private String originalUrl;
    private String deeplink;

}
