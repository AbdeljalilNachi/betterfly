package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.ConstatAudit;
import com.betterfly.repository.ConstatAuditRepository;
import com.betterfly.repository.search.ConstatAuditSearchRepository;

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

import com.betterfly.domain.enumeration.TypeConstatAudit;
/**
 * Integration tests for the {@link ConstatAuditResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConstatAuditResourceIT {

    private static final TypeConstatAudit DEFAULT_TYPE = TypeConstatAudit.PF;
    private static final TypeConstatAudit UPDATED_TYPE = TypeConstatAudit.RA;

    private static final String DEFAULT_CONSTAT = "AAAAAAAAAA";
    private static final String UPDATED_CONSTAT = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE = "BBBBBBBBBB";

    @Autowired
    private ConstatAuditRepository constatAuditRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.ConstatAuditSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConstatAuditSearchRepository mockConstatAuditSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConstatAuditMockMvc;

    private ConstatAudit constatAudit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConstatAudit createEntity(EntityManager em) {
        ConstatAudit constatAudit = new ConstatAudit()
            .type(DEFAULT_TYPE)
            .constat(DEFAULT_CONSTAT)
            .origine(DEFAULT_ORIGINE);
        return constatAudit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConstatAudit createUpdatedEntity(EntityManager em) {
        ConstatAudit constatAudit = new ConstatAudit()
            .type(UPDATED_TYPE)
            .constat(UPDATED_CONSTAT)
            .origine(UPDATED_ORIGINE);
        return constatAudit;
    }

    @BeforeEach
    public void initTest() {
        constatAudit = createEntity(em);
    }

    @Test
    @Transactional
    public void createConstatAudit() throws Exception {
        int databaseSizeBeforeCreate = constatAuditRepository.findAll().size();
        // Create the ConstatAudit
        restConstatAuditMockMvc.perform(post("/api/constat-audits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constatAudit)))
            .andExpect(status().isCreated());

        // Validate the ConstatAudit in the database
        List<ConstatAudit> constatAuditList = constatAuditRepository.findAll();
        assertThat(constatAuditList).hasSize(databaseSizeBeforeCreate + 1);
        ConstatAudit testConstatAudit = constatAuditList.get(constatAuditList.size() - 1);
        assertThat(testConstatAudit.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testConstatAudit.getConstat()).isEqualTo(DEFAULT_CONSTAT);
        assertThat(testConstatAudit.getOrigine()).isEqualTo(DEFAULT_ORIGINE);

        // Validate the ConstatAudit in Elasticsearch
        verify(mockConstatAuditSearchRepository, times(1)).save(testConstatAudit);
    }

    @Test
    @Transactional
    public void createConstatAuditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = constatAuditRepository.findAll().size();

        // Create the ConstatAudit with an existing ID
        constatAudit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConstatAuditMockMvc.perform(post("/api/constat-audits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constatAudit)))
            .andExpect(status().isBadRequest());

        // Validate the ConstatAudit in the database
        List<ConstatAudit> constatAuditList = constatAuditRepository.findAll();
        assertThat(constatAuditList).hasSize(databaseSizeBeforeCreate);

        // Validate the ConstatAudit in Elasticsearch
        verify(mockConstatAuditSearchRepository, times(0)).save(constatAudit);
    }


    @Test
    @Transactional
    public void getAllConstatAudits() throws Exception {
        // Initialize the database
        constatAuditRepository.saveAndFlush(constatAudit);

        // Get all the constatAuditList
        restConstatAuditMockMvc.perform(get("/api/constat-audits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(constatAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].constat").value(hasItem(DEFAULT_CONSTAT)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
    
    @Test
    @Transactional
    public void getConstatAudit() throws Exception {
        // Initialize the database
        constatAuditRepository.saveAndFlush(constatAudit);

        // Get the constatAudit
        restConstatAuditMockMvc.perform(get("/api/constat-audits/{id}", constatAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(constatAudit.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.constat").value(DEFAULT_CONSTAT))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE));
    }
    @Test
    @Transactional
    public void getNonExistingConstatAudit() throws Exception {
        // Get the constatAudit
        restConstatAuditMockMvc.perform(get("/api/constat-audits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConstatAudit() throws Exception {
        // Initialize the database
        constatAuditRepository.saveAndFlush(constatAudit);

        int databaseSizeBeforeUpdate = constatAuditRepository.findAll().size();

        // Update the constatAudit
        ConstatAudit updatedConstatAudit = constatAuditRepository.findById(constatAudit.getId()).get();
        // Disconnect from session so that the updates on updatedConstatAudit are not directly saved in db
        em.detach(updatedConstatAudit);
        updatedConstatAudit
            .type(UPDATED_TYPE)
            .constat(UPDATED_CONSTAT)
            .origine(UPDATED_ORIGINE);

        restConstatAuditMockMvc.perform(put("/api/constat-audits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConstatAudit)))
            .andExpect(status().isOk());

        // Validate the ConstatAudit in the database
        List<ConstatAudit> constatAuditList = constatAuditRepository.findAll();
        assertThat(constatAuditList).hasSize(databaseSizeBeforeUpdate);
        ConstatAudit testConstatAudit = constatAuditList.get(constatAuditList.size() - 1);
        assertThat(testConstatAudit.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConstatAudit.getConstat()).isEqualTo(UPDATED_CONSTAT);
        assertThat(testConstatAudit.getOrigine()).isEqualTo(UPDATED_ORIGINE);

        // Validate the ConstatAudit in Elasticsearch
        verify(mockConstatAuditSearchRepository, times(1)).save(testConstatAudit);
    }

    @Test
    @Transactional
    public void updateNonExistingConstatAudit() throws Exception {
        int databaseSizeBeforeUpdate = constatAuditRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConstatAuditMockMvc.perform(put("/api/constat-audits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constatAudit)))
            .andExpect(status().isBadRequest());

        // Validate the ConstatAudit in the database
        List<ConstatAudit> constatAuditList = constatAuditRepository.findAll();
        assertThat(constatAuditList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ConstatAudit in Elasticsearch
        verify(mockConstatAuditSearchRepository, times(0)).save(constatAudit);
    }

    @Test
    @Transactional
    public void deleteConstatAudit() throws Exception {
        // Initialize the database
        constatAuditRepository.saveAndFlush(constatAudit);

        int databaseSizeBeforeDelete = constatAuditRepository.findAll().size();

        // Delete the constatAudit
        restConstatAuditMockMvc.perform(delete("/api/constat-audits/{id}", constatAudit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConstatAudit> constatAuditList = constatAuditRepository.findAll();
        assertThat(constatAuditList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ConstatAudit in Elasticsearch
        verify(mockConstatAuditSearchRepository, times(1)).deleteById(constatAudit.getId());
    }

    @Test
    @Transactional
    public void searchConstatAudit() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        constatAuditRepository.saveAndFlush(constatAudit);
        when(mockConstatAuditSearchRepository.search(queryStringQuery("id:" + constatAudit.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(constatAudit), PageRequest.of(0, 1), 1));

        // Search the constatAudit
        restConstatAuditMockMvc.perform(get("/api/_search/constat-audits?query=id:" + constatAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(constatAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].constat").value(hasItem(DEFAULT_CONSTAT)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
}
