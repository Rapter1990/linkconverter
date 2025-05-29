package com.casestudy.linkconverter.converter.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRequest {

    @NotNull(message = "url must not be null")
    private String url;

}
