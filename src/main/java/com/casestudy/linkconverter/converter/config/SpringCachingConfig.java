package com.casestudy.linkconverter.converter.config;

import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration for application caching.
 * <p>
 * Enables Spring's annotation-driven cache management and defines
 * the CacheManager bean responsible for managing application caches.
 * </p>
 *
 * <p>This configuration creates a {@link ConcurrentMapCacheManager} with
 * a cache name defined by {@link DeeplinkConstants#URL_CONVERSION_CACHE}.
 * </p>
 *
 */
@Configuration
@EnableCaching
public class SpringCachingConfig {

    /**
     * Creates and configures a CacheManager bean.
     * The CacheManager will manage caches with the specified cache name.
     *
     * @return a configured instance of ConcurrentMapCacheManager.
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(DeeplinkConstants.URL_CONVERSION_CACHE);
    }

}
