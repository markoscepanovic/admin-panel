package com.logate.adminpanel.repository;

import com.logate.adminpanel.domain.WorkGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the WorkGroup entity.
 */
public interface WorkGroupRepository extends JpaRepository<WorkGroup, Long> {

}
