package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.PolitiqueQSE;
import com.betterfly.repository.PolitiqueQSERepository;
import com.betterfly.repository.search.PolitiqueQSESearchRepository;

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

/**
 * Integration tests for the {@link PolitiqueQSEResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PolitiqueQSEResourceIT {

    private static final String DEFAULT_PROCESSUS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_AXE_POLITIQUE_QSE = "AAAAAAAAAA";
    private static final String UPDATED_AXE_POLITIQUE_QSE = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIF_QSE = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIF_QSE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VIGUEUR = false;
    private static final Boolean UPDATED_VIGUEUR = true;

    private static final String DEFAULT_INDICATEUR = "AAAAAAAAAA";
    private static final String UPDATED_INDICATEUR = "BBBBBBBBBB";

    @Autowired
    private PolitiqueQSERepository politiqueQSERepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.PolitiqueQSESearchRepositoryMockConfiguration
     */
    @Autowired
    private PolitiqueQSESearchRepository mockPolitiqueQSESearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPolitiqueQSEMockMvc;

    private PolitiqueQSE politiqueQSE;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolitiqueQSE createEntity(EntityManager em) {
        PolitiqueQSE politiqueQSE = new PolitiqueQSE()
            .processus(DEFAULT_PROCESSUS)
            .date(DEFAULT_DATE)
            .axePolitiqueQSE(DEFAULT_AXE_POLITIQUE_QSE)
            .objectifQSE(DEFAULT_OBJECTIF_QSE)
            .vigueur(DEFAULT_VIGUEUR)
            .indicateur(DEFAULT_INDICATEUR);
        return politiqueQSE;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolitiqueQSE createUpdatedEntity(EntityManager em) {
        PolitiqueQSE politiqueQSE = new PolitiqueQSE()
            .processus(UPDATED_PROCESSUS)
            .date(UPDATED_DATE)
            .axePolitiqueQSE(UPDATED_AXE_POLITIQUE_QSE)
            .objectifQSE(UPDATED_OBJECTIF_QSE)
            .vigueur(UPDATED_VIGUEUR)
            .indicateur(UPDATED_INDICATEUR);
        return politiqueQSE;
    }

    @BeforeEach
    public void initTest() {
        politiqueQSE = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolitiqueQSE() throws Exception {
        int databaseSizeBeforeCreate = politiqueQSERepository.findAll().size();
        // Create the PolitiqueQSE
        restPolitiqueQSEMockMvc.perform(post("/api/politique-qses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(politiqueQSE)))
            .andExpect(status().isCreated());

        // Validate the PolitiqueQSE in the database
        List<PolitiqueQSE> politiqueQSEList = politiqueQSERepository.findAll();
        assertThat(politiqueQSEList).hasSize(databaseSizeBeforeCreate + 1);
        PolitiqueQSE testPolitiqueQSE = politiqueQSEList.get(politiqueQSEList.size() - 1);
        assertThat(testPolitiqueQSE.getProcessus()).isEqualTo(DEFAULT_PROCESSUS);
        assertThat(testPolitiqueQSE.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPolitiqueQSE.getAxePolitiqueQSE()).isEqualTo(DEFAULT_AXE_POLITIQUE_QSE);
        assertThat(testPolitiqueQSE.getObjectifQSE()).isEqualTo(DEFAULT_OBJECTIF_QSE);
        assertThat(testPolitiqueQSE.isVigueur()).isEqualTo(DEFAULT_VIGUEUR);
        assertThat(testPolitiqueQSE.getIndicateur()).isEqualTo(DEFAULT_INDICATEUR);

        // Validate the PolitiqueQSE in Elasticsearch
        verify(mockPolitiqueQSESearchRepository, times(1)).save(testPolitiqueQSE);
    }

    @Test
    @Transactional
    public void createPolitiqueQSEWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = politiqueQSERepository.findAll().size();

        // Create the PolitiqueQSE with an existing ID
        politiqueQSE.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolitiqueQSEMockMvc.perform(post("/api/politique-qses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(politiqueQSE)))
            .andExpect(status().isBadRequest());

        // Validate the PolitiqueQSE in the database
        List<PolitiqueQSE> politiqueQSEList = politiqueQSERepository.findAll();
        assertThat(politiqueQSEList).hasSize(databaseSizeBeforeCreate);

        // Validate the PolitiqueQSE in Elasticsearch
        verify(mockPolitiqueQSESearchRepository, times(0)).save(politiqueQSE);
    }


    @Test
    @Transactional
    public void getAllPolitiqueQSES() throws Exception {
        // Initialize the database
        politiqueQSERepository.saveAndFlush(politiqueQSE);

        // Get all the politiqueQSEList
        restPolitiqueQSEMockMvc.perform(get("/api/politique-qses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(politiqueQSE.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].axePolitiqueQSE").value(hasItem(DEFAULT_AXE_POLITIQUE_QSE)))
            .andExpect(jsonPath("$.[*].objectifQSE").value(hasItem(DEFAULT_OBJECTIF_QSE)))
            .andExpect(jsonPath("$.[*].vigueur").value(hasItem(DEFAULT_VIGUEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].indicateur").value(hasItem(DEFAULT_INDICATEUR)));
    }
    
    @Test
    @Transactional
    public void getPolitiqueQSE() throws Exception {
        // Initialize the database
        politiqueQSERepository.saveAndFlush(politiqueQSE);

        // Get the politiqueQSE
        restPolitiqueQSEMockMvc.perform(get("/api/politique-qses/{id}", politiqueQSE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(politiqueQSE.getId().intValue()))
            .andExpect(jsonPath("$.processus").value(DEFAULT_PROCESSUS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.axePolitiqueQSE").value(DEFAULT_AXE_POLITIQUE_QSE))
            .andExpect(jsonPath("$.objectifQSE").value(DEFAULT_OBJECTIF_QSE))
            .andExpect(jsonPath("$.vigueur").value(DEFAULT_VIGUEUR.booleanValue()))
            .andExpect(jsonPath("$.indicateur").value(DEFAULT_INDICATEUR));
    }
    @Test
    @Transactional
    public void getNonExistingPolitiqueQSE() throws Exception {
        // Get the politiqueQSE
        restPolitiqueQSEMockMvc.perform(get("/api/politique-qses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolitiqueQSE() throws Exception {
        // Initialize the database
        politiqueQSERepository.saveAndFlush(politiqueQSE);

        int databaseSizeBeforeUpdate = politiqueQSERepository.findAll().size();

        // Update the politiqueQSE
        PolitiqueQSE updatedPolitiqueQSE = politiqueQSERepository.findById(politiqueQSE.getId()).get();
        // Disconnect from session so that the updates on updatedPolitiqueQSE are not directly saved in db
        em.detach(updatedPolitiqueQSE);
        updatedPolitiqueQSE
            .processus(UPDATED_PROCESSUS)
            .date(UPDATED_DATE)
            .axePolitiqueQSE(UPDATED_AXE_POLITIQUE_QSE)
            .objectifQSE(UPDATED_OBJECTIF_QSE)
            .vigueur(UPDATED_VIGUEUR)
            .indicateur(UPDATED_INDICATEUR);

        restPolitiqueQSEMockMvc.perform(put("/api/politique-qses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPolitiqueQSE)))
            .andExpect(status().isOk());

        // Validate the PolitiqueQSE in the database
        List<PolitiqueQSE> politiqueQSEList = politiqueQSERepository.findAll();
        assertThat(politiqueQSEList).hasSize(databaseSizeBeforeUpdate);
        PolitiqueQSE testPolitiqueQSE = politiqueQSEList.get(politiqueQSEList.size() - 1);
        assertThat(testPolitiqueQSE.getProcessus()).isEqualTo(UPDATED_PROCESSUS);
        assertThat(testPolitiqueQSE.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPolitiqueQSE.getAxePolitiqueQSE()).isEqualTo(UPDATED_AXE_POLITIQUE_QSE);
        assertThat(testPolitiqueQSE.getObjectifQSE()).isEqualTo(UPDATED_OBJECTIF_QSE);
        assertThat(testPolitiqueQSE.isVigueur()).isEqualTo(UPDATED_VIGUEUR);
        assertThat(testPolitiqueQSE.getIndicateur()).isEqualTo(UPDATED_INDICATEUR);

        // Validate the PolitiqueQSE in Elasticsearch
        verify(mockPolitiqueQSESearchRepository, times(1)).save(testPolitiqueQSE);
    }

    @Test
    @Transactional
    public void updateNonExistingPolitiqueQSE() throws Exception {
        int databaseSizeBeforeUpdate = politiqueQSERepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolitiqueQSEMockMvc.perform(put("/api/politique-qses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(politiqueQSE)))
            .andExpect(status().isBadRequest());

        // Validate the PolitiqueQSE in the database
        List<PolitiqueQSE> politiqueQSEList = politiqueQSERepository.findAll();
        assertThat(politiqueQSEList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PolitiqueQSE in Elasticsearch
        verify(mockPolitiqueQSESearchRepository, times(0)).save(politiqueQSE);
    }

    @Test
    @Transactional
    public void deletePolitiqueQSE() throws Exception {
        // Initialize the database
        politiqueQSERepository.saveAndFlush(politiqueQSE);

        int databaseSizeBeforeDelete = politiqueQSERepository.findAll().size();

        // Delete the politiqueQSE
        restPolitiqueQSEMockMvc.perform(delete("/api/politique-qses/{id}", politiqueQSE.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PolitiqueQSE> politiqueQSEList = politiqueQSERepository.findAll();
        assertThat(politiqueQSEList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PolitiqueQSE in Elasticsearch
        verify(mockPolitiqueQSESearchRepository, times(1)).deleteById(politiqueQSE.getId());
    }

    @Test
    @Transactional
    public void searchPolitiqueQSE() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        politiqueQSERepository.saveAndFlush(politiqueQSE);
        when(mockPolitiqueQSESearchRepository.search(queryStringQuery("id:" + politiqueQSE.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(politiqueQSE), PageRequest.of(0, 1), 1));

        // Search the politiqueQSE
        restPolitiqueQSEMockMvc.perform(get("/api/_search/politique-qses?query=id:" + politiqueQSE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(politiqueQSE.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].axePolitiqueQSE").value(hasItem(DEFAULT_AXE_POLITIQUE_QSE)))
            .andExpect(jsonPath("$.[*].objectifQSE").value(hasItem(DEFAULT_OBJECTIF_QSE)))
            .andExpect(jsonPath("$.[*].vigueur").value(hasItem(DEFAULT_VIGUEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].indicateur").value(hasItem(DEFAULT_INDICATEUR)));
    }
}
