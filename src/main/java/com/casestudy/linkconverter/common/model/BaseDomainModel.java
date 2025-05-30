package com.casestudy.linkconverter.common.model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Abstract base class for domain models, providing a creation timestamp field.
 * Subclasses inherit a {@code createdAt} property that records when the domain object was instantiated or persisted.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseDomainModel {

    protected LocalDateTime createdAt;

}
