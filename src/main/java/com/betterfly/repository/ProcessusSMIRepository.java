package com.betterfly.repository;

import com.betterfly.domain.ProcessusSMI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ProcessusSMI entity.
 */
@Repository
public interface ProcessusSMIRepository extends JpaRepository<ProcessusSMI, Long> {

    @Query(value = "select distinct processusSMI from ProcessusSMI processusSMI left join fetch processusSMI.indicateurs",
        countQuery = "select count(distinct processusSMI) from ProcessusSMI processusSMI")
    Page<ProcessusSMI> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct processusSMI from ProcessusSMI processusSMI left join fetch processusSMI.indicateurs")
    List<ProcessusSMI> findAllWithEagerRelationships();

    @Query("select processusSMI from ProcessusSMI processusSMI left join fetch processusSMI.indicateurs where processusSMI.id =:id")
    Optional<ProcessusSMI> findOneWithEagerRelationships(@Param("id") Long id);
}
