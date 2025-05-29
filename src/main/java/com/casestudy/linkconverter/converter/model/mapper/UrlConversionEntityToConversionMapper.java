package com.casestudy.linkconverter.converter.model.mapper;

import com.casestudy.linkconverter.common.model.mapper.BaseMapper;
import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.entity.UrlConversionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UrlConversionEntityToConversionMapper extends BaseMapper<UrlConversionEntity, Conversion> {

    /**
     * Maps a UrlConversionEntity to a Conversion, copying the original URL
     * and the generated deep link.
     */
    @Named("mapFromEntity")
    default Conversion mapFromEntity(UrlConversionEntity entity) {
        return Conversion.builder()
                .originalUrl(entity.getRequestUrl())
                .deeplink(entity.getDeeplink())
                .build();
    }

    /**
     * Returns a singleton mapper instance.
     */
    static UrlConversionEntityToConversionMapper initialize() {
        return Mappers.getMapper(UrlConversionEntityToConversionMapper.class);
    }
}
