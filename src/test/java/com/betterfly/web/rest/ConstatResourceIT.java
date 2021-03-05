package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.Constat;
import com.betterfly.repository.ConstatRepository;
import com.betterfly.repository.search.ConstatSearchRepository;

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
 * Integration tests for the {@link ConstatResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConstatResourceIT {

    private static final String DEFAULT_PROCESSUS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSUS = "BBBBBBBBBB";

    private static final String DEFAULT_AUDIT = "AAAAAAAAAA";
    private static final String UPDATED_AUDIT = "BBBBBBBBBB";

    private static final String DEFAULT_CONSTAT = "AAAAAAAAAA";
    private static final String UPDATED_CONSTAT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE = "BBBBBBBBBB";

    @Autowired
    private ConstatRepository constatRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.ConstatSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConstatSearchRepository mockConstatSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConstatMockMvc;

    private Constat constat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Constat createEntity(EntityManager em) {
        Constat constat = new Constat()
            .processus(DEFAULT_PROCESSUS)
            .audit(DEFAULT_AUDIT)
            .constat(DEFAULT_CONSTAT)
            .type(DEFAULT_TYPE)
            .origine(DEFAULT_ORIGINE);
        return constat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Constat createUpdatedEntity(EntityManager em) {
        Constat constat = new Constat()
            .processus(UPDATED_PROCESSUS)
            .audit(UPDATED_AUDIT)
            .constat(UPDATED_CONSTAT)
            .type(UPDATED_TYPE)
            .origine(UPDATED_ORIGINE);
        return constat;
    }

    @BeforeEach
    public void initTest() {
        constat = createEntity(em);
    }

    @Test
    @Transactional
    public void createConstat() throws Exception {
        int databaseSizeBeforeCreate = constatRepository.findAll().size();
        // Create the Constat
        restConstatMockMvc.perform(post("/api/constats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constat)))
            .andExpect(status().isCreated());

        // Validate the Constat in the database
        List<Constat> constatList = constatRepository.findAll();
        assertThat(constatList).hasSize(databaseSizeBeforeCreate + 1);
        Constat testConstat = constatList.get(constatList.size() - 1);
        assertThat(testConstat.getProcessus()).isEqualTo(DEFAULT_PROCESSUS);
        assertThat(testConstat.getAudit()).isEqualTo(DEFAULT_AUDIT);
        assertThat(testConstat.getConstat()).isEqualTo(DEFAULT_CONSTAT);
        assertThat(testConstat.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testConstat.getOrigine()).isEqualTo(DEFAULT_ORIGINE);

        // Validate the Constat in Elasticsearch
        verify(mockConstatSearchRepository, times(1)).save(testConstat);
    }

    @Test
    @Transactional
    public void createConstatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = constatRepository.findAll().size();

        // Create the Constat with an existing ID
        constat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConstatMockMvc.perform(post("/api/constats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constat)))
            .andExpect(status().isBadRequest());

        // Validate the Constat in the database
        List<Constat> constatList = constatRepository.findAll();
        assertThat(constatList).hasSize(databaseSizeBeforeCreate);

        // Validate the Constat in Elasticsearch
        verify(mockConstatSearchRepository, times(0)).save(constat);
    }


    @Test
    @Transactional
    public void getAllConstats() throws Exception {
        // Initialize the database
        constatRepository.saveAndFlush(constat);

        // Get all the constatList
        restConstatMockMvc.perform(get("/api/constats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(constat.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].audit").value(hasItem(DEFAULT_AUDIT)))
            .andExpect(jsonPath("$.[*].constat").value(hasItem(DEFAULT_CONSTAT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
    
    @Test
    @Transactional
    public void getConstat() throws Exception {
        // Initialize the database
        constatRepository.saveAndFlush(constat);

        // Get the constat
        restConstatMockMvc.perform(get("/api/constats/{id}", constat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(constat.getId().intValue()))
            .andExpect(jsonPath("$.processus").value(DEFAULT_PROCESSUS))
            .andExpect(jsonPath("$.audit").value(DEFAULT_AUDIT))
            .andExpect(jsonPath("$.constat").value(DEFAULT_CONSTAT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE));
    }
    @Test
    @Transactional
    public void getNonExistingConstat() throws Exception {
        // Get the constat
        restConstatMockMvc.perform(get("/api/constats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConstat() throws Exception {
        // Initialize the database
        constatRepository.saveAndFlush(constat);

        int databaseSizeBeforeUpdate = constatRepository.findAll().size();

        // Update the constat
        Constat updatedConstat = constatRepository.findById(constat.getId()).get();
        // Disconnect from session so that the updates on updatedConstat are not directly saved in db
        em.detach(updatedConstat);
        updatedConstat
            .processus(UPDATED_PROCESSUS)
            .audit(UPDATED_AUDIT)
            .constat(UPDATED_CONSTAT)
            .type(UPDATED_TYPE)
            .origine(UPDATED_ORIGINE);

        restConstatMockMvc.perform(put("/api/constats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConstat)))
            .andExpect(status().isOk());

        // Validate the Constat in the database
        List<Constat> constatList = constatRepository.findAll();
        assertThat(constatList).hasSize(databaseSizeBeforeUpdate);
        Constat testConstat = constatList.get(constatList.size() - 1);
        assertThat(testConstat.getProcessus()).isEqualTo(UPDATED_PROCESSUS);
        assertThat(testConstat.getAudit()).isEqualTo(UPDATED_AUDIT);
        assertThat(testConstat.getConstat()).isEqualTo(UPDATED_CONSTAT);
        assertThat(testConstat.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConstat.getOrigine()).isEqualTo(UPDATED_ORIGINE);

        // Validate the Constat in Elasticsearch
        verify(mockConstatSearchRepository, times(1)).save(testConstat);
    }

    @Test
    @Transactional
    public void updateNonExistingConstat() throws Exception {
        int databaseSizeBeforeUpdate = constatRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConstatMockMvc.perform(put("/api/constats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constat)))
            .andExpect(status().isBadRequest());

        // Validate the Constat in the database
        List<Constat> constatList = constatRepository.findAll();
        assertThat(constatList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Constat in Elasticsearch
        verify(mockConstatSearchRepository, times(0)).save(constat);
    }

    @Test
    @Transactional
    public void deleteConstat() throws Exception {
        // Initialize the database
        constatRepository.saveAndFlush(constat);

        int databaseSizeBeforeDelete = constatRepository.findAll().size();

        // Delete the constat
        restConstatMockMvc.perform(delete("/api/constats/{id}", constat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Constat> constatList = constatRepository.findAll();
        assertThat(constatList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Constat in Elasticsearch
        verify(mockConstatSearchRepository, times(1)).deleteById(constat.getId());
    }

    @Test
    @Transactional
    public void searchConstat() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        constatRepository.saveAndFlush(constat);
        when(mockConstatSearchRepository.search(queryStringQuery("id:" + constat.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(constat), PageRequest.of(0, 1), 1));

        // Search the constat
        restConstatMockMvc.perform(get("/api/_search/constats?query=id:" + constat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(constat.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].audit").value(hasItem(DEFAULT_AUDIT)))
            .andExpect(jsonPath("$.[*].constat").value(hasItem(DEFAULT_CONSTAT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
}
