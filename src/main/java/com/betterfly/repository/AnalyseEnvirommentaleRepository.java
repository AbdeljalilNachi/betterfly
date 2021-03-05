package com.betterfly.repository;

import com.betterfly.domain.AnalyseEnvirommentale;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AnalyseEnvirommentale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalyseEnvirommentaleRepository extends JpaRepository<AnalyseEnvirommentale, Long> {
}
