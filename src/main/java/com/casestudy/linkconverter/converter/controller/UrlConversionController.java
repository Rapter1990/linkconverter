package com.casestudy.linkconverter.converter.controller;

import com.casestudy.linkconverter.common.model.dto.response.CustomResponse;
import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.dto.request.ConversionRequest;
import com.casestudy.linkconverter.converter.model.dto.response.ConversionResponse;
import com.casestudy.linkconverter.converter.model.mapper.ConversionToConversionResponseMapper;
import com.casestudy.linkconverter.converter.service.UrlConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Url Conversion", description = "Handles operations for url conversion")
public class UrlConversionController {

    private final UrlConversionService urlConversionService;

    private final ConversionToConversionResponseMapper conversionToConversionResponseMapper
            = ConversionToConversionResponseMapper.initialize();

    @Operation(
            summary     = "Convert a web URL into a deep link",
            description = "Accepts a standard web URL and returns the corresponding mobile deep link."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully converted to deep link"),
            @ApiResponse(responseCode = "400", description = "Invalid or malformed URL provided")
    })
    @PostMapping("/convert/url")
    public CustomResponse<ConversionResponse> convertUrl(@RequestBody ConversionRequest request) {
        Conversion convert = urlConversionService.convert(request.getUrl());
        ConversionResponse conversionResponse = conversionToConversionResponseMapper.mapToResponse(convert);
        return CustomResponse.successOf(conversionResponse);
    }

    @Operation(
            summary     = "Convert a deep link into a web URL",
            description = "Accepts a mobile deep link and returns the corresponding standard web URL."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully converted to web URL"),
            @ApiResponse(responseCode = "400", description = "Invalid or malformed deep link provided")
    })
    @PostMapping("/convert/deeplink")
    public CustomResponse<ConversionResponse> convertDeeplink(@RequestBody ConversionRequest request) {
        Conversion convert = urlConversionService.convertDeepLink(request.getUrl());
        ConversionResponse conversionResponse = conversionToConversionResponseMapper.mapToResponse(convert);
        return CustomResponse.successOf(conversionResponse);
    }

}
