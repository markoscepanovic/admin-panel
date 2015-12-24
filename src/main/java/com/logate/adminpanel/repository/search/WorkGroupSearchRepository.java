package com.logate.adminpanel.repository.search;

import com.logate.adminpanel.domain.WorkGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the WorkGroup entity.
 */
public interface WorkGroupSearchRepository extends ElasticsearchRepository<WorkGroup, Long> {
}
