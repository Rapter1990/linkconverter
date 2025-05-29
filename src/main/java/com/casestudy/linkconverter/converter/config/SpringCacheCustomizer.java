package com.casestudy.linkconverter.converter.config;

import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringCacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

    /**
     * Customizes the provided ConcurrentMapCacheManager by setting cache names and allowing null values.
     *
     * @param cacheManager the ConcurrentMapCacheManager to customize.
     */
    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(List.of(DeeplinkConstants.URL_CONVERSION_CACHE));
        cacheManager.setAllowNullValues(false);
    }
}
