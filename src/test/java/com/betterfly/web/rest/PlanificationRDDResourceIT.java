package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.PlanificationRDD;
import com.betterfly.repository.PlanificationRDDRepository;
import com.betterfly.repository.search.PlanificationRDDSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.betterfly.domain.enumeration.Standard;
/**
 * Integration tests for the {@link PlanificationRDDResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanificationRDDResourceIT {

    private static final Integer DEFAULT_N_RDD = 1;
    private static final Integer UPDATED_N_RDD = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_REALISEE = false;
    private static final Boolean UPDATED_REALISEE = true;

    private static final byte[] DEFAULT_PRESENTATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRESENTATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRESENTATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRESENTATION_CONTENT_TYPE = "image/png";

    private static final Standard DEFAULT_STANDARD = Standard.ISO9001;
    private static final Standard UPDATED_STANDARD = Standard.ISO14001;

    @Autowired
    private PlanificationRDDRepository planificationRDDRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.PlanificationRDDSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlanificationRDDSearchRepository mockPlanificationRDDSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanificationRDDMockMvc;

    private PlanificationRDD planificationRDD;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanificationRDD createEntity(EntityManager em) {
        PlanificationRDD planificationRDD = new PlanificationRDD()
            .nRdd(DEFAULT_N_RDD)
            .date(DEFAULT_DATE)
            .realisee(DEFAULT_REALISEE)
            .presentation(DEFAULT_PRESENTATION)
            .presentationContentType(DEFAULT_PRESENTATION_CONTENT_TYPE)
            .standard(DEFAULT_STANDARD);
        return planificationRDD;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanificationRDD createUpdatedEntity(EntityManager em) {
        PlanificationRDD planificationRDD = new PlanificationRDD()
            .nRdd(UPDATED_N_RDD)
            .date(UPDATED_DATE)
            .realisee(UPDATED_REALISEE)
            .presentation(UPDATED_PRESENTATION)
            .presentationContentType(UPDATED_PRESENTATION_CONTENT_TYPE)
            .standard(UPDATED_STANDARD);
        return planificationRDD;
    }

    @BeforeEach
    public void initTest() {
        planificationRDD = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanificationRDD() throws Exception {
        int databaseSizeBeforeCreate = planificationRDDRepository.findAll().size();
        // Create the PlanificationRDD
        restPlanificationRDDMockMvc.perform(post("/api/planification-rdds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planificationRDD)))
            .andExpect(status().isCreated());

        // Validate the PlanificationRDD in the database
        List<PlanificationRDD> planificationRDDList = planificationRDDRepository.findAll();
        assertThat(planificationRDDList).hasSize(databaseSizeBeforeCreate + 1);
        PlanificationRDD testPlanificationRDD = planificationRDDList.get(planificationRDDList.size() - 1);
        assertThat(testPlanificationRDD.getnRdd()).isEqualTo(DEFAULT_N_RDD);
        assertThat(testPlanificationRDD.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPlanificationRDD.isRealisee()).isEqualTo(DEFAULT_REALISEE);
        assertThat(testPlanificationRDD.getPresentation()).isEqualTo(DEFAULT_PRESENTATION);
        assertThat(testPlanificationRDD.getPresentationContentType()).isEqualTo(DEFAULT_PRESENTATION_CONTENT_TYPE);
        assertThat(testPlanificationRDD.getStandard()).isEqualTo(DEFAULT_STANDARD);

        // Validate the PlanificationRDD in Elasticsearch
        verify(mockPlanificationRDDSearchRepository, times(1)).save(testPlanificationRDD);
    }

    @Test
    @Transactional
    public void createPlanificationRDDWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planificationRDDRepository.findAll().size();

        // Create the PlanificationRDD with an existing ID
        planificationRDD.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanificationRDDMockMvc.perform(post("/api/planification-rdds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planificationRDD)))
            .andExpect(status().isBadRequest());

        // Validate the PlanificationRDD in the database
        List<PlanificationRDD> planificationRDDList = planificationRDDRepository.findAll();
        assertThat(planificationRDDList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlanificationRDD in Elasticsearch
        verify(mockPlanificationRDDSearchRepository, times(0)).save(planificationRDD);
    }


    @Test
    @Transactional
    public void getAllPlanificationRDDS() throws Exception {
        // Initialize the database
        planificationRDDRepository.saveAndFlush(planificationRDD);

        // Get all the planificationRDDList
        restPlanificationRDDMockMvc.perform(get("/api/planification-rdds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planificationRDD.getId().intValue())))
            .andExpect(jsonPath("$.[*].nRdd").value(hasItem(DEFAULT_N_RDD)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].realisee").value(hasItem(DEFAULT_REALISEE.booleanValue())))
            .andExpect(jsonPath("$.[*].presentationContentType").value(hasItem(DEFAULT_PRESENTATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].presentation").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRESENTATION))))
            .andExpect(jsonPath("$.[*].standard").value(hasItem(DEFAULT_STANDARD.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanificationRDD() throws Exception {
        // Initialize the database
        planificationRDDRepository.saveAndFlush(planificationRDD);

        // Get the planificationRDD
        restPlanificationRDDMockMvc.perform(get("/api/planification-rdds/{id}", planificationRDD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planificationRDD.getId().intValue()))
            .andExpect(jsonPath("$.nRdd").value(DEFAULT_N_RDD))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.realisee").value(DEFAULT_REALISEE.booleanValue()))
            .andExpect(jsonPath("$.presentationContentType").value(DEFAULT_PRESENTATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.presentation").value(Base64Utils.encodeToString(DEFAULT_PRESENTATION)))
            .andExpect(jsonPath("$.standard").value(DEFAULT_STANDARD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPlanificationRDD() throws Exception {
        // Get the planificationRDD
        restPlanificationRDDMockMvc.perform(get("/api/planification-rdds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanificationRDD() throws Exception {
        // Initialize the database
        planificationRDDRepository.saveAndFlush(planificationRDD);

        int databaseSizeBeforeUpdate = planificationRDDRepository.findAll().size();

        // Update the planificationRDD
        PlanificationRDD updatedPlanificationRDD = planificationRDDRepository.findById(planificationRDD.getId()).get();
        // Disconnect from session so that the updates on updatedPlanificationRDD are not directly saved in db
        em.detach(updatedPlanificationRDD);
        updatedPlanificationRDD
            .nRdd(UPDATED_N_RDD)
            .date(UPDATED_DATE)
            .realisee(UPDATED_REALISEE)
            .presentation(UPDATED_PRESENTATION)
            .presentationContentType(UPDATED_PRESENTATION_CONTENT_TYPE)
            .standard(UPDATED_STANDARD);

        restPlanificationRDDMockMvc.perform(put("/api/planification-rdds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanificationRDD)))
            .andExpect(status().isOk());

        // Validate the PlanificationRDD in the database
        List<PlanificationRDD> planificationRDDList = planificationRDDRepository.findAll();
        assertThat(planificationRDDList).hasSize(databaseSizeBeforeUpdate);
        PlanificationRDD testPlanificationRDD = planificationRDDList.get(planificationRDDList.size() - 1);
        assertThat(testPlanificationRDD.getnRdd()).isEqualTo(UPDATED_N_RDD);
        assertThat(testPlanificationRDD.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPlanificationRDD.isRealisee()).isEqualTo(UPDATED_REALISEE);
        assertThat(testPlanificationRDD.getPresentation()).isEqualTo(UPDATED_PRESENTATION);
        assertThat(testPlanificationRDD.getPresentationContentType()).isEqualTo(UPDATED_PRESENTATION_CONTENT_TYPE);
        assertThat(testPlanificationRDD.getStandard()).isEqualTo(UPDATED_STANDARD);

        // Validate the PlanificationRDD in Elasticsearch
        verify(mockPlanificationRDDSearchRepository, times(1)).save(testPlanificationRDD);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanificationRDD() throws Exception {
        int databaseSizeBeforeUpdate = planificationRDDRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanificationRDDMockMvc.perform(put("/api/planification-rdds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planificationRDD)))
            .andExpect(status().isBadRequest());

        // Validate the PlanificationRDD in the database
        List<PlanificationRDD> planificationRDDList = planificationRDDRepository.findAll();
        assertThat(planificationRDDList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlanificationRDD in Elasticsearch
        verify(mockPlanificationRDDSearchRepository, times(0)).save(planificationRDD);
    }

    @Test
    @Transactional
    public void deletePlanificationRDD() throws Exception {
        // Initialize the database
        planificationRDDRepository.saveAndFlush(planificationRDD);

        int databaseSizeBeforeDelete = planificationRDDRepository.findAll().size();

        // Delete the planificationRDD
        restPlanificationRDDMockMvc.perform(delete("/api/planification-rdds/{id}", planificationRDD.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanificationRDD> planificationRDDList = planificationRDDRepository.findAll();
        assertThat(planificationRDDList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlanificationRDD in Elasticsearch
        verify(mockPlanificationRDDSearchRepository, times(1)).deleteById(planificationRDD.getId());
    }

    @Test
    @Transactional
    public void searchPlanificationRDD() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        planificationRDDRepository.saveAndFlush(planificationRDD);
        when(mockPlanificationRDDSearchRepository.search(queryStringQuery("id:" + planificationRDD.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(planificationRDD), PageRequest.of(0, 1), 1));

        // Search the planificationRDD
        restPlanificationRDDMockMvc.perform(get("/api/_search/planification-rdds?query=id:" + planificationRDD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planificationRDD.getId().intValue())))
            .andExpect(jsonPath("$.[*].nRdd").value(hasItem(DEFAULT_N_RDD)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].realisee").value(hasItem(DEFAULT_REALISEE.booleanValue())))
            .andExpect(jsonPath("$.[*].presentationContentType").value(hasItem(DEFAULT_PRESENTATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].presentation").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRESENTATION))))
            .andExpect(jsonPath("$.[*].standard").value(hasItem(DEFAULT_STANDARD.toString())));
    }
}
