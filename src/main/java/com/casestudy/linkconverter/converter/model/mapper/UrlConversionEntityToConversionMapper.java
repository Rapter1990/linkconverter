package com.casestudy.linkconverter.converter.model.mapper;

import com.casestudy.linkconverter.common.model.mapper.BaseMapper;
import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.entity.UrlConversionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface to convert from {@link UrlConversionEntity} to {@link Conversion}.
 */
@Mapper
public interface UrlConversionEntityToConversionMapper extends BaseMapper<UrlConversionEntity, Conversion> {

    /**
     * Map a JPA entity to the Conversion domain model.
     *
     * @param entity the persisted entity
     * @return a Conversion object with originalUrl and deeplink fields
     */
    @Named("mapFromEntity")
    default Conversion mapFromEntity(UrlConversionEntity entity) {
        return Conversion.builder()
                .originalUrl(entity.getRequestUrl())
                .deeplink(entity.getDeeplink())
                .build();
    }

    /**
     * Obtain a singleton instance of the mapper.
     *
     * @return the mapper implementation
     */
    static UrlConversionEntityToConversionMapper initialize() {
        return Mappers.getMapper(UrlConversionEntityToConversionMapper.class);
    }
}
