package com.casestudy.linkconverter.converter.model.mapper;

import com.casestudy.linkconverter.common.model.mapper.BaseMapper;
import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.dto.response.ConversionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConversionToConversionResponseMapper extends BaseMapper<Conversion, ConversionResponse> {

    @Named("mapToResponse")
    default ConversionResponse mapToResponse(Conversion conversion) {
        return ConversionResponse.builder()
                .originalUrl(conversion.getOriginalUrl())
                .deeplink(conversion.getDeeplink())
                .build();
    }

    static ConversionToConversionResponseMapper initialize() {
        return Mappers.getMapper(ConversionToConversionResponseMapper.class);
    }

}
