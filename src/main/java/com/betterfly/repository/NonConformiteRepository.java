package com.betterfly.repository;

import com.betterfly.domain.NonConformite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NonConformite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NonConformiteRepository extends JpaRepository<NonConformite, Long> {
}
