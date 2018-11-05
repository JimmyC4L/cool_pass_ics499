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
    public List<SharedAccount> findAll() {
        return sharedAccountRepository.findAll();
    }

    @Override
    public List<SharedAccount> findWhere(SharedAccount sharedAccount){
        List<SharedAccount> sharedAccountList=sharedAccountRepository.findAll();
        List<SharedAccount> sharedAccountList1= new List<SharedAccount>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<SharedAccount> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(SharedAccount sharedAccount) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends SharedAccount> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends SharedAccount> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public SharedAccount get(int index) {
                return null;
            }

            @Override
            public SharedAccount set(int index, SharedAccount element) {
                return null;
            }

            @Override
            public void add(int index, SharedAccount element) {

            }

            @Override
            public SharedAccount remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<SharedAccount> listIterator() {
                return null;
            }

            @Override
            public ListIterator<SharedAccount> listIterator(int index) {
                return null;
            }

            @Override
            public List<SharedAccount> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        for(int i=0;i<sharedAccountList.size();++i){
            if(sharedAccountList.get(i).getEnvironment()==sharedAccount.getEnvironment()){
                sharedAccountList1.add(sharedAccountList.get(i));
            }
        }
    return sharedAccountList1;
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
