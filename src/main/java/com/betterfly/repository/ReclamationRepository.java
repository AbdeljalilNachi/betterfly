package com.betterfly.repository;

import com.betterfly.domain.Reclamation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Reclamation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

    @Query("select reclamation from Reclamation reclamation where reclamation.delegue.login = ?#{principal.username}")
    List<Reclamation> findByDelegueIsCurrentUser();
}
