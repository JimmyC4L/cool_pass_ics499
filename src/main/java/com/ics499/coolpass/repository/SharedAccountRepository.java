package com.ics499.coolpass.repository;

import com.ics499.coolpass.domain.SharedAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SharedAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SharedAccountRepository extends JpaRepository<SharedAccount, Long> {

}
