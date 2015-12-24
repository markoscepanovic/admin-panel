package com.logate.adminpanel.web.rest;

import com.logate.adminpanel.Application;
import com.logate.adminpanel.domain.WorkGroup;
import com.logate.adminpanel.repository.WorkGroupRepository;
import com.logate.adminpanel.repository.search.WorkGroupSearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the WorkGroupResource REST controller.
 *
 * @see WorkGroupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class WorkGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private WorkGroupRepository workGroupRepository;

    @Inject
    private WorkGroupSearchRepository workGroupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restWorkGroupMockMvc;

    private WorkGroup workGroup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkGroupResource workGroupResource = new WorkGroupResource();
        ReflectionTestUtils.setField(workGroupResource, "workGroupSearchRepository", workGroupSearchRepository);
        ReflectionTestUtils.setField(workGroupResource, "workGroupRepository", workGroupRepository);
        this.restWorkGroupMockMvc = MockMvcBuilders.standaloneSetup(workGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        workGroup = new WorkGroup();
        workGroup.setName(DEFAULT_NAME);
        workGroup.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createWorkGroup() throws Exception {
        int databaseSizeBeforeCreate = workGroupRepository.findAll().size();

        // Create the WorkGroup

        restWorkGroupMockMvc.perform(post("/api/workGroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workGroup)))
            .andExpect(status().isCreated());

        // Validate the WorkGroup in the database
        List<WorkGroup> workGroups = workGroupRepository.findAll();
        assertThat(workGroups).hasSize(databaseSizeBeforeCreate + 1);
        WorkGroup testWorkGroup = workGroups.get(workGroups.size() - 1);
        assertThat(testWorkGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWorkGroups() throws Exception {
        // Initialize the database
        workGroupRepository.saveAndFlush(workGroup);

        // Get all the workGroups
        restWorkGroupMockMvc.perform(get("/api/workGroups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getWorkGroup() throws Exception {
        // Initialize the database
        workGroupRepository.saveAndFlush(workGroup);

        // Get the workGroup
        restWorkGroupMockMvc.perform(get("/api/workGroups/{id}", workGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(workGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkGroup() throws Exception {
        // Get the workGroup
        restWorkGroupMockMvc.perform(get("/api/workGroups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkGroup() throws Exception {
        // Initialize the database
        workGroupRepository.saveAndFlush(workGroup);

        int databaseSizeBeforeUpdate = workGroupRepository.findAll().size();

        // Update the workGroup
        workGroup.setName(UPDATED_NAME);
        workGroup.setDescription(UPDATED_DESCRIPTION);

        restWorkGroupMockMvc.perform(put("/api/workGroups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workGroup)))
            .andExpect(status().isOk());

        // Validate the WorkGroup in the database
        List<WorkGroup> workGroups = workGroupRepository.findAll();
        assertThat(workGroups).hasSize(databaseSizeBeforeUpdate);
        WorkGroup testWorkGroup = workGroups.get(workGroups.size() - 1);
        assertThat(testWorkGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteWorkGroup() throws Exception {
        // Initialize the database
        workGroupRepository.saveAndFlush(workGroup);

        int databaseSizeBeforeDelete = workGroupRepository.findAll().size();

        // Get the workGroup
        restWorkGroupMockMvc.perform(delete("/api/workGroups/{id}", workGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkGroup> workGroups = workGroupRepository.findAll();
        assertThat(workGroups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
