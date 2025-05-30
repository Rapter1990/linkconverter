package com.casestudy.linkconverter.converter.service;

import com.casestudy.linkconverter.converter.factory.deeplinktourl.DeepLinkToUrlConverter;
import com.casestudy.linkconverter.converter.factory.urltodeeplink.UrlConverter;
import com.casestudy.linkconverter.converter.factory.deeplinktourl.DeepLinkToUrlConverterFactory;
import com.casestudy.linkconverter.converter.factory.urltodeeplink.UrlToDeepLinkConverterFactory;
import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.entity.UrlConversionEntity;
import com.casestudy.linkconverter.converter.model.mapper.UrlConversionEntityToConversionMapper;
import com.casestudy.linkconverter.converter.repository.UrlConversionRepository;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.springframework.cache.annotation.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service managing URL ↔ deep-link conversions.
 * <p>
 * Provides methods to convert web URLs to deep links and vice versa,
 * persisting each conversion and caching results for performance.
 * </p>
 *
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = DeeplinkConstants.URL_CONVERSION_CACHE)
public class UrlConversionService {

    private final UrlToDeepLinkConverterFactory urlToDeepLinkConverterFactory;
    private final DeepLinkToUrlConverterFactory deepLinkToUrlConverterFactory;
    private final UrlConversionRepository urlConversionRepository;
    private final UrlConversionEntityToConversionMapper urlConversionEntityToConversionMapper
            = UrlConversionEntityToConversionMapper.initialize();

    /**
     * Convert a web URL into a deep link, save the record, and cache by URL.
     *
     * @param url the web URL to convert
     * @return a Conversion object containing original URL and generated deep link
     */
    @Cacheable(key = "#url")
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

    /**
     * Convert a deep link into a web URL, save the record, and cache by deep link.
     *
     * @param deeplink the deep link to convert
     * @return a Conversion object containing deep link and resulting web URL
     */
    @Cacheable(key = "#deeplink")
    @Transactional
    public Conversion convertDeepLink(String deeplink) {
        DeepLinkToUrlConverter conv = deepLinkToUrlConverterFactory.getConverter(deeplink);
        String webUrl = conv.convert(deeplink);

        UrlConversionEntity record = UrlConversionEntity.builder()
                .requestUrl(deeplink)
                .deeplink(webUrl)
                .build();

        UrlConversionEntity savedUrlConversion = urlConversionRepository.save(record);

        return urlConversionEntityToConversionMapper.mapFromEntity(savedUrlConversion);

    }

}
