package com.casestudy.linkconverter.converter.repository;

import com.casestudy.linkconverter.converter.model.entity.UrlConversionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for persisting and retrieving {@link UrlConversionEntity} records.
 * <p>
 * Extends Spring Data JPA’s {@link JpaRepository} to provide CRUD operations
 * on URL conversion entries, keyed by their UUID string identifier.
 * </p>
 */
public interface UrlConversionRepository extends JpaRepository<UrlConversionEntity, String> {

}
