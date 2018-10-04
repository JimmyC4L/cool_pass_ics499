package com.ics499.coolpass.service;

import com.ics499.coolpass.domain.Environment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Environment.
 */
public interface EnvironmentService {

    /**
     * Save a environment.
     *
     * @param environment the entity to save
     * @return the persisted entity
     */
    Environment save(Environment environment);

    /**
     * Get all the environments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Environment> findAll(Pageable pageable);


    /**
     * Get the "id" environment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Environment> findOne(Long id);

    /**
     * Delete the "id" environment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
