package com.betterfly.repository;

import com.betterfly.domain.Constat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Constat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConstatRepository extends JpaRepository<Constat, Long> {
}
