package com.casestudy.linkconverter.logging.service.impl;

import com.casestudy.linkconverter.logging.entity.LogEntity;
import com.casestudy.linkconverter.logging.repository.LogRepository;
import com.casestudy.linkconverter.logging.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementation of the {@link LogService} interface.
 * Handles persistence of log entries to the database.
 */
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    /**
     * Saves the given {@link LogEntity} to the database with a current timestamp.
     *
     * @param logEntity the log to persist
     */
    @Override
    public void saveLogToDatabase(final LogEntity logEntity) {
        logEntity.setTime(LocalDateTime.now());
        logRepository.save(logEntity);
    }

}
