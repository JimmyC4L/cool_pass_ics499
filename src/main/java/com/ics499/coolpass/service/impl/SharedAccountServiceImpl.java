package com.ics499.coolpass.service.impl;

import com.ics499.coolpass.service.SharedAccountService;
import com.ics499.coolpass.domain.SharedAccount;
import com.ics499.coolpass.repository.SharedAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing SharedAccount.
 */
@Service
@Transactional
public class SharedAccountServiceImpl implements SharedAccountService {

    private final Logger log = LoggerFactory.getLogger(SharedAccountServiceImpl.class);

    private final SharedAccountRepository sharedAccountRepository;

    public SharedAccountServiceImpl(SharedAccountRepository sharedAccountRepository) {
        this.sharedAccountRepository = sharedAccountRepository;
    }

    /**
     * Save a sharedAccount.
     *
     * @param sharedAccount the entity to save
     * @return the persisted entity
     */
    @Override
    public SharedAccount save(SharedAccount sharedAccount) {
        log.debug("Request to save SharedAccount : {}", sharedAccount);        return sharedAccountRepository.save(sharedAccount);
    }

    /**
     * Get all the sharedAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SharedAccount> findAll(Pageable pageable) {
        log.debug("Request to get all SharedAccounts");
        return sharedAccountRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SharedAccount> findAll() {
        return sharedAccountRepository.findAll();
    }

    @Override
    public Page<SharedAccount> findAllByEnvironment(Pageable pageable, Long environmentId) {
        return sharedAccountRepository.findAllByEnvironment_IdOrderByEnvironment(pageable, environmentId);
    }

    @Override
    public Page<SharedAccount> findAllByLogin(Pageable pageable, String login) {
        return sharedAccountRepository.findAllByLoginOrderByLogin(pageable, login);
    }

    /**
     * Get one sharedAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SharedAccount> findOne(Long id) {
        log.debug("Request to get SharedAccount : {}", id);
        return sharedAccountRepository.findById(id);
    }

    /**
     * Delete the sharedAccount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SharedAccount : {}", id);
        sharedAccountRepository.deleteById(id);
    }
}
