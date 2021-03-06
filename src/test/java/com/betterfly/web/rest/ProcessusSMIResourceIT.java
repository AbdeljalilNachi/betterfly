package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.ProcessusSMI;
import com.betterfly.repository.ProcessusSMIRepository;
import com.betterfly.repository.search.ProcessusSMISearchRepository;

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

import com.betterfly.domain.enumeration.TypeProcessus;
/**
 * Integration tests for the {@link ProcessusSMIResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProcessusSMIResourceIT {

    private static final String DEFAULT_PROCESSUS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final String DEFAULT_PILOTE = "AAAAAAAAAA";
    private static final String UPDATED_PILOTE = "BBBBBBBBBB";

    private static final String DEFAULT_FINALITE = "AAAAAAAAAA";
    private static final String UPDATED_FINALITE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICHE_PROCESSUS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHE_PROCESSUS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHE_PROCESSUS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHE_PROCESSUS_CONTENT_TYPE = "image/png";

    private static final TypeProcessus DEFAULT_TYPE = TypeProcessus.MANAGEMENT;
    private static final TypeProcessus UPDATED_TYPE = TypeProcessus.REALISATION;

    private static final Boolean DEFAULT_VIGUEUR = false;
    private static final Boolean UPDATED_VIGUEUR = true;

    @Autowired
    private ProcessusSMIRepository processusSMIRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.ProcessusSMISearchRepositoryMockConfiguration
     */
    @Autowired
    private ProcessusSMISearchRepository mockProcessusSMISearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessusSMIMockMvc;

    private ProcessusSMI processusSMI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessusSMI createEntity(EntityManager em) {
        ProcessusSMI processusSMI = new ProcessusSMI()
            .processus(DEFAULT_PROCESSUS)
            .date(DEFAULT_DATE)
            .version(DEFAULT_VERSION)
            .pilote(DEFAULT_PILOTE)
            .finalite(DEFAULT_FINALITE)
            .ficheProcessus(DEFAULT_FICHE_PROCESSUS)
            .ficheProcessusContentType(DEFAULT_FICHE_PROCESSUS_CONTENT_TYPE)
            .type(DEFAULT_TYPE)
            .vigueur(DEFAULT_VIGUEUR);
        return processusSMI;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessusSMI createUpdatedEntity(EntityManager em) {
        ProcessusSMI processusSMI = new ProcessusSMI()
            .processus(UPDATED_PROCESSUS)
            .date(UPDATED_DATE)
            .version(UPDATED_VERSION)
            .pilote(UPDATED_PILOTE)
            .finalite(UPDATED_FINALITE)
            .ficheProcessus(UPDATED_FICHE_PROCESSUS)
            .ficheProcessusContentType(UPDATED_FICHE_PROCESSUS_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .vigueur(UPDATED_VIGUEUR);
        return processusSMI;
    }

    @BeforeEach
    public void initTest() {
        processusSMI = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessusSMI() throws Exception {
        int databaseSizeBeforeCreate = processusSMIRepository.findAll().size();
        // Create the ProcessusSMI
        restProcessusSMIMockMvc.perform(post("/api/processus-smis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processusSMI)))
            .andExpect(status().isCreated());

        // Validate the ProcessusSMI in the database
        List<ProcessusSMI> processusSMIList = processusSMIRepository.findAll();
        assertThat(processusSMIList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessusSMI testProcessusSMI = processusSMIList.get(processusSMIList.size() - 1);
        assertThat(testProcessusSMI.getProcessus()).isEqualTo(DEFAULT_PROCESSUS);
        assertThat(testProcessusSMI.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testProcessusSMI.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProcessusSMI.getPilote()).isEqualTo(DEFAULT_PILOTE);
        assertThat(testProcessusSMI.getFinalite()).isEqualTo(DEFAULT_FINALITE);
        assertThat(testProcessusSMI.getFicheProcessus()).isEqualTo(DEFAULT_FICHE_PROCESSUS);
        assertThat(testProcessusSMI.getFicheProcessusContentType()).isEqualTo(DEFAULT_FICHE_PROCESSUS_CONTENT_TYPE);
        assertThat(testProcessusSMI.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProcessusSMI.isVigueur()).isEqualTo(DEFAULT_VIGUEUR);

        // Validate the ProcessusSMI in Elasticsearch
        verify(mockProcessusSMISearchRepository, times(1)).save(testProcessusSMI);
    }

    @Test
    @Transactional
    public void createProcessusSMIWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processusSMIRepository.findAll().size();

        // Create the ProcessusSMI with an existing ID
        processusSMI.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessusSMIMockMvc.perform(post("/api/processus-smis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processusSMI)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessusSMI in the database
        List<ProcessusSMI> processusSMIList = processusSMIRepository.findAll();
        assertThat(processusSMIList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProcessusSMI in Elasticsearch
        verify(mockProcessusSMISearchRepository, times(0)).save(processusSMI);
    }


    @Test
    @Transactional
    public void getAllProcessusSMIS() throws Exception {
        // Initialize the database
        processusSMIRepository.saveAndFlush(processusSMI);

        // Get all the processusSMIList
        restProcessusSMIMockMvc.perform(get("/api/processus-smis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processusSMI.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].pilote").value(hasItem(DEFAULT_PILOTE)))
            .andExpect(jsonPath("$.[*].finalite").value(hasItem(DEFAULT_FINALITE)))
            .andExpect(jsonPath("$.[*].ficheProcessusContentType").value(hasItem(DEFAULT_FICHE_PROCESSUS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].ficheProcessus").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHE_PROCESSUS))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vigueur").value(hasItem(DEFAULT_VIGUEUR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProcessusSMI() throws Exception {
        // Initialize the database
        processusSMIRepository.saveAndFlush(processusSMI);

        // Get the processusSMI
        restProcessusSMIMockMvc.perform(get("/api/processus-smis/{id}", processusSMI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processusSMI.getId().intValue()))
            .andExpect(jsonPath("$.processus").value(DEFAULT_PROCESSUS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.pilote").value(DEFAULT_PILOTE))
            .andExpect(jsonPath("$.finalite").value(DEFAULT_FINALITE))
            .andExpect(jsonPath("$.ficheProcessusContentType").value(DEFAULT_FICHE_PROCESSUS_CONTENT_TYPE))
            .andExpect(jsonPath("$.ficheProcessus").value(Base64Utils.encodeToString(DEFAULT_FICHE_PROCESSUS)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.vigueur").value(DEFAULT_VIGUEUR.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProcessusSMI() throws Exception {
        // Get the processusSMI
        restProcessusSMIMockMvc.perform(get("/api/processus-smis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessusSMI() throws Exception {
        // Initialize the database
        processusSMIRepository.saveAndFlush(processusSMI);

        int databaseSizeBeforeUpdate = processusSMIRepository.findAll().size();

        // Update the processusSMI
        ProcessusSMI updatedProcessusSMI = processusSMIRepository.findById(processusSMI.getId()).get();
        // Disconnect from session so that the updates on updatedProcessusSMI are not directly saved in db
        em.detach(updatedProcessusSMI);
        updatedProcessusSMI
            .processus(UPDATED_PROCESSUS)
            .date(UPDATED_DATE)
            .version(UPDATED_VERSION)
            .pilote(UPDATED_PILOTE)
            .finalite(UPDATED_FINALITE)
            .ficheProcessus(UPDATED_FICHE_PROCESSUS)
            .ficheProcessusContentType(UPDATED_FICHE_PROCESSUS_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .vigueur(UPDATED_VIGUEUR);

        restProcessusSMIMockMvc.perform(put("/api/processus-smis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessusSMI)))
            .andExpect(status().isOk());

        // Validate the ProcessusSMI in the database
        List<ProcessusSMI> processusSMIList = processusSMIRepository.findAll();
        assertThat(processusSMIList).hasSize(databaseSizeBeforeUpdate);
        ProcessusSMI testProcessusSMI = processusSMIList.get(processusSMIList.size() - 1);
        assertThat(testProcessusSMI.getProcessus()).isEqualTo(UPDATED_PROCESSUS);
        assertThat(testProcessusSMI.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProcessusSMI.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProcessusSMI.getPilote()).isEqualTo(UPDATED_PILOTE);
        assertThat(testProcessusSMI.getFinalite()).isEqualTo(UPDATED_FINALITE);
        assertThat(testProcessusSMI.getFicheProcessus()).isEqualTo(UPDATED_FICHE_PROCESSUS);
        assertThat(testProcessusSMI.getFicheProcessusContentType()).isEqualTo(UPDATED_FICHE_PROCESSUS_CONTENT_TYPE);
        assertThat(testProcessusSMI.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProcessusSMI.isVigueur()).isEqualTo(UPDATED_VIGUEUR);

        // Validate the ProcessusSMI in Elasticsearch
        verify(mockProcessusSMISearchRepository, times(1)).save(testProcessusSMI);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessusSMI() throws Exception {
        int databaseSizeBeforeUpdate = processusSMIRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessusSMIMockMvc.perform(put("/api/processus-smis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processusSMI)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessusSMI in the database
        List<ProcessusSMI> processusSMIList = processusSMIRepository.findAll();
        assertThat(processusSMIList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProcessusSMI in Elasticsearch
        verify(mockProcessusSMISearchRepository, times(0)).save(processusSMI);
    }

    @Test
    @Transactional
    public void deleteProcessusSMI() throws Exception {
        // Initialize the database
        processusSMIRepository.saveAndFlush(processusSMI);

        int databaseSizeBeforeDelete = processusSMIRepository.findAll().size();

        // Delete the processusSMI
        restProcessusSMIMockMvc.perform(delete("/api/processus-smis/{id}", processusSMI.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessusSMI> processusSMIList = processusSMIRepository.findAll();
        assertThat(processusSMIList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProcessusSMI in Elasticsearch
        verify(mockProcessusSMISearchRepository, times(1)).deleteById(processusSMI.getId());
    }

    @Test
    @Transactional
    public void searchProcessusSMI() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        processusSMIRepository.saveAndFlush(processusSMI);
        when(mockProcessusSMISearchRepository.search(queryStringQuery("id:" + processusSMI.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(processusSMI), PageRequest.of(0, 1), 1));

        // Search the processusSMI
        restProcessusSMIMockMvc.perform(get("/api/_search/processus-smis?query=id:" + processusSMI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processusSMI.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].pilote").value(hasItem(DEFAULT_PILOTE)))
            .andExpect(jsonPath("$.[*].finalite").value(hasItem(DEFAULT_FINALITE)))
            .andExpect(jsonPath("$.[*].ficheProcessusContentType").value(hasItem(DEFAULT_FICHE_PROCESSUS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].ficheProcessus").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHE_PROCESSUS))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vigueur").value(hasItem(DEFAULT_VIGUEUR.booleanValue())));
    }
}
