package com.casestudy.linkconverter.converter.model;

import com.casestudy.linkconverter.common.model.BaseDomainModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Conversion extends BaseDomainModel {

    private String originalUrl;
    private String deeplink;

}
