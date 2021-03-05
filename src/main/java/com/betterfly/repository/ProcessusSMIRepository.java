package com.betterfly.repository;

import com.betterfly.domain.ProcessusSMI;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProcessusSMI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessusSMIRepository extends JpaRepository<ProcessusSMI, Long> {
}
