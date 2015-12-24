package com.logate.adminpanel.repository;

import com.logate.adminpanel.domain.TestFeatures;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TestFeatures entity.
 */
public interface TestFeaturesRepository extends JpaRepository<TestFeatures, Long> {

}
