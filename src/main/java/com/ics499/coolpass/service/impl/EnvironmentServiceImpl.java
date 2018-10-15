package com.ics499.coolpass.service.impl;

import com.ics499.coolpass.service.EnvironmentService;
import com.ics499.coolpass.domain.Environment;
import com.ics499.coolpass.repository.EnvironmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Environment.
 */
@Service
@Transactional
public class EnvironmentServiceImpl implements EnvironmentService {

    private final Logger log = LoggerFactory.getLogger(EnvironmentServiceImpl.class);

    private final EnvironmentRepository environmentRepository;

    public EnvironmentServiceImpl(EnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
    }

    /**
     * Save a environment.
     *
     * @param environment the entity to save
     * @return the persisted entity
     */
    @Override
    public Environment save(Environment environment) {
        log.debug("Request to save Environment : {}", environment);        return environmentRepository.save(environment);
    }

    /**
     * Get all the environments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Environment> findAll(Pageable pageable) {
        log.debug("Request to get all Environments");
        return environmentRepository.findAll(pageable);
    }

    @Override
    public List<Environment> findAll() {
        return environmentRepository.findAll();
    }


    /**
     * Get one environment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Environment> findOne(Long id) {
        log.debug("Request to get Environment : {}", id);
        return environmentRepository.findById(id);
    }

    /**
     * Delete the environment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Environment : {}", id);
        environmentRepository.deleteById(id);
    }
}
