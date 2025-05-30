package com.casestudy.linkconverter.logging.repository;

import com.casestudy.linkconverter.logging.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link LogEntity} persistence operations.
 * Extends Spring Data {@link JpaRepository} to provide CRUD support.
 */
public interface LogRepository extends JpaRepository<LogEntity,String> {

}
