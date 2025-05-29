package com.casestudy.linkconverter.converter.repository;

import com.casestudy.linkconverter.converter.model.entity.UrlConversionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlConversionRepository extends JpaRepository<UrlConversionEntity, String> {

}
