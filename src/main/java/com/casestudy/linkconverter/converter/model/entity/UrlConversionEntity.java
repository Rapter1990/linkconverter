package com.casestudy.linkconverter.converter.model.entity;

import com.casestudy.linkconverter.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "url-coversions")
public class UrlConversionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "request_url", nullable = false, length = 2048)
    private String requestUrl;

    @Column(name = "deeplink", nullable = false, length = 2048)
    private String deeplink;

}
