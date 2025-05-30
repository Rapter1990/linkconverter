package com.casestudy.linkconverter.converter.model.mapper;

import com.casestudy.linkconverter.converter.model.Conversion;
import com.casestudy.linkconverter.converter.model.dto.response.ConversionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConversionToConversionResponseMapperTest {

    private final ConversionToConversionResponseMapper mapper = ConversionToConversionResponseMapper.initialize();

    @Test
    @DisplayName("map(null) should return null")
    void testMapNullSingle() {
        assertNull(mapper.map((Conversion) null));
    }

    @Test
    @DisplayName("map(empty list) should return empty list, not null")
    void testMapEmptyList() {
        List<ConversionResponse> result = mapper.map(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("map(null collection) should return null")
    void testMapNullCollection() {
        assertNull(mapper.map((Collection<Conversion>) null));
    }

    @Test
    @DisplayName("map list with values and nulls should map each element appropriately")
    void testMapListWithNullElements() {
        Conversion valid = Conversion.builder()
                .originalUrl("http://foo")
                .deeplink("ty://?Page=Home")
                .build();
        List<Conversion> inputs = Arrays.asList(valid, null);
        List<ConversionResponse> outputs = mapper.map(inputs);
        assertNotNull(outputs);
        assertEquals(2, outputs.size());
        // first element mapped
        ConversionResponse first = outputs.get(0);
        assertNotNull(first);
        assertEquals(valid.getOriginalUrl(), first.getOriginalUrl());
        assertEquals(valid.getDeeplink(), first.getDeeplink());
        // second null input → null output
        assertNull(outputs.get(1));
    }

    @Test
    @DisplayName("map single Conversion should produce correct ConversionResponse")
    void testMapSingleConversion() {
        Conversion conv = Conversion.builder()
                .originalUrl("https://test.com")
                .deeplink("ty://?Page=Product")
                .build();
        ConversionResponse resp = mapper.map(conv);
        assertNotNull(resp);
        assertEquals(conv.getOriginalUrl(), resp.getOriginalUrl());
        assertEquals(conv.getDeeplink(), resp.getDeeplink());
    }
}