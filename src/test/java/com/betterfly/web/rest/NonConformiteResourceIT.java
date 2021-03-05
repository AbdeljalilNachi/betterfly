package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.NonConformite;
import com.betterfly.repository.NonConformiteRepository;
import com.betterfly.repository.search.NonConformiteSearchRepository;

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
 * Integration tests for the {@link NonConformiteResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class NonConformiteResourceIT {

    private static final String DEFAULT_PROCESSUS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CAUSES_POTENTIELLES = "AAAAAAAAAA";
    private static final String UPDATED_CAUSES_POTENTIELLES = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE = "BBBBBBBBBB";

    @Autowired
    private NonConformiteRepository nonConformiteRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.NonConformiteSearchRepositoryMockConfiguration
     */
    @Autowired
    private NonConformiteSearchRepository mockNonConformiteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNonConformiteMockMvc;

    private NonConformite nonConformite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NonConformite createEntity(EntityManager em) {
        NonConformite nonConformite = new NonConformite()
            .processus(DEFAULT_PROCESSUS)
            .date(DEFAULT_DATE)
            .description(DEFAULT_DESCRIPTION)
            .causesPotentielles(DEFAULT_CAUSES_POTENTIELLES)
            .origine(DEFAULT_ORIGINE);
        return nonConformite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NonConformite createUpdatedEntity(EntityManager em) {
        NonConformite nonConformite = new NonConformite()
            .processus(UPDATED_PROCESSUS)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .causesPotentielles(UPDATED_CAUSES_POTENTIELLES)
            .origine(UPDATED_ORIGINE);
        return nonConformite;
    }

    @BeforeEach
    public void initTest() {
        nonConformite = createEntity(em);
    }

    @Test
    @Transactional
    public void createNonConformite() throws Exception {
        int databaseSizeBeforeCreate = nonConformiteRepository.findAll().size();
        // Create the NonConformite
        restNonConformiteMockMvc.perform(post("/api/non-conformites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nonConformite)))
            .andExpect(status().isCreated());

        // Validate the NonConformite in the database
        List<NonConformite> nonConformiteList = nonConformiteRepository.findAll();
        assertThat(nonConformiteList).hasSize(databaseSizeBeforeCreate + 1);
        NonConformite testNonConformite = nonConformiteList.get(nonConformiteList.size() - 1);
        assertThat(testNonConformite.getProcessus()).isEqualTo(DEFAULT_PROCESSUS);
        assertThat(testNonConformite.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNonConformite.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNonConformite.getCausesPotentielles()).isEqualTo(DEFAULT_CAUSES_POTENTIELLES);
        assertThat(testNonConformite.getOrigine()).isEqualTo(DEFAULT_ORIGINE);

        // Validate the NonConformite in Elasticsearch
        verify(mockNonConformiteSearchRepository, times(1)).save(testNonConformite);
    }

    @Test
    @Transactional
    public void createNonConformiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nonConformiteRepository.findAll().size();

        // Create the NonConformite with an existing ID
        nonConformite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNonConformiteMockMvc.perform(post("/api/non-conformites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nonConformite)))
            .andExpect(status().isBadRequest());

        // Validate the NonConformite in the database
        List<NonConformite> nonConformiteList = nonConformiteRepository.findAll();
        assertThat(nonConformiteList).hasSize(databaseSizeBeforeCreate);

        // Validate the NonConformite in Elasticsearch
        verify(mockNonConformiteSearchRepository, times(0)).save(nonConformite);
    }


    @Test
    @Transactional
    public void getAllNonConformites() throws Exception {
        // Initialize the database
        nonConformiteRepository.saveAndFlush(nonConformite);

        // Get all the nonConformiteList
        restNonConformiteMockMvc.perform(get("/api/non-conformites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nonConformite.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].causesPotentielles").value(hasItem(DEFAULT_CAUSES_POTENTIELLES)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
    
    @Test
    @Transactional
    public void getNonConformite() throws Exception {
        // Initialize the database
        nonConformiteRepository.saveAndFlush(nonConformite);

        // Get the nonConformite
        restNonConformiteMockMvc.perform(get("/api/non-conformites/{id}", nonConformite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nonConformite.getId().intValue()))
            .andExpect(jsonPath("$.processus").value(DEFAULT_PROCESSUS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.causesPotentielles").value(DEFAULT_CAUSES_POTENTIELLES))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE));
    }
    @Test
    @Transactional
    public void getNonExistingNonConformite() throws Exception {
        // Get the nonConformite
        restNonConformiteMockMvc.perform(get("/api/non-conformites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNonConformite() throws Exception {
        // Initialize the database
        nonConformiteRepository.saveAndFlush(nonConformite);

        int databaseSizeBeforeUpdate = nonConformiteRepository.findAll().size();

        // Update the nonConformite
        NonConformite updatedNonConformite = nonConformiteRepository.findById(nonConformite.getId()).get();
        // Disconnect from session so that the updates on updatedNonConformite are not directly saved in db
        em.detach(updatedNonConformite);
        updatedNonConformite
            .processus(UPDATED_PROCESSUS)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .causesPotentielles(UPDATED_CAUSES_POTENTIELLES)
            .origine(UPDATED_ORIGINE);

        restNonConformiteMockMvc.perform(put("/api/non-conformites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNonConformite)))
            .andExpect(status().isOk());

        // Validate the NonConformite in the database
        List<NonConformite> nonConformiteList = nonConformiteRepository.findAll();
        assertThat(nonConformiteList).hasSize(databaseSizeBeforeUpdate);
        NonConformite testNonConformite = nonConformiteList.get(nonConformiteList.size() - 1);
        assertThat(testNonConformite.getProcessus()).isEqualTo(UPDATED_PROCESSUS);
        assertThat(testNonConformite.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNonConformite.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNonConformite.getCausesPotentielles()).isEqualTo(UPDATED_CAUSES_POTENTIELLES);
        assertThat(testNonConformite.getOrigine()).isEqualTo(UPDATED_ORIGINE);

        // Validate the NonConformite in Elasticsearch
        verify(mockNonConformiteSearchRepository, times(1)).save(testNonConformite);
    }

    @Test
    @Transactional
    public void updateNonExistingNonConformite() throws Exception {
        int databaseSizeBeforeUpdate = nonConformiteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNonConformiteMockMvc.perform(put("/api/non-conformites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nonConformite)))
            .andExpect(status().isBadRequest());

        // Validate the NonConformite in the database
        List<NonConformite> nonConformiteList = nonConformiteRepository.findAll();
        assertThat(nonConformiteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the NonConformite in Elasticsearch
        verify(mockNonConformiteSearchRepository, times(0)).save(nonConformite);
    }

    @Test
    @Transactional
    public void deleteNonConformite() throws Exception {
        // Initialize the database
        nonConformiteRepository.saveAndFlush(nonConformite);

        int databaseSizeBeforeDelete = nonConformiteRepository.findAll().size();

        // Delete the nonConformite
        restNonConformiteMockMvc.perform(delete("/api/non-conformites/{id}", nonConformite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NonConformite> nonConformiteList = nonConformiteRepository.findAll();
        assertThat(nonConformiteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the NonConformite in Elasticsearch
        verify(mockNonConformiteSearchRepository, times(1)).deleteById(nonConformite.getId());
    }

    @Test
    @Transactional
    public void searchNonConformite() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        nonConformiteRepository.saveAndFlush(nonConformite);
        when(mockNonConformiteSearchRepository.search(queryStringQuery("id:" + nonConformite.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(nonConformite), PageRequest.of(0, 1), 1));

        // Search the nonConformite
        restNonConformiteMockMvc.perform(get("/api/_search/non-conformites?query=id:" + nonConformite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nonConformite.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].causesPotentielles").value(hasItem(DEFAULT_CAUSES_POTENTIELLES)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
}
