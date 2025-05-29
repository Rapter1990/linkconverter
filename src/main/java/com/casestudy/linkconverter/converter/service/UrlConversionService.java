package com.casestudy.linkconverter.converter.service;

import com.casestudy.linkconverter.converter.factory.DeepLinkToUrlConverter;
import com.casestudy.linkconverter.converter.factory.UrlConverter;
import com.casestudy.linkconverter.converter.factory.deeplinktourl.DeepLinkToUrlConverterFactory;
import com.casestudy.linkconverter.converter.factory.urltodeeplink.UrlToDeepLinkConverterFactory;
import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.entity.UrlConversionEntity;
import com.casestudy.linkconverter.converter.model.mapper.UrlConversionEntityToConversionMapper;
import com.casestudy.linkconverter.converter.repository.UrlConversionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlConversionService {

    private final UrlToDeepLinkConverterFactory urlToDeepLinkConverterFactory;
    private final DeepLinkToUrlConverterFactory deepLinkToUrlConverterFactory;
    private final UrlConversionRepository urlConversionRepository;
    private final UrlConversionEntityToConversionMapper urlConversionEntityToConversionMapper
            = UrlConversionEntityToConversionMapper.initialize();;

    @Transactional
    public Conversion convert(String url) {

        UrlConverter converter = urlToDeepLinkConverterFactory.getConverter(url);
        String deeplink = converter.convert(url);

        UrlConversionEntity record = UrlConversionEntity.builder()
                .requestUrl(url)
                .deeplink(deeplink)
                .build();

        UrlConversionEntity savedUrlConversion = urlConversionRepository.save(record);

        return urlConversionEntityToConversionMapper.mapFromEntity(savedUrlConversion);
    }

    @Transactional
    public Conversion convertDeepLink(String deeplink) {
        DeepLinkToUrlConverter conv = deepLinkToUrlConverterFactory.getConverter(deeplink);
        String webUrl = conv.convert(deeplink);

        UrlConversionEntity record = UrlConversionEntity.builder()
                .requestUrl(deeplink)
                .deeplink(webUrl)            // reusing your fields: `deeplink` now holds the web URL
                .build();

        UrlConversionEntity savedUrlConversion = urlConversionRepository.save(record);

        return urlConversionEntityToConversionMapper.mapFromEntity(savedUrlConversion);

    }

}
