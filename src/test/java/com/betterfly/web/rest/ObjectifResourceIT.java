package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.Objectif;
import com.betterfly.repository.ObjectifRepository;
import com.betterfly.repository.search.ObjectifSearchRepository;

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
 * Integration tests for the {@link ObjectifResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ObjectifResourceIT {

    private static final String DEFAULT_PROCESSUS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSUS = "BBBBBBBBBB";

    private static final String DEFAULT_AXEDELAPOLITIQUEQSE = "AAAAAAAAAA";
    private static final String UPDATED_AXEDELAPOLITIQUEQSE = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIFQSE = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIFQSE = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATEUR = "AAAAAAAAAA";
    private static final String UPDATED_INDICATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE = "BBBBBBBBBB";

    @Autowired
    private ObjectifRepository objectifRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.ObjectifSearchRepositoryMockConfiguration
     */
    @Autowired
    private ObjectifSearchRepository mockObjectifSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObjectifMockMvc;

    private Objectif objectif;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Objectif createEntity(EntityManager em) {
        Objectif objectif = new Objectif()
            .processus(DEFAULT_PROCESSUS)
            .axedelapolitiqueqse(DEFAULT_AXEDELAPOLITIQUEQSE)
            .objectifqse(DEFAULT_OBJECTIFQSE)
            .indicateur(DEFAULT_INDICATEUR)
            .origine(DEFAULT_ORIGINE);
        return objectif;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Objectif createUpdatedEntity(EntityManager em) {
        Objectif objectif = new Objectif()
            .processus(UPDATED_PROCESSUS)
            .axedelapolitiqueqse(UPDATED_AXEDELAPOLITIQUEQSE)
            .objectifqse(UPDATED_OBJECTIFQSE)
            .indicateur(UPDATED_INDICATEUR)
            .origine(UPDATED_ORIGINE);
        return objectif;
    }

    @BeforeEach
    public void initTest() {
        objectif = createEntity(em);
    }

    @Test
    @Transactional
    public void createObjectif() throws Exception {
        int databaseSizeBeforeCreate = objectifRepository.findAll().size();
        // Create the Objectif
        restObjectifMockMvc.perform(post("/api/objectifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(objectif)))
            .andExpect(status().isCreated());

        // Validate the Objectif in the database
        List<Objectif> objectifList = objectifRepository.findAll();
        assertThat(objectifList).hasSize(databaseSizeBeforeCreate + 1);
        Objectif testObjectif = objectifList.get(objectifList.size() - 1);
        assertThat(testObjectif.getProcessus()).isEqualTo(DEFAULT_PROCESSUS);
        assertThat(testObjectif.getAxedelapolitiqueqse()).isEqualTo(DEFAULT_AXEDELAPOLITIQUEQSE);
        assertThat(testObjectif.getObjectifqse()).isEqualTo(DEFAULT_OBJECTIFQSE);
        assertThat(testObjectif.getIndicateur()).isEqualTo(DEFAULT_INDICATEUR);
        assertThat(testObjectif.getOrigine()).isEqualTo(DEFAULT_ORIGINE);

        // Validate the Objectif in Elasticsearch
        verify(mockObjectifSearchRepository, times(1)).save(testObjectif);
    }

    @Test
    @Transactional
    public void createObjectifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = objectifRepository.findAll().size();

        // Create the Objectif with an existing ID
        objectif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjectifMockMvc.perform(post("/api/objectifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(objectif)))
            .andExpect(status().isBadRequest());

        // Validate the Objectif in the database
        List<Objectif> objectifList = objectifRepository.findAll();
        assertThat(objectifList).hasSize(databaseSizeBeforeCreate);

        // Validate the Objectif in Elasticsearch
        verify(mockObjectifSearchRepository, times(0)).save(objectif);
    }


    @Test
    @Transactional
    public void getAllObjectifs() throws Exception {
        // Initialize the database
        objectifRepository.saveAndFlush(objectif);

        // Get all the objectifList
        restObjectifMockMvc.perform(get("/api/objectifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objectif.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].axedelapolitiqueqse").value(hasItem(DEFAULT_AXEDELAPOLITIQUEQSE)))
            .andExpect(jsonPath("$.[*].objectifqse").value(hasItem(DEFAULT_OBJECTIFQSE)))
            .andExpect(jsonPath("$.[*].indicateur").value(hasItem(DEFAULT_INDICATEUR)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
    
    @Test
    @Transactional
    public void getObjectif() throws Exception {
        // Initialize the database
        objectifRepository.saveAndFlush(objectif);

        // Get the objectif
        restObjectifMockMvc.perform(get("/api/objectifs/{id}", objectif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(objectif.getId().intValue()))
            .andExpect(jsonPath("$.processus").value(DEFAULT_PROCESSUS))
            .andExpect(jsonPath("$.axedelapolitiqueqse").value(DEFAULT_AXEDELAPOLITIQUEQSE))
            .andExpect(jsonPath("$.objectifqse").value(DEFAULT_OBJECTIFQSE))
            .andExpect(jsonPath("$.indicateur").value(DEFAULT_INDICATEUR))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE));
    }
    @Test
    @Transactional
    public void getNonExistingObjectif() throws Exception {
        // Get the objectif
        restObjectifMockMvc.perform(get("/api/objectifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObjectif() throws Exception {
        // Initialize the database
        objectifRepository.saveAndFlush(objectif);

        int databaseSizeBeforeUpdate = objectifRepository.findAll().size();

        // Update the objectif
        Objectif updatedObjectif = objectifRepository.findById(objectif.getId()).get();
        // Disconnect from session so that the updates on updatedObjectif are not directly saved in db
        em.detach(updatedObjectif);
        updatedObjectif
            .processus(UPDATED_PROCESSUS)
            .axedelapolitiqueqse(UPDATED_AXEDELAPOLITIQUEQSE)
            .objectifqse(UPDATED_OBJECTIFQSE)
            .indicateur(UPDATED_INDICATEUR)
            .origine(UPDATED_ORIGINE);

        restObjectifMockMvc.perform(put("/api/objectifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedObjectif)))
            .andExpect(status().isOk());

        // Validate the Objectif in the database
        List<Objectif> objectifList = objectifRepository.findAll();
        assertThat(objectifList).hasSize(databaseSizeBeforeUpdate);
        Objectif testObjectif = objectifList.get(objectifList.size() - 1);
        assertThat(testObjectif.getProcessus()).isEqualTo(UPDATED_PROCESSUS);
        assertThat(testObjectif.getAxedelapolitiqueqse()).isEqualTo(UPDATED_AXEDELAPOLITIQUEQSE);
        assertThat(testObjectif.getObjectifqse()).isEqualTo(UPDATED_OBJECTIFQSE);
        assertThat(testObjectif.getIndicateur()).isEqualTo(UPDATED_INDICATEUR);
        assertThat(testObjectif.getOrigine()).isEqualTo(UPDATED_ORIGINE);

        // Validate the Objectif in Elasticsearch
        verify(mockObjectifSearchRepository, times(1)).save(testObjectif);
    }

    @Test
    @Transactional
    public void updateNonExistingObjectif() throws Exception {
        int databaseSizeBeforeUpdate = objectifRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjectifMockMvc.perform(put("/api/objectifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(objectif)))
            .andExpect(status().isBadRequest());

        // Validate the Objectif in the database
        List<Objectif> objectifList = objectifRepository.findAll();
        assertThat(objectifList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Objectif in Elasticsearch
        verify(mockObjectifSearchRepository, times(0)).save(objectif);
    }

    @Test
    @Transactional
    public void deleteObjectif() throws Exception {
        // Initialize the database
        objectifRepository.saveAndFlush(objectif);

        int databaseSizeBeforeDelete = objectifRepository.findAll().size();

        // Delete the objectif
        restObjectifMockMvc.perform(delete("/api/objectifs/{id}", objectif.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Objectif> objectifList = objectifRepository.findAll();
        assertThat(objectifList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Objectif in Elasticsearch
        verify(mockObjectifSearchRepository, times(1)).deleteById(objectif.getId());
    }

    @Test
    @Transactional
    public void searchObjectif() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        objectifRepository.saveAndFlush(objectif);
        when(mockObjectifSearchRepository.search(queryStringQuery("id:" + objectif.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(objectif), PageRequest.of(0, 1), 1));

        // Search the objectif
        restObjectifMockMvc.perform(get("/api/_search/objectifs?query=id:" + objectif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objectif.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].axedelapolitiqueqse").value(hasItem(DEFAULT_AXEDELAPOLITIQUEQSE)))
            .andExpect(jsonPath("$.[*].objectifqse").value(hasItem(DEFAULT_OBJECTIFQSE)))
            .andExpect(jsonPath("$.[*].indicateur").value(hasItem(DEFAULT_INDICATEUR)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
}
