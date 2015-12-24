package com.logate.adminpanel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.logate.adminpanel.domain.Department;
import com.logate.adminpanel.repository.DepartmentRepository;
import com.logate.adminpanel.repository.search.DepartmentSearchRepository;
import com.logate.adminpanel.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Department.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    @Inject
    private DepartmentRepository departmentRepository;

    @Inject
    private DepartmentSearchRepository departmentSearchRepository;

    /**
     * POST  /departments -> Create a new department.
     */
    @RequestMapping(value = "/departments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to save Department : {}", department);
        if (department.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("department", "idexists", "A new department cannot already have an ID")).body(null);
        }
        Department result = departmentRepository.save(department);
        departmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("department", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departments -> Updates an existing department.
     */
    @RequestMapping(value = "/departments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to update Department : {}", department);
        if (department.getId() == null) {
            return createDepartment(department);
        }
        Department result = departmentRepository.save(department);
        departmentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("department", department.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departments -> get all the departments.
     */
    @RequestMapping(value = "/departments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Department> getAllDepartments() {
        log.debug("REST request to get all Departments");
        return departmentRepository.findAll();
    }

    /**
     * GET  /departments/:id -> get the "id" department.
     */
    @RequestMapping(value = "/departments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        Department department = departmentRepository.findOne(id);
        return Optional.ofNullable(department)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /departments/:id -> delete the "id" department.
     */
    @RequestMapping(value = "/departments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        departmentRepository.delete(id);
        departmentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("department", id.toString())).build();
    }

    /**
     * SEARCH  /_search/departments/:query -> search for the department corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/departments/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Department> searchDepartments(@PathVariable String query) {
        log.debug("REST request to search Departments for query {}", query);
        return StreamSupport
            .stream(departmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
