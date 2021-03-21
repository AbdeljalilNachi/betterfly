package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.ResultIndicateurs;
import com.betterfly.repository.ResultIndicateursRepository;
import com.betterfly.repository.search.ResultIndicateursSearchRepository;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ResultIndicateursResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResultIndicateursResourceIT {

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    @Autowired
    private ResultIndicateursRepository resultIndicateursRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.ResultIndicateursSearchRepositoryMockConfiguration
     */
    @Autowired
    private ResultIndicateursSearchRepository mockResultIndicateursSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultIndicateursMockMvc;

    private ResultIndicateurs resultIndicateurs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultIndicateurs createEntity(EntityManager em) {
        ResultIndicateurs resultIndicateurs = new ResultIndicateurs()
            .annee(DEFAULT_ANNEE)
            .observation(DEFAULT_OBSERVATION);
        return resultIndicateurs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultIndicateurs createUpdatedEntity(EntityManager em) {
        ResultIndicateurs resultIndicateurs = new ResultIndicateurs()
            .annee(UPDATED_ANNEE)
            .observation(UPDATED_OBSERVATION);
        return resultIndicateurs;
    }

    @BeforeEach
    public void initTest() {
        resultIndicateurs = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultIndicateurs() throws Exception {
        int databaseSizeBeforeCreate = resultIndicateursRepository.findAll().size();
        // Create the ResultIndicateurs
        restResultIndicateursMockMvc.perform(post("/api/result-indicateurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultIndicateurs)))
            .andExpect(status().isCreated());

        // Validate the ResultIndicateurs in the database
        List<ResultIndicateurs> resultIndicateursList = resultIndicateursRepository.findAll();
        assertThat(resultIndicateursList).hasSize(databaseSizeBeforeCreate + 1);
        ResultIndicateurs testResultIndicateurs = resultIndicateursList.get(resultIndicateursList.size() - 1);
        assertThat(testResultIndicateurs.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testResultIndicateurs.getObservation()).isEqualTo(DEFAULT_OBSERVATION);

        // Validate the ResultIndicateurs in Elasticsearch
        verify(mockResultIndicateursSearchRepository, times(1)).save(testResultIndicateurs);
    }

    @Test
    @Transactional
    public void createResultIndicateursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultIndicateursRepository.findAll().size();

        // Create the ResultIndicateurs with an existing ID
        resultIndicateurs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultIndicateursMockMvc.perform(post("/api/result-indicateurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultIndicateurs)))
            .andExpect(status().isBadRequest());

        // Validate the ResultIndicateurs in the database
        List<ResultIndicateurs> resultIndicateursList = resultIndicateursRepository.findAll();
        assertThat(resultIndicateursList).hasSize(databaseSizeBeforeCreate);

        // Validate the ResultIndicateurs in Elasticsearch
        verify(mockResultIndicateursSearchRepository, times(0)).save(resultIndicateurs);
    }


    @Test
    @Transactional
    public void getAllResultIndicateurs() throws Exception {
        // Initialize the database
        resultIndicateursRepository.saveAndFlush(resultIndicateurs);

        // Get all the resultIndicateursList
        restResultIndicateursMockMvc.perform(get("/api/result-indicateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultIndicateurs.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)));
    }
    
    @Test
    @Transactional
    public void getResultIndicateurs() throws Exception {
        // Initialize the database
        resultIndicateursRepository.saveAndFlush(resultIndicateurs);

        // Get the resultIndicateurs
        restResultIndicateursMockMvc.perform(get("/api/result-indicateurs/{id}", resultIndicateurs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultIndicateurs.getId().intValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION));
    }
    @Test
    @Transactional
    public void getNonExistingResultIndicateurs() throws Exception {
        // Get the resultIndicateurs
        restResultIndicateursMockMvc.perform(get("/api/result-indicateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultIndicateurs() throws Exception {
        // Initialize the database
        resultIndicateursRepository.saveAndFlush(resultIndicateurs);

        int databaseSizeBeforeUpdate = resultIndicateursRepository.findAll().size();

        // Update the resultIndicateurs
        ResultIndicateurs updatedResultIndicateurs = resultIndicateursRepository.findById(resultIndicateurs.getId()).get();
        // Disconnect from session so that the updates on updatedResultIndicateurs are not directly saved in db
        em.detach(updatedResultIndicateurs);
        updatedResultIndicateurs
            .annee(UPDATED_ANNEE)
            .observation(UPDATED_OBSERVATION);

        restResultIndicateursMockMvc.perform(put("/api/result-indicateurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultIndicateurs)))
            .andExpect(status().isOk());

        // Validate the ResultIndicateurs in the database
        List<ResultIndicateurs> resultIndicateursList = resultIndicateursRepository.findAll();
        assertThat(resultIndicateursList).hasSize(databaseSizeBeforeUpdate);
        ResultIndicateurs testResultIndicateurs = resultIndicateursList.get(resultIndicateursList.size() - 1);
        assertThat(testResultIndicateurs.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testResultIndicateurs.getObservation()).isEqualTo(UPDATED_OBSERVATION);

        // Validate the ResultIndicateurs in Elasticsearch
        verify(mockResultIndicateursSearchRepository, times(1)).save(testResultIndicateurs);
    }

    @Test
    @Transactional
    public void updateNonExistingResultIndicateurs() throws Exception {
        int databaseSizeBeforeUpdate = resultIndicateursRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultIndicateursMockMvc.perform(put("/api/result-indicateurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultIndicateurs)))
            .andExpect(status().isBadRequest());

        // Validate the ResultIndicateurs in the database
        List<ResultIndicateurs> resultIndicateursList = resultIndicateursRepository.findAll();
        assertThat(resultIndicateursList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ResultIndicateurs in Elasticsearch
        verify(mockResultIndicateursSearchRepository, times(0)).save(resultIndicateurs);
    }

    @Test
    @Transactional
    public void deleteResultIndicateurs() throws Exception {
        // Initialize the database
        resultIndicateursRepository.saveAndFlush(resultIndicateurs);

        int databaseSizeBeforeDelete = resultIndicateursRepository.findAll().size();

        // Delete the resultIndicateurs
        restResultIndicateursMockMvc.perform(delete("/api/result-indicateurs/{id}", resultIndicateurs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultIndicateurs> resultIndicateursList = resultIndicateursRepository.findAll();
        assertThat(resultIndicateursList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ResultIndicateurs in Elasticsearch
        verify(mockResultIndicateursSearchRepository, times(1)).deleteById(resultIndicateurs.getId());
    }

    @Test
    @Transactional
    public void searchResultIndicateurs() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        resultIndicateursRepository.saveAndFlush(resultIndicateurs);
        when(mockResultIndicateursSearchRepository.search(queryStringQuery("id:" + resultIndicateurs.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(resultIndicateurs), PageRequest.of(0, 1), 1));

        // Search the resultIndicateurs
        restResultIndicateursMockMvc.perform(get("/api/_search/result-indicateurs?query=id:" + resultIndicateurs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultIndicateurs.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)));
    }
}
