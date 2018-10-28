package com.ics499.coolpass.repository;

import com.ics499.coolpass.domain.Environment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Environment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
}
