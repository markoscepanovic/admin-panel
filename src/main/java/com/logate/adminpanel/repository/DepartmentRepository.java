package com.logate.adminpanel.repository;

import com.logate.adminpanel.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Department entity.
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
