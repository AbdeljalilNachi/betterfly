package com.betterfly.repository.search;

import com.betterfly.domain.Constat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Constat} entity.
 */
public interface ConstatSearchRepository extends ElasticsearchRepository<Constat, Long> {
}
