package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.Processus;
import com.betterfly.repository.ProcessusRepository;
import com.betterfly.repository.search.ProcessusSearchRepository;

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

/**
 * Integration tests for the {@link ProcessusResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProcessusResourceIT {

    private static final String DEFAULT_PROCESSUS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final String DEFAULT_FINALITE = "AAAAAAAAAA";
    private static final String UPDATED_FINALITE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICHE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ProcessusRepository processusRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.ProcessusSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProcessusSearchRepository mockProcessusSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessusMockMvc;

    private Processus processus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processus createEntity(EntityManager em) {
        Processus processus = new Processus()
            .processus(DEFAULT_PROCESSUS)
            .date(DEFAULT_DATE)
            .version(DEFAULT_VERSION)
            .finalite(DEFAULT_FINALITE)
            .fiche(DEFAULT_FICHE)
            .ficheContentType(DEFAULT_FICHE_CONTENT_TYPE)
            .type(DEFAULT_TYPE);
        return processus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processus createUpdatedEntity(EntityManager em) {
        Processus processus = new Processus()
            .processus(UPDATED_PROCESSUS)
            .date(UPDATED_DATE)
            .version(UPDATED_VERSION)
            .finalite(UPDATED_FINALITE)
            .fiche(UPDATED_FICHE)
            .ficheContentType(UPDATED_FICHE_CONTENT_TYPE)
            .type(UPDATED_TYPE);
        return processus;
    }

    @BeforeEach
    public void initTest() {
        processus = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessus() throws Exception {
        int databaseSizeBeforeCreate = processusRepository.findAll().size();
        // Create the Processus
        restProcessusMockMvc.perform(post("/api/processuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processus)))
            .andExpect(status().isCreated());

        // Validate the Processus in the database
        List<Processus> processusList = processusRepository.findAll();
        assertThat(processusList).hasSize(databaseSizeBeforeCreate + 1);
        Processus testProcessus = processusList.get(processusList.size() - 1);
        assertThat(testProcessus.getProcessus()).isEqualTo(DEFAULT_PROCESSUS);
        assertThat(testProcessus.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testProcessus.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProcessus.getFinalite()).isEqualTo(DEFAULT_FINALITE);
        assertThat(testProcessus.getFiche()).isEqualTo(DEFAULT_FICHE);
        assertThat(testProcessus.getFicheContentType()).isEqualTo(DEFAULT_FICHE_CONTENT_TYPE);
        assertThat(testProcessus.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the Processus in Elasticsearch
        verify(mockProcessusSearchRepository, times(1)).save(testProcessus);
    }

    @Test
    @Transactional
    public void createProcessusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processusRepository.findAll().size();

        // Create the Processus with an existing ID
        processus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessusMockMvc.perform(post("/api/processuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processus)))
            .andExpect(status().isBadRequest());

        // Validate the Processus in the database
        List<Processus> processusList = processusRepository.findAll();
        assertThat(processusList).hasSize(databaseSizeBeforeCreate);

        // Validate the Processus in Elasticsearch
        verify(mockProcessusSearchRepository, times(0)).save(processus);
    }


    @Test
    @Transactional
    public void getAllProcessuses() throws Exception {
        // Initialize the database
        processusRepository.saveAndFlush(processus);

        // Get all the processusList
        restProcessusMockMvc.perform(get("/api/processuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processus.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].finalite").value(hasItem(DEFAULT_FINALITE)))
            .andExpect(jsonPath("$.[*].ficheContentType").value(hasItem(DEFAULT_FICHE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fiche").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getProcessus() throws Exception {
        // Initialize the database
        processusRepository.saveAndFlush(processus);

        // Get the processus
        restProcessusMockMvc.perform(get("/api/processuses/{id}", processus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processus.getId().intValue()))
            .andExpect(jsonPath("$.processus").value(DEFAULT_PROCESSUS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.finalite").value(DEFAULT_FINALITE))
            .andExpect(jsonPath("$.ficheContentType").value(DEFAULT_FICHE_CONTENT_TYPE))
            .andExpect(jsonPath("$.fiche").value(Base64Utils.encodeToString(DEFAULT_FICHE)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingProcessus() throws Exception {
        // Get the processus
        restProcessusMockMvc.perform(get("/api/processuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessus() throws Exception {
        // Initialize the database
        processusRepository.saveAndFlush(processus);

        int databaseSizeBeforeUpdate = processusRepository.findAll().size();

        // Update the processus
        Processus updatedProcessus = processusRepository.findById(processus.getId()).get();
        // Disconnect from session so that the updates on updatedProcessus are not directly saved in db
        em.detach(updatedProcessus);
        updatedProcessus
            .processus(UPDATED_PROCESSUS)
            .date(UPDATED_DATE)
            .version(UPDATED_VERSION)
            .finalite(UPDATED_FINALITE)
            .fiche(UPDATED_FICHE)
            .ficheContentType(UPDATED_FICHE_CONTENT_TYPE)
            .type(UPDATED_TYPE);

        restProcessusMockMvc.perform(put("/api/processuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessus)))
            .andExpect(status().isOk());

        // Validate the Processus in the database
        List<Processus> processusList = processusRepository.findAll();
        assertThat(processusList).hasSize(databaseSizeBeforeUpdate);
        Processus testProcessus = processusList.get(processusList.size() - 1);
        assertThat(testProcessus.getProcessus()).isEqualTo(UPDATED_PROCESSUS);
        assertThat(testProcessus.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProcessus.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProcessus.getFinalite()).isEqualTo(UPDATED_FINALITE);
        assertThat(testProcessus.getFiche()).isEqualTo(UPDATED_FICHE);
        assertThat(testProcessus.getFicheContentType()).isEqualTo(UPDATED_FICHE_CONTENT_TYPE);
        assertThat(testProcessus.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the Processus in Elasticsearch
        verify(mockProcessusSearchRepository, times(1)).save(testProcessus);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessus() throws Exception {
        int databaseSizeBeforeUpdate = processusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessusMockMvc.perform(put("/api/processuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processus)))
            .andExpect(status().isBadRequest());

        // Validate the Processus in the database
        List<Processus> processusList = processusRepository.findAll();
        assertThat(processusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Processus in Elasticsearch
        verify(mockProcessusSearchRepository, times(0)).save(processus);
    }

    @Test
    @Transactional
    public void deleteProcessus() throws Exception {
        // Initialize the database
        processusRepository.saveAndFlush(processus);

        int databaseSizeBeforeDelete = processusRepository.findAll().size();

        // Delete the processus
        restProcessusMockMvc.perform(delete("/api/processuses/{id}", processus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Processus> processusList = processusRepository.findAll();
        assertThat(processusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Processus in Elasticsearch
        verify(mockProcessusSearchRepository, times(1)).deleteById(processus.getId());
    }

    @Test
    @Transactional
    public void searchProcessus() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        processusRepository.saveAndFlush(processus);
        when(mockProcessusSearchRepository.search(queryStringQuery("id:" + processus.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(processus), PageRequest.of(0, 1), 1));

        // Search the processus
        restProcessusMockMvc.perform(get("/api/_search/processuses?query=id:" + processus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processus.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].finalite").value(hasItem(DEFAULT_FINALITE)))
            .andExpect(jsonPath("$.[*].ficheContentType").value(hasItem(DEFAULT_FICHE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fiche").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
}
