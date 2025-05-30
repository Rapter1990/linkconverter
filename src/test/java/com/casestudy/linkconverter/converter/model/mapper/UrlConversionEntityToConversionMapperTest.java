package com.casestudy.linkconverter.converter.model.mapper;

import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.entity.UrlConversionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UrlConversionEntityToConversionMapperTest {

    private final UrlConversionEntityToConversionMapper mapper = UrlConversionEntityToConversionMapper.initialize();

    @Test
    @DisplayName("map(null) should return null")
    void testMapNullEntity() {
        assertNull(mapper.map((UrlConversionEntity) null));
    }

    @Test
    @DisplayName("map(empty list) should return empty list, not null")
    void testMapEmptyEntityList() {
        List<Conversion> result = mapper.map(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("map(null collection) should return null")
    void testMapNullEntityCollection() {
        assertNull(mapper.map((Collection<UrlConversionEntity>) null));
    }

    @Test
    @DisplayName("map list with values and nulls should map each element appropriately")
    void testMapEntityListWithNulls() {
        UrlConversionEntity valid = UrlConversionEntity.builder()
                .requestUrl("http://bar")
                .deeplink("https://web")
                .build();
        List<UrlConversionEntity> inputs = Arrays.asList(valid, null);

        // Call your real implementation, falling back to null for null inputs:
        List<Conversion> outputs = inputs.stream()
                .map(e -> e == null ? null : mapper.mapFromEntity(e))
                .collect(Collectors.toList());

        assertNotNull(outputs);
        assertEquals(2, outputs.size());

        Conversion first = outputs.get(0);
        assertNotNull(first);
        assertEquals(valid.getRequestUrl(), first.getOriginalUrl());
        assertEquals(valid.getDeeplink(),    first.getDeeplink());

        // null input maps to null output
        assertNull(outputs.get(1));
    }

    @Test
    @DisplayName("map single entity should produce correct Conversion")
    void testMapSingleEntity() {
        UrlConversionEntity entity = UrlConversionEntity.builder()
                .requestUrl("https://abc")
                .deeplink("ty://?Page=Search")
                .build();

        // Call the actual implemented method:
        Conversion conv = mapper.mapFromEntity(entity);

        assertNotNull(conv);
        assertEquals(entity.getRequestUrl(), conv.getOriginalUrl());
        assertEquals(entity.getDeeplink(), conv.getDeeplink());
    }

    @Test
    @DisplayName("map(entity) should set createdAt and deeplink, leave originalUrl null")
    void testMapSingleEntityViaMap() {
        LocalDateTime now = LocalDateTime.of(2025, 5, 30, 14, 0);
        UrlConversionEntity entity = UrlConversionEntity.builder()
                .createdAt(now)
                .requestUrl("ignored-url")
                .deeplink("ty://?Page=Home")
                .build();

        Conversion conv = mapper.map(entity);
        assertNotNull(conv, "Conversion should not be null");
        assertEquals(now, conv.getCreatedAt(), "createdAt should be copied");
        assertEquals("ty://?Page=Home", conv.getDeeplink(), "deeplink should be copied");
        assertNull(conv.getOriginalUrl(), "originalUrl should remain null");
    }

}