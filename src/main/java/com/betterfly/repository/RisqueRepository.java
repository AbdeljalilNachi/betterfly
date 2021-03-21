package com.betterfly.repository;

import com.betterfly.domain.Risque;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Risque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RisqueRepository extends JpaRepository<Risque, Long> {

    @Query("select risque from Risque risque where risque.delegue.login = ?#{principal.username}")
    List<Risque> findByDelegueIsCurrentUser();
}
