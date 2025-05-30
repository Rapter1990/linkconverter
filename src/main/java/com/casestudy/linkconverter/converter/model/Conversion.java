package com.casestudy.linkconverter.converter.model;

import com.casestudy.linkconverter.common.model.BaseDomainModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Domain model representing a URL conversion result.
 * <p>
 * Contains the original URL and the generated deep link.
 * Inherits creation timestamp from {@link BaseDomainModel}.
 * </p>
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Conversion extends BaseDomainModel {

    private String originalUrl;
    private String deeplink;

}
