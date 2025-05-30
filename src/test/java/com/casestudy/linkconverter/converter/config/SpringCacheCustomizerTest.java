package com.casestudy.linkconverter.converter.config;

import com.casestudy.linkconverter.base.AbstractBaseServiceTest;
import com.casestudy.linkconverter.converter.utils.DeeplinkConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class SpringCacheCustomizerTest extends AbstractBaseServiceTest {

    @InjectMocks
    private SpringCacheCustomizer customizer;

    @Mock
    private ConcurrentMapCacheManager cacheManager;

    @Test
    @DisplayName("customize should set cache names and disable null values")
    void customize_setsCacheNamesAndDisallowsNull() {

        // When
        customizer.customize(cacheManager);

        // Then
        verify(cacheManager).setCacheNames(List.of(DeeplinkConstants.URL_CONVERSION_CACHE));
        verify(cacheManager).setAllowNullValues(false);
        verifyNoMoreInteractions(cacheManager);

    }

}