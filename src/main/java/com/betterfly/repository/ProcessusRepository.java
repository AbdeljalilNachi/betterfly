package com.betterfly.repository;

import com.betterfly.domain.Processus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Processus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessusRepository extends JpaRepository<Processus, Long> {
}
