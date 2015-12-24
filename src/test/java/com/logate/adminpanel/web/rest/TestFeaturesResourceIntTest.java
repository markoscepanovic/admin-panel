package com.logate.adminpanel.web.rest;

import com.logate.adminpanel.Application;
import com.logate.adminpanel.domain.TestFeatures;
import com.logate.adminpanel.domain.enumeration.Satus;
import com.logate.adminpanel.repository.TestFeaturesRepository;
import com.logate.adminpanel.repository.search.TestFeaturesSearchRepository;
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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TestFeaturesResource REST controller.
 *
 * @see TestFeaturesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TestFeaturesResourceIntTest {

    private static final String DEFAULT_VALIDATION_STRING_FIELD = "AA";
    private static final String UPDATED_VALIDATION_STRING_FIELD = "BB";
    private static final String DEFAULT_VALIDATION_REG_EXP_FIELD = "AAAAA";
    private static final String UPDATED_VALIDATION_REG_EXP_FIELD = "BBBBB";

    private static final byte[] DEFAULT_BLOB_FIELD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BLOB_FIELD = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BLOB_FIELD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BLOB_FIELD_CONTENT_TYPE = "image/png";


    private static final Satus DEFAULT_ENUM_FIELD = Satus.open;
    private static final Satus UPDATED_ENUM_FIELD = Satus.inProgress;

    @Inject
    private TestFeaturesRepository testFeaturesRepository;

    @Inject
    private TestFeaturesSearchRepository testFeaturesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTestFeaturesMockMvc;

    private TestFeatures testFeatures;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestFeaturesResource testFeaturesResource = new TestFeaturesResource();
        ReflectionTestUtils.setField(testFeaturesResource, "testFeaturesSearchRepository", testFeaturesSearchRepository);
        ReflectionTestUtils.setField(testFeaturesResource, "testFeaturesRepository", testFeaturesRepository);
        this.restTestFeaturesMockMvc = MockMvcBuilders.standaloneSetup(testFeaturesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        testFeatures = new TestFeatures();
        testFeatures.setValidationStringField(DEFAULT_VALIDATION_STRING_FIELD);
        testFeatures.setValidationRegExpField(DEFAULT_VALIDATION_REG_EXP_FIELD);
        testFeatures.setBlobField(DEFAULT_BLOB_FIELD);
        testFeatures.setBlobFieldContentType(DEFAULT_BLOB_FIELD_CONTENT_TYPE);
        testFeatures.setEnumField(DEFAULT_ENUM_FIELD);
    }

    @Test
    @Transactional
    public void createTestFeatures() throws Exception {
        int databaseSizeBeforeCreate = testFeaturesRepository.findAll().size();

        // Create the TestFeatures

        restTestFeaturesMockMvc.perform(post("/api/testFeaturess")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testFeatures)))
            .andExpect(status().isCreated());

        // Validate the TestFeatures in the database
        List<TestFeatures> testFeaturess = testFeaturesRepository.findAll();
        assertThat(testFeaturess).hasSize(databaseSizeBeforeCreate + 1);
        TestFeatures testTestFeatures = testFeaturess.get(testFeaturess.size() - 1);
        assertThat(testTestFeatures.getValidationStringField()).isEqualTo(DEFAULT_VALIDATION_STRING_FIELD);
        assertThat(testTestFeatures.getValidationRegExpField()).isEqualTo(DEFAULT_VALIDATION_REG_EXP_FIELD);
        assertThat(testTestFeatures.getBlobField()).isEqualTo(DEFAULT_BLOB_FIELD);
        assertThat(testTestFeatures.getBlobFieldContentType()).isEqualTo(DEFAULT_BLOB_FIELD_CONTENT_TYPE);
        assertThat(testTestFeatures.getEnumField()).isEqualTo(DEFAULT_ENUM_FIELD);
    }

    @Test
    @Transactional
    public void checkValidationStringFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = testFeaturesRepository.findAll().size();
        // set the field null
        testFeatures.setValidationStringField(null);

        // Create the TestFeatures, which fails.

        restTestFeaturesMockMvc.perform(post("/api/testFeaturess")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testFeatures)))
            .andExpect(status().isBadRequest());

        List<TestFeatures> testFeaturess = testFeaturesRepository.findAll();
        assertThat(testFeaturess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBlobFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = testFeaturesRepository.findAll().size();
        // set the field null
        testFeatures.setBlobField(null);

        // Create the TestFeatures, which fails.

        restTestFeaturesMockMvc.perform(post("/api/testFeaturess")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testFeatures)))
            .andExpect(status().isBadRequest());

        List<TestFeatures> testFeaturess = testFeaturesRepository.findAll();
        assertThat(testFeaturess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnumFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = testFeaturesRepository.findAll().size();
        // set the field null
        testFeatures.setEnumField(null);

        // Create the TestFeatures, which fails.

        restTestFeaturesMockMvc.perform(post("/api/testFeaturess")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testFeatures)))
            .andExpect(status().isBadRequest());

        List<TestFeatures> testFeaturess = testFeaturesRepository.findAll();
        assertThat(testFeaturess).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTestFeaturess() throws Exception {
        // Initialize the database
        testFeaturesRepository.saveAndFlush(testFeatures);

        // Get all the testFeaturess
        restTestFeaturesMockMvc.perform(get("/api/testFeaturess?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testFeatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].validationStringField").value(hasItem(DEFAULT_VALIDATION_STRING_FIELD.toString())))
            .andExpect(jsonPath("$.[*].validationRegExpField").value(hasItem(DEFAULT_VALIDATION_REG_EXP_FIELD.toString())))
            .andExpect(jsonPath("$.[*].blobFieldContentType").value(hasItem(DEFAULT_BLOB_FIELD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blobField").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_FIELD))))
            .andExpect(jsonPath("$.[*].enumField").value(hasItem(DEFAULT_ENUM_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getTestFeatures() throws Exception {
        // Initialize the database
        testFeaturesRepository.saveAndFlush(testFeatures);

        // Get the testFeatures
        restTestFeaturesMockMvc.perform(get("/api/testFeaturess/{id}", testFeatures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(testFeatures.getId().intValue()))
            .andExpect(jsonPath("$.validationStringField").value(DEFAULT_VALIDATION_STRING_FIELD.toString()))
            .andExpect(jsonPath("$.validationRegExpField").value(DEFAULT_VALIDATION_REG_EXP_FIELD.toString()))
            .andExpect(jsonPath("$.blobFieldContentType").value(DEFAULT_BLOB_FIELD_CONTENT_TYPE))
            .andExpect(jsonPath("$.blobField").value(Base64Utils.encodeToString(DEFAULT_BLOB_FIELD)))
            .andExpect(jsonPath("$.enumField").value(DEFAULT_ENUM_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTestFeatures() throws Exception {
        // Get the testFeatures
        restTestFeaturesMockMvc.perform(get("/api/testFeaturess/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestFeatures() throws Exception {
        // Initialize the database
        testFeaturesRepository.saveAndFlush(testFeatures);

        int databaseSizeBeforeUpdate = testFeaturesRepository.findAll().size();

        // Update the testFeatures
        testFeatures.setValidationStringField(UPDATED_VALIDATION_STRING_FIELD);
        testFeatures.setValidationRegExpField(UPDATED_VALIDATION_REG_EXP_FIELD);
        testFeatures.setBlobField(UPDATED_BLOB_FIELD);
        testFeatures.setBlobFieldContentType(UPDATED_BLOB_FIELD_CONTENT_TYPE);
        testFeatures.setEnumField(UPDATED_ENUM_FIELD);

        restTestFeaturesMockMvc.perform(put("/api/testFeaturess")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testFeatures)))
            .andExpect(status().isOk());

        // Validate the TestFeatures in the database
        List<TestFeatures> testFeaturess = testFeaturesRepository.findAll();
        assertThat(testFeaturess).hasSize(databaseSizeBeforeUpdate);
        TestFeatures testTestFeatures = testFeaturess.get(testFeaturess.size() - 1);
        assertThat(testTestFeatures.getValidationStringField()).isEqualTo(UPDATED_VALIDATION_STRING_FIELD);
        assertThat(testTestFeatures.getValidationRegExpField()).isEqualTo(UPDATED_VALIDATION_REG_EXP_FIELD);
        assertThat(testTestFeatures.getBlobField()).isEqualTo(UPDATED_BLOB_FIELD);
        assertThat(testTestFeatures.getBlobFieldContentType()).isEqualTo(UPDATED_BLOB_FIELD_CONTENT_TYPE);
        assertThat(testTestFeatures.getEnumField()).isEqualTo(UPDATED_ENUM_FIELD);
    }

    @Test
    @Transactional
    public void deleteTestFeatures() throws Exception {
        // Initialize the database
        testFeaturesRepository.saveAndFlush(testFeatures);

        int databaseSizeBeforeDelete = testFeaturesRepository.findAll().size();

        // Get the testFeatures
        restTestFeaturesMockMvc.perform(delete("/api/testFeaturess/{id}", testFeatures.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TestFeatures> testFeaturess = testFeaturesRepository.findAll();
        assertThat(testFeaturess).hasSize(databaseSizeBeforeDelete - 1);
    }
}
