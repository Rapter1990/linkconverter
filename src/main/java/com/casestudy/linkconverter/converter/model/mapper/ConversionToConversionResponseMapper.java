package com.casestudy.linkconverter.converter.model.mapper;

import com.casestudy.linkconverter.common.model.mapper.BaseMapper;
import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.dto.response.ConversionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface to convert from {@link Conversion} to {@link ConversionResponse}.
 */
@Mapper
public interface ConversionToConversionResponseMapper extends BaseMapper<Conversion, ConversionResponse> {

    /**
     * Map a Conversion domain object to a ConversionResponse DTO.
     *
     * @param conversion the domain object containing URL data
     * @return a response DTO with originalUrl and deeplink
     */
    @Named("mapToResponse")
    default ConversionResponse mapToResponse(Conversion conversion) {
        return ConversionResponse.builder()
                .originalUrl(conversion.getOriginalUrl())
                .deeplink(conversion.getDeeplink())
                .build();
    }

    /**
     * Obtain a singleton instance of the mapper.
     *
     * @return the mapper implementation
     */
    static ConversionToConversionResponseMapper initialize() {
        return Mappers.getMapper(ConversionToConversionResponseMapper.class);
    }

}
