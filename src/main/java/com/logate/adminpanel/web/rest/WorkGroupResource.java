package com.logate.adminpanel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.logate.adminpanel.domain.WorkGroup;
import com.logate.adminpanel.repository.WorkGroupRepository;
import com.logate.adminpanel.repository.search.WorkGroupSearchRepository;
import com.logate.adminpanel.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing WorkGroup.
 */
@RestController
@RequestMapping("/api")
public class WorkGroupResource {

    private final Logger log = LoggerFactory.getLogger(WorkGroupResource.class);

    @Inject
    private WorkGroupRepository workGroupRepository;

    @Inject
    private WorkGroupSearchRepository workGroupSearchRepository;

    /**
     * POST  /workGroups -> Create a new workGroup.
     */
    @RequestMapping(value = "/workGroups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WorkGroup> createWorkGroup(@Valid @RequestBody WorkGroup workGroup) throws URISyntaxException {
        log.debug("REST request to save WorkGroup : {}", workGroup);
        if (workGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("workGroup", "idexists", "A new workGroup cannot already have an ID")).body(null);
        }
        WorkGroup result = workGroupRepository.save(workGroup);
        workGroupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/workGroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("workGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workGroups -> Updates an existing workGroup.
     */
    @RequestMapping(value = "/workGroups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WorkGroup> updateWorkGroup(@Valid @RequestBody WorkGroup workGroup) throws URISyntaxException {
        log.debug("REST request to update WorkGroup : {}", workGroup);
        if (workGroup.getId() == null) {
            return createWorkGroup(workGroup);
        }
        WorkGroup result = workGroupRepository.save(workGroup);
        workGroupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("workGroup", workGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workGroups -> get all the workGroups.
     */
    @RequestMapping(value = "/workGroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<WorkGroup> getAllWorkGroups() {
        log.debug("REST request to get all WorkGroups");
        return workGroupRepository.findAll();
    }

    /**
     * GET  /workGroups/:id -> get the "id" workGroup.
     */
    @RequestMapping(value = "/workGroups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WorkGroup> getWorkGroup(@PathVariable Long id) {
        log.debug("REST request to get WorkGroup : {}", id);
        WorkGroup workGroup = workGroupRepository.findOne(id);
        return Optional.ofNullable(workGroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /workGroups/:id -> delete the "id" workGroup.
     */
    @RequestMapping(value = "/workGroups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWorkGroup(@PathVariable Long id) {
        log.debug("REST request to delete WorkGroup : {}", id);
        workGroupRepository.delete(id);
        workGroupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("workGroup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/workGroups/:query -> search for the workGroup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/workGroups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<WorkGroup> searchWorkGroups(@PathVariable String query) {
        log.debug("REST request to search WorkGroups for query {}", query);
        return StreamSupport
            .stream(workGroupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
