package com.logate.adminpanel.repository.search;

import com.logate.adminpanel.domain.TestFeatures;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TestFeatures entity.
 */
public interface TestFeaturesSearchRepository extends ElasticsearchRepository<TestFeatures, Long> {
}
