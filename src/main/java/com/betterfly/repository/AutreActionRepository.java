package com.betterfly.repository;

import com.betterfly.domain.AutreAction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AutreAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutreActionRepository extends JpaRepository<AutreAction, Long> {

    @Query("select autreAction from AutreAction autreAction where autreAction.delegue.login = ?#{principal.username}")
    List<AutreAction> findByDelegueIsCurrentUser();
}
