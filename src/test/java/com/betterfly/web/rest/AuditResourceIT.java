package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.Audit;
import com.betterfly.repository.AuditRepository;
import com.betterfly.repository.search.AuditSearchRepository;

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

import com.betterfly.domain.enumeration.TypeAudit;
import com.betterfly.domain.enumeration.Standard;
import com.betterfly.domain.enumeration.StatutAudit;
/**
 * Integration tests for the {@link AuditResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AuditResourceIT {

    private static final LocalDate DEFAULT_DATE_AUDIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AUDIT = LocalDate.now(ZoneId.systemDefault());

    private static final TypeAudit DEFAULT_TYPE_AUDIT = TypeAudit.Interne;
    private static final TypeAudit UPDATED_TYPE_AUDIT = TypeAudit.Externe;

    private static final String DEFAULT_AUDITEUR = "AAAAAAAAAA";
    private static final String UPDATED_AUDITEUR = "BBBBBBBBBB";

    private static final Standard DEFAULT_STANDARD = Standard.ISO9001;
    private static final Standard UPDATED_STANDARD = Standard.ISO14001;

    private static final StatutAudit DEFAULT_STATUT = StatutAudit.Retard;
    private static final StatutAudit UPDATED_STATUT = StatutAudit.REALISE;

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    @Autowired
    private AuditRepository auditRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.AuditSearchRepositoryMockConfiguration
     */
    @Autowired
    private AuditSearchRepository mockAuditSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditMockMvc;

    private Audit audit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Audit createEntity(EntityManager em) {
        Audit audit = new Audit()
            .dateAudit(DEFAULT_DATE_AUDIT)
            .typeAudit(DEFAULT_TYPE_AUDIT)
            .auditeur(DEFAULT_AUDITEUR)
            .standard(DEFAULT_STANDARD)
            .statut(DEFAULT_STATUT)
            .conclusion(DEFAULT_CONCLUSION);
        return audit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Audit createUpdatedEntity(EntityManager em) {
        Audit audit = new Audit()
            .dateAudit(UPDATED_DATE_AUDIT)
            .typeAudit(UPDATED_TYPE_AUDIT)
            .auditeur(UPDATED_AUDITEUR)
            .standard(UPDATED_STANDARD)
            .statut(UPDATED_STATUT)
            .conclusion(UPDATED_CONCLUSION);
        return audit;
    }

    @BeforeEach
    public void initTest() {
        audit = createEntity(em);
    }

    @Test
    @Transactional
    public void createAudit() throws Exception {
        int databaseSizeBeforeCreate = auditRepository.findAll().size();
        // Create the Audit
        restAuditMockMvc.perform(post("/api/audits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(audit)))
            .andExpect(status().isCreated());

        // Validate the Audit in the database
        List<Audit> auditList = auditRepository.findAll();
        assertThat(auditList).hasSize(databaseSizeBeforeCreate + 1);
        Audit testAudit = auditList.get(auditList.size() - 1);
        assertThat(testAudit.getDateAudit()).isEqualTo(DEFAULT_DATE_AUDIT);
        assertThat(testAudit.getTypeAudit()).isEqualTo(DEFAULT_TYPE_AUDIT);
        assertThat(testAudit.getAuditeur()).isEqualTo(DEFAULT_AUDITEUR);
        assertThat(testAudit.getStandard()).isEqualTo(DEFAULT_STANDARD);
        assertThat(testAudit.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testAudit.getConclusion()).isEqualTo(DEFAULT_CONCLUSION);

        // Validate the Audit in Elasticsearch
        verify(mockAuditSearchRepository, times(1)).save(testAudit);
    }

    @Test
    @Transactional
    public void createAuditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditRepository.findAll().size();

        // Create the Audit with an existing ID
        audit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditMockMvc.perform(post("/api/audits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(audit)))
            .andExpect(status().isBadRequest());

        // Validate the Audit in the database
        List<Audit> auditList = auditRepository.findAll();
        assertThat(auditList).hasSize(databaseSizeBeforeCreate);

        // Validate the Audit in Elasticsearch
        verify(mockAuditSearchRepository, times(0)).save(audit);
    }


    @Test
    @Transactional
    public void getAllAudits() throws Exception {
        // Initialize the database
        auditRepository.saveAndFlush(audit);

        // Get all the auditList
        restAuditMockMvc.perform(get("/api/audits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audit.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAudit").value(hasItem(DEFAULT_DATE_AUDIT.toString())))
            .andExpect(jsonPath("$.[*].typeAudit").value(hasItem(DEFAULT_TYPE_AUDIT.toString())))
            .andExpect(jsonPath("$.[*].auditeur").value(hasItem(DEFAULT_AUDITEUR)))
            .andExpect(jsonPath("$.[*].standard").value(hasItem(DEFAULT_STANDARD.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION)));
    }
    
    @Test
    @Transactional
    public void getAudit() throws Exception {
        // Initialize the database
        auditRepository.saveAndFlush(audit);

        // Get the audit
        restAuditMockMvc.perform(get("/api/audits/{id}", audit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(audit.getId().intValue()))
            .andExpect(jsonPath("$.dateAudit").value(DEFAULT_DATE_AUDIT.toString()))
            .andExpect(jsonPath("$.typeAudit").value(DEFAULT_TYPE_AUDIT.toString()))
            .andExpect(jsonPath("$.auditeur").value(DEFAULT_AUDITEUR))
            .andExpect(jsonPath("$.standard").value(DEFAULT_STANDARD.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION));
    }
    @Test
    @Transactional
    public void getNonExistingAudit() throws Exception {
        // Get the audit
        restAuditMockMvc.perform(get("/api/audits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudit() throws Exception {
        // Initialize the database
        auditRepository.saveAndFlush(audit);

        int databaseSizeBeforeUpdate = auditRepository.findAll().size();

        // Update the audit
        Audit updatedAudit = auditRepository.findById(audit.getId()).get();
        // Disconnect from session so that the updates on updatedAudit are not directly saved in db
        em.detach(updatedAudit);
        updatedAudit
            .dateAudit(UPDATED_DATE_AUDIT)
            .typeAudit(UPDATED_TYPE_AUDIT)
            .auditeur(UPDATED_AUDITEUR)
            .standard(UPDATED_STANDARD)
            .statut(UPDATED_STATUT)
            .conclusion(UPDATED_CONCLUSION);

        restAuditMockMvc.perform(put("/api/audits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAudit)))
            .andExpect(status().isOk());

        // Validate the Audit in the database
        List<Audit> auditList = auditRepository.findAll();
        assertThat(auditList).hasSize(databaseSizeBeforeUpdate);
        Audit testAudit = auditList.get(auditList.size() - 1);
        assertThat(testAudit.getDateAudit()).isEqualTo(UPDATED_DATE_AUDIT);
        assertThat(testAudit.getTypeAudit()).isEqualTo(UPDATED_TYPE_AUDIT);
        assertThat(testAudit.getAuditeur()).isEqualTo(UPDATED_AUDITEUR);
        assertThat(testAudit.getStandard()).isEqualTo(UPDATED_STANDARD);
        assertThat(testAudit.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testAudit.getConclusion()).isEqualTo(UPDATED_CONCLUSION);

        // Validate the Audit in Elasticsearch
        verify(mockAuditSearchRepository, times(1)).save(testAudit);
    }

    @Test
    @Transactional
    public void updateNonExistingAudit() throws Exception {
        int databaseSizeBeforeUpdate = auditRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditMockMvc.perform(put("/api/audits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(audit)))
            .andExpect(status().isBadRequest());

        // Validate the Audit in the database
        List<Audit> auditList = auditRepository.findAll();
        assertThat(auditList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Audit in Elasticsearch
        verify(mockAuditSearchRepository, times(0)).save(audit);
    }

    @Test
    @Transactional
    public void deleteAudit() throws Exception {
        // Initialize the database
        auditRepository.saveAndFlush(audit);

        int databaseSizeBeforeDelete = auditRepository.findAll().size();

        // Delete the audit
        restAuditMockMvc.perform(delete("/api/audits/{id}", audit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Audit> auditList = auditRepository.findAll();
        assertThat(auditList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Audit in Elasticsearch
        verify(mockAuditSearchRepository, times(1)).deleteById(audit.getId());
    }

    @Test
    @Transactional
    public void searchAudit() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        auditRepository.saveAndFlush(audit);
        when(mockAuditSearchRepository.search(queryStringQuery("id:" + audit.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(audit), PageRequest.of(0, 1), 1));

        // Search the audit
        restAuditMockMvc.perform(get("/api/_search/audits?query=id:" + audit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audit.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAudit").value(hasItem(DEFAULT_DATE_AUDIT.toString())))
            .andExpect(jsonPath("$.[*].typeAudit").value(hasItem(DEFAULT_TYPE_AUDIT.toString())))
            .andExpect(jsonPath("$.[*].auditeur").value(hasItem(DEFAULT_AUDITEUR)))
            .andExpect(jsonPath("$.[*].standard").value(hasItem(DEFAULT_STANDARD.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION)));
    }
}
