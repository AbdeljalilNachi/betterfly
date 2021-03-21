package com.betterfly.repository;

import com.betterfly.domain.ProcessusSMI;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ProcessusSMI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessusSMIRepository extends JpaRepository<ProcessusSMI, Long> {

    @Query("select processusSMI from ProcessusSMI processusSMI where processusSMI.pilote.login = ?#{principal.username}")
    List<ProcessusSMI> findByPiloteIsCurrentUser();
}
