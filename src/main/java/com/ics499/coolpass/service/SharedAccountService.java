package com.ics499.coolpass.service;

import com.ics499.coolpass.domain.SharedAccount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SharedAccount.
 */
public interface SharedAccountService {

    /**
     * Save a sharedAccount.
     *
     * @param sharedAccount the entity to save
     * @return the persisted entity
     */
    SharedAccount save(SharedAccount sharedAccount);

    /**
     * Get all the sharedAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SharedAccount> findAll(Pageable pageable);

    List<SharedAccount> findAll();

    Page<SharedAccount> findAllByEnvironment(Pageable pageable, Long environmentId);

    Page<SharedAccount> findAllByLogin(Pageable pageable, String login);

    /**
     * Get the "id" sharedAccount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SharedAccount> findOne(Long id);

    /**
     * Delete the "id" sharedAccount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
