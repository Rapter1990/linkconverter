package com.casestudy.linkconverter.converter.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Request DTO for URL conversion operations.
 * <p>
 * Wraps the incoming URL to be converted.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRequest {

    @NotNull(message = "url must not be null")
    private String url;

}
