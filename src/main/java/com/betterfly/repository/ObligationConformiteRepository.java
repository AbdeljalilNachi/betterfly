package com.betterfly.repository;

import com.betterfly.domain.ObligationConformite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ObligationConformite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObligationConformiteRepository extends JpaRepository<ObligationConformite, Long> {
}
