package com.betterfly.repository;

import com.betterfly.domain.AnalyseSST;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AnalyseSST entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalyseSSTRepository extends JpaRepository<AnalyseSST, Long> {
}
