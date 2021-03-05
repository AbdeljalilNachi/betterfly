package com.betterfly.repository.search;

import com.betterfly.domain.Processus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Processus} entity.
 */
public interface ProcessusSearchRepository extends ElasticsearchRepository<Processus, Long> {
}
