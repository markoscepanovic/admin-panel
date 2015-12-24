package com.logate.adminpanel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.logate.adminpanel.domain.TestFeatures;
import com.logate.adminpanel.repository.TestFeaturesRepository;
import com.logate.adminpanel.repository.search.TestFeaturesSearchRepository;
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
 * REST controller for managing TestFeatures.
 */
@RestController
@RequestMapping("/api")
public class TestFeaturesResource {

    private final Logger log = LoggerFactory.getLogger(TestFeaturesResource.class);

    @Inject
    private TestFeaturesRepository testFeaturesRepository;

    @Inject
    private TestFeaturesSearchRepository testFeaturesSearchRepository;

    /**
     * POST  /testFeaturess -> Create a new testFeatures.
     */
    @RequestMapping(value = "/testFeaturess",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestFeatures> createTestFeatures(@Valid @RequestBody TestFeatures testFeatures) throws URISyntaxException {
        log.debug("REST request to save TestFeatures : {}", testFeatures);
        if (testFeatures.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("testFeatures", "idexists", "A new testFeatures cannot already have an ID")).body(null);
        }
        TestFeatures result = testFeaturesRepository.save(testFeatures);
        testFeaturesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/testFeaturess/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("testFeatures", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testFeaturess -> Updates an existing testFeatures.
     */
    @RequestMapping(value = "/testFeaturess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestFeatures> updateTestFeatures(@Valid @RequestBody TestFeatures testFeatures) throws URISyntaxException {
        log.debug("REST request to update TestFeatures : {}", testFeatures);
        if (testFeatures.getId() == null) {
            return createTestFeatures(testFeatures);
        }
        TestFeatures result = testFeaturesRepository.save(testFeatures);
        testFeaturesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("testFeatures", testFeatures.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testFeaturess -> get all the testFeaturess.
     */
    @RequestMapping(value = "/testFeaturess",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TestFeatures> getAllTestFeaturess() {
        log.debug("REST request to get all TestFeaturess");
        return testFeaturesRepository.findAll();
    }

    /**
     * GET  /testFeaturess/:id -> get the "id" testFeatures.
     */
    @RequestMapping(value = "/testFeaturess/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestFeatures> getTestFeatures(@PathVariable Long id) {
        log.debug("REST request to get TestFeatures : {}", id);
        TestFeatures testFeatures = testFeaturesRepository.findOne(id);
        return Optional.ofNullable(testFeatures)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /testFeaturess/:id -> delete the "id" testFeatures.
     */
    @RequestMapping(value = "/testFeaturess/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTestFeatures(@PathVariable Long id) {
        log.debug("REST request to delete TestFeatures : {}", id);
        testFeaturesRepository.delete(id);
        testFeaturesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testFeatures", id.toString())).build();
    }

    /**
     * SEARCH  /_search/testFeaturess/:query -> search for the testFeatures corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/testFeaturess/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TestFeatures> searchTestFeaturess(@PathVariable String query) {
        log.debug("REST request to search TestFeaturess for query {}", query);
        return StreamSupport
            .stream(testFeaturesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
