package com.ics499.coolpass.repository;

import com.ics499.coolpass.domain.SharedAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the SharedAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SharedAccountRepository extends JpaRepository<SharedAccount, Long> {

    Page<SharedAccount> findAllByEnvironment_IdOrderByEnvironment(Pageable pageable, Long environmentId);

    Page<SharedAccount> findAllByLoginOrderByLogin(Pageable pageable, String login);

}
