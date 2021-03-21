package com.betterfly.repository;

import com.betterfly.domain.NonConformite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the NonConformite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NonConformiteRepository extends JpaRepository<NonConformite, Long> {

    @Query("select nonConformite from NonConformite nonConformite where nonConformite.delegue.login = ?#{principal.username}")
    List<NonConformite> findByDelegueIsCurrentUser();
}
