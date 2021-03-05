package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.AnalyseEnvirommentale;
import com.betterfly.repository.AnalyseEnvirommentaleRepository;
import com.betterfly.repository.search.AnalyseEnvirommentaleSearchRepository;

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

import com.betterfly.domain.enumeration.Situation;
import com.betterfly.domain.enumeration.EnumFive;
import com.betterfly.domain.enumeration.EnumFive;
import com.betterfly.domain.enumeration.EnumFive;
import com.betterfly.domain.enumeration.EnumFive;
/**
 * Integration tests for the {@link AnalyseEnvirommentaleResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AnalyseEnvirommentaleResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PROCESSUS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSUS = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_ASPECT_ENVIRONNEMENTAL = "AAAAAAAAAA";
    private static final String UPDATED_ASPECT_ENVIRONNEMENTAL = "BBBBBBBBBB";

    private static final String DEFAULT_IMPACT_ENVIRONNEMENTAL = "AAAAAAAAAA";
    private static final String UPDATED_IMPACT_ENVIRONNEMENTAL = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETENCES_REQUISES = "AAAAAAAAAA";
    private static final String UPDATED_COMPETENCES_REQUISES = "BBBBBBBBBB";

    private static final Situation DEFAULT_SITUATION = Situation.Normale;
    private static final Situation UPDATED_SITUATION = Situation.Anormale;

    private static final EnumFive DEFAULT_FREQUENCE = EnumFive.ONE;
    private static final EnumFive UPDATED_FREQUENCE = EnumFive.TWO;

    private static final EnumFive DEFAULT_SENSIBILITE_MILIEU = EnumFive.ONE;
    private static final EnumFive UPDATED_SENSIBILITE_MILIEU = EnumFive.TWO;

    private static final EnumFive DEFAULT_COEFFICIENT_MAITRISE = EnumFive.ONE;
    private static final EnumFive UPDATED_COEFFICIENT_MAITRISE = EnumFive.TWO;

    private static final EnumFive DEFAULT_GRAVITE = EnumFive.ONE;
    private static final EnumFive UPDATED_GRAVITE = EnumFive.TWO;

    private static final Integer DEFAULT_CRITICITE = 1;
    private static final Integer UPDATED_CRITICITE = 2;

    private static final String DEFAULT_MAITRISE_EXISTANTE = "AAAAAAAAAA";
    private static final String UPDATED_MAITRISE_EXISTANTE = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE = "BBBBBBBBBB";

    @Autowired
    private AnalyseEnvirommentaleRepository analyseEnvirommentaleRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.AnalyseEnvirommentaleSearchRepositoryMockConfiguration
     */
    @Autowired
    private AnalyseEnvirommentaleSearchRepository mockAnalyseEnvirommentaleSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalyseEnvirommentaleMockMvc;

    private AnalyseEnvirommentale analyseEnvirommentale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyseEnvirommentale createEntity(EntityManager em) {
        AnalyseEnvirommentale analyseEnvirommentale = new AnalyseEnvirommentale()
            .date(DEFAULT_DATE)
            .processus(DEFAULT_PROCESSUS)
            .businessUnit(DEFAULT_BUSINESS_UNIT)
            .activite(DEFAULT_ACTIVITE)
            .aspectEnvironnemental(DEFAULT_ASPECT_ENVIRONNEMENTAL)
            .impactEnvironnemental(DEFAULT_IMPACT_ENVIRONNEMENTAL)
            .competencesRequises(DEFAULT_COMPETENCES_REQUISES)
            .situation(DEFAULT_SITUATION)
            .frequence(DEFAULT_FREQUENCE)
            .sensibiliteMilieu(DEFAULT_SENSIBILITE_MILIEU)
            .coefficientMaitrise(DEFAULT_COEFFICIENT_MAITRISE)
            .gravite(DEFAULT_GRAVITE)
            .criticite(DEFAULT_CRITICITE)
            .maitriseExistante(DEFAULT_MAITRISE_EXISTANTE)
            .origine(DEFAULT_ORIGINE);
        return analyseEnvirommentale;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyseEnvirommentale createUpdatedEntity(EntityManager em) {
        AnalyseEnvirommentale analyseEnvirommentale = new AnalyseEnvirommentale()
            .date(UPDATED_DATE)
            .processus(UPDATED_PROCESSUS)
            .businessUnit(UPDATED_BUSINESS_UNIT)
            .activite(UPDATED_ACTIVITE)
            .aspectEnvironnemental(UPDATED_ASPECT_ENVIRONNEMENTAL)
            .impactEnvironnemental(UPDATED_IMPACT_ENVIRONNEMENTAL)
            .competencesRequises(UPDATED_COMPETENCES_REQUISES)
            .situation(UPDATED_SITUATION)
            .frequence(UPDATED_FREQUENCE)
            .sensibiliteMilieu(UPDATED_SENSIBILITE_MILIEU)
            .coefficientMaitrise(UPDATED_COEFFICIENT_MAITRISE)
            .gravite(UPDATED_GRAVITE)
            .criticite(UPDATED_CRITICITE)
            .maitriseExistante(UPDATED_MAITRISE_EXISTANTE)
            .origine(UPDATED_ORIGINE);
        return analyseEnvirommentale;
    }

    @BeforeEach
    public void initTest() {
        analyseEnvirommentale = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnalyseEnvirommentale() throws Exception {
        int databaseSizeBeforeCreate = analyseEnvirommentaleRepository.findAll().size();
        // Create the AnalyseEnvirommentale
        restAnalyseEnvirommentaleMockMvc.perform(post("/api/analyse-envirommentales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(analyseEnvirommentale)))
            .andExpect(status().isCreated());

        // Validate the AnalyseEnvirommentale in the database
        List<AnalyseEnvirommentale> analyseEnvirommentaleList = analyseEnvirommentaleRepository.findAll();
        assertThat(analyseEnvirommentaleList).hasSize(databaseSizeBeforeCreate + 1);
        AnalyseEnvirommentale testAnalyseEnvirommentale = analyseEnvirommentaleList.get(analyseEnvirommentaleList.size() - 1);
        assertThat(testAnalyseEnvirommentale.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAnalyseEnvirommentale.getProcessus()).isEqualTo(DEFAULT_PROCESSUS);
        assertThat(testAnalyseEnvirommentale.getBusinessUnit()).isEqualTo(DEFAULT_BUSINESS_UNIT);
        assertThat(testAnalyseEnvirommentale.getActivite()).isEqualTo(DEFAULT_ACTIVITE);
        assertThat(testAnalyseEnvirommentale.getAspectEnvironnemental()).isEqualTo(DEFAULT_ASPECT_ENVIRONNEMENTAL);
        assertThat(testAnalyseEnvirommentale.getImpactEnvironnemental()).isEqualTo(DEFAULT_IMPACT_ENVIRONNEMENTAL);
        assertThat(testAnalyseEnvirommentale.getCompetencesRequises()).isEqualTo(DEFAULT_COMPETENCES_REQUISES);
        assertThat(testAnalyseEnvirommentale.getSituation()).isEqualTo(DEFAULT_SITUATION);
        assertThat(testAnalyseEnvirommentale.getFrequence()).isEqualTo(DEFAULT_FREQUENCE);
        assertThat(testAnalyseEnvirommentale.getSensibiliteMilieu()).isEqualTo(DEFAULT_SENSIBILITE_MILIEU);
        assertThat(testAnalyseEnvirommentale.getCoefficientMaitrise()).isEqualTo(DEFAULT_COEFFICIENT_MAITRISE);
        assertThat(testAnalyseEnvirommentale.getGravite()).isEqualTo(DEFAULT_GRAVITE);
        assertThat(testAnalyseEnvirommentale.getCriticite()).isEqualTo(DEFAULT_CRITICITE);
        assertThat(testAnalyseEnvirommentale.getMaitriseExistante()).isEqualTo(DEFAULT_MAITRISE_EXISTANTE);
        assertThat(testAnalyseEnvirommentale.getOrigine()).isEqualTo(DEFAULT_ORIGINE);

        // Validate the AnalyseEnvirommentale in Elasticsearch
        verify(mockAnalyseEnvirommentaleSearchRepository, times(1)).save(testAnalyseEnvirommentale);
    }

    @Test
    @Transactional
    public void createAnalyseEnvirommentaleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = analyseEnvirommentaleRepository.findAll().size();

        // Create the AnalyseEnvirommentale with an existing ID
        analyseEnvirommentale.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalyseEnvirommentaleMockMvc.perform(post("/api/analyse-envirommentales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(analyseEnvirommentale)))
            .andExpect(status().isBadRequest());

        // Validate the AnalyseEnvirommentale in the database
        List<AnalyseEnvirommentale> analyseEnvirommentaleList = analyseEnvirommentaleRepository.findAll();
        assertThat(analyseEnvirommentaleList).hasSize(databaseSizeBeforeCreate);

        // Validate the AnalyseEnvirommentale in Elasticsearch
        verify(mockAnalyseEnvirommentaleSearchRepository, times(0)).save(analyseEnvirommentale);
    }


    @Test
    @Transactional
    public void getAllAnalyseEnvirommentales() throws Exception {
        // Initialize the database
        analyseEnvirommentaleRepository.saveAndFlush(analyseEnvirommentale);

        // Get all the analyseEnvirommentaleList
        restAnalyseEnvirommentaleMockMvc.perform(get("/api/analyse-envirommentales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analyseEnvirommentale.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].businessUnit").value(hasItem(DEFAULT_BUSINESS_UNIT)))
            .andExpect(jsonPath("$.[*].activite").value(hasItem(DEFAULT_ACTIVITE)))
            .andExpect(jsonPath("$.[*].aspectEnvironnemental").value(hasItem(DEFAULT_ASPECT_ENVIRONNEMENTAL)))
            .andExpect(jsonPath("$.[*].impactEnvironnemental").value(hasItem(DEFAULT_IMPACT_ENVIRONNEMENTAL)))
            .andExpect(jsonPath("$.[*].competencesRequises").value(hasItem(DEFAULT_COMPETENCES_REQUISES)))
            .andExpect(jsonPath("$.[*].situation").value(hasItem(DEFAULT_SITUATION.toString())))
            .andExpect(jsonPath("$.[*].frequence").value(hasItem(DEFAULT_FREQUENCE.toString())))
            .andExpect(jsonPath("$.[*].sensibiliteMilieu").value(hasItem(DEFAULT_SENSIBILITE_MILIEU.toString())))
            .andExpect(jsonPath("$.[*].coefficientMaitrise").value(hasItem(DEFAULT_COEFFICIENT_MAITRISE.toString())))
            .andExpect(jsonPath("$.[*].gravite").value(hasItem(DEFAULT_GRAVITE.toString())))
            .andExpect(jsonPath("$.[*].criticite").value(hasItem(DEFAULT_CRITICITE)))
            .andExpect(jsonPath("$.[*].maitriseExistante").value(hasItem(DEFAULT_MAITRISE_EXISTANTE)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
    
    @Test
    @Transactional
    public void getAnalyseEnvirommentale() throws Exception {
        // Initialize the database
        analyseEnvirommentaleRepository.saveAndFlush(analyseEnvirommentale);

        // Get the analyseEnvirommentale
        restAnalyseEnvirommentaleMockMvc.perform(get("/api/analyse-envirommentales/{id}", analyseEnvirommentale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analyseEnvirommentale.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.processus").value(DEFAULT_PROCESSUS))
            .andExpect(jsonPath("$.businessUnit").value(DEFAULT_BUSINESS_UNIT))
            .andExpect(jsonPath("$.activite").value(DEFAULT_ACTIVITE))
            .andExpect(jsonPath("$.aspectEnvironnemental").value(DEFAULT_ASPECT_ENVIRONNEMENTAL))
            .andExpect(jsonPath("$.impactEnvironnemental").value(DEFAULT_IMPACT_ENVIRONNEMENTAL))
            .andExpect(jsonPath("$.competencesRequises").value(DEFAULT_COMPETENCES_REQUISES))
            .andExpect(jsonPath("$.situation").value(DEFAULT_SITUATION.toString()))
            .andExpect(jsonPath("$.frequence").value(DEFAULT_FREQUENCE.toString()))
            .andExpect(jsonPath("$.sensibiliteMilieu").value(DEFAULT_SENSIBILITE_MILIEU.toString()))
            .andExpect(jsonPath("$.coefficientMaitrise").value(DEFAULT_COEFFICIENT_MAITRISE.toString()))
            .andExpect(jsonPath("$.gravite").value(DEFAULT_GRAVITE.toString()))
            .andExpect(jsonPath("$.criticite").value(DEFAULT_CRITICITE))
            .andExpect(jsonPath("$.maitriseExistante").value(DEFAULT_MAITRISE_EXISTANTE))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE));
    }
    @Test
    @Transactional
    public void getNonExistingAnalyseEnvirommentale() throws Exception {
        // Get the analyseEnvirommentale
        restAnalyseEnvirommentaleMockMvc.perform(get("/api/analyse-envirommentales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnalyseEnvirommentale() throws Exception {
        // Initialize the database
        analyseEnvirommentaleRepository.saveAndFlush(analyseEnvirommentale);

        int databaseSizeBeforeUpdate = analyseEnvirommentaleRepository.findAll().size();

        // Update the analyseEnvirommentale
        AnalyseEnvirommentale updatedAnalyseEnvirommentale = analyseEnvirommentaleRepository.findById(analyseEnvirommentale.getId()).get();
        // Disconnect from session so that the updates on updatedAnalyseEnvirommentale are not directly saved in db
        em.detach(updatedAnalyseEnvirommentale);
        updatedAnalyseEnvirommentale
            .date(UPDATED_DATE)
            .processus(UPDATED_PROCESSUS)
            .businessUnit(UPDATED_BUSINESS_UNIT)
            .activite(UPDATED_ACTIVITE)
            .aspectEnvironnemental(UPDATED_ASPECT_ENVIRONNEMENTAL)
            .impactEnvironnemental(UPDATED_IMPACT_ENVIRONNEMENTAL)
            .competencesRequises(UPDATED_COMPETENCES_REQUISES)
            .situation(UPDATED_SITUATION)
            .frequence(UPDATED_FREQUENCE)
            .sensibiliteMilieu(UPDATED_SENSIBILITE_MILIEU)
            .coefficientMaitrise(UPDATED_COEFFICIENT_MAITRISE)
            .gravite(UPDATED_GRAVITE)
            .criticite(UPDATED_CRITICITE)
            .maitriseExistante(UPDATED_MAITRISE_EXISTANTE)
            .origine(UPDATED_ORIGINE);

        restAnalyseEnvirommentaleMockMvc.perform(put("/api/analyse-envirommentales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnalyseEnvirommentale)))
            .andExpect(status().isOk());

        // Validate the AnalyseEnvirommentale in the database
        List<AnalyseEnvirommentale> analyseEnvirommentaleList = analyseEnvirommentaleRepository.findAll();
        assertThat(analyseEnvirommentaleList).hasSize(databaseSizeBeforeUpdate);
        AnalyseEnvirommentale testAnalyseEnvirommentale = analyseEnvirommentaleList.get(analyseEnvirommentaleList.size() - 1);
        assertThat(testAnalyseEnvirommentale.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAnalyseEnvirommentale.getProcessus()).isEqualTo(UPDATED_PROCESSUS);
        assertThat(testAnalyseEnvirommentale.getBusinessUnit()).isEqualTo(UPDATED_BUSINESS_UNIT);
        assertThat(testAnalyseEnvirommentale.getActivite()).isEqualTo(UPDATED_ACTIVITE);
        assertThat(testAnalyseEnvirommentale.getAspectEnvironnemental()).isEqualTo(UPDATED_ASPECT_ENVIRONNEMENTAL);
        assertThat(testAnalyseEnvirommentale.getImpactEnvironnemental()).isEqualTo(UPDATED_IMPACT_ENVIRONNEMENTAL);
        assertThat(testAnalyseEnvirommentale.getCompetencesRequises()).isEqualTo(UPDATED_COMPETENCES_REQUISES);
        assertThat(testAnalyseEnvirommentale.getSituation()).isEqualTo(UPDATED_SITUATION);
        assertThat(testAnalyseEnvirommentale.getFrequence()).isEqualTo(UPDATED_FREQUENCE);
        assertThat(testAnalyseEnvirommentale.getSensibiliteMilieu()).isEqualTo(UPDATED_SENSIBILITE_MILIEU);
        assertThat(testAnalyseEnvirommentale.getCoefficientMaitrise()).isEqualTo(UPDATED_COEFFICIENT_MAITRISE);
        assertThat(testAnalyseEnvirommentale.getGravite()).isEqualTo(UPDATED_GRAVITE);
        assertThat(testAnalyseEnvirommentale.getCriticite()).isEqualTo(UPDATED_CRITICITE);
        assertThat(testAnalyseEnvirommentale.getMaitriseExistante()).isEqualTo(UPDATED_MAITRISE_EXISTANTE);
        assertThat(testAnalyseEnvirommentale.getOrigine()).isEqualTo(UPDATED_ORIGINE);

        // Validate the AnalyseEnvirommentale in Elasticsearch
        verify(mockAnalyseEnvirommentaleSearchRepository, times(1)).save(testAnalyseEnvirommentale);
    }

    @Test
    @Transactional
    public void updateNonExistingAnalyseEnvirommentale() throws Exception {
        int databaseSizeBeforeUpdate = analyseEnvirommentaleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyseEnvirommentaleMockMvc.perform(put("/api/analyse-envirommentales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(analyseEnvirommentale)))
            .andExpect(status().isBadRequest());

        // Validate the AnalyseEnvirommentale in the database
        List<AnalyseEnvirommentale> analyseEnvirommentaleList = analyseEnvirommentaleRepository.findAll();
        assertThat(analyseEnvirommentaleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AnalyseEnvirommentale in Elasticsearch
        verify(mockAnalyseEnvirommentaleSearchRepository, times(0)).save(analyseEnvirommentale);
    }

    @Test
    @Transactional
    public void deleteAnalyseEnvirommentale() throws Exception {
        // Initialize the database
        analyseEnvirommentaleRepository.saveAndFlush(analyseEnvirommentale);

        int databaseSizeBeforeDelete = analyseEnvirommentaleRepository.findAll().size();

        // Delete the analyseEnvirommentale
        restAnalyseEnvirommentaleMockMvc.perform(delete("/api/analyse-envirommentales/{id}", analyseEnvirommentale.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnalyseEnvirommentale> analyseEnvirommentaleList = analyseEnvirommentaleRepository.findAll();
        assertThat(analyseEnvirommentaleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AnalyseEnvirommentale in Elasticsearch
        verify(mockAnalyseEnvirommentaleSearchRepository, times(1)).deleteById(analyseEnvirommentale.getId());
    }

    @Test
    @Transactional
    public void searchAnalyseEnvirommentale() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        analyseEnvirommentaleRepository.saveAndFlush(analyseEnvirommentale);
        when(mockAnalyseEnvirommentaleSearchRepository.search(queryStringQuery("id:" + analyseEnvirommentale.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(analyseEnvirommentale), PageRequest.of(0, 1), 1));

        // Search the analyseEnvirommentale
        restAnalyseEnvirommentaleMockMvc.perform(get("/api/_search/analyse-envirommentales?query=id:" + analyseEnvirommentale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analyseEnvirommentale.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].businessUnit").value(hasItem(DEFAULT_BUSINESS_UNIT)))
            .andExpect(jsonPath("$.[*].activite").value(hasItem(DEFAULT_ACTIVITE)))
            .andExpect(jsonPath("$.[*].aspectEnvironnemental").value(hasItem(DEFAULT_ASPECT_ENVIRONNEMENTAL)))
            .andExpect(jsonPath("$.[*].impactEnvironnemental").value(hasItem(DEFAULT_IMPACT_ENVIRONNEMENTAL)))
            .andExpect(jsonPath("$.[*].competencesRequises").value(hasItem(DEFAULT_COMPETENCES_REQUISES)))
            .andExpect(jsonPath("$.[*].situation").value(hasItem(DEFAULT_SITUATION.toString())))
            .andExpect(jsonPath("$.[*].frequence").value(hasItem(DEFAULT_FREQUENCE.toString())))
            .andExpect(jsonPath("$.[*].sensibiliteMilieu").value(hasItem(DEFAULT_SENSIBILITE_MILIEU.toString())))
            .andExpect(jsonPath("$.[*].coefficientMaitrise").value(hasItem(DEFAULT_COEFFICIENT_MAITRISE.toString())))
            .andExpect(jsonPath("$.[*].gravite").value(hasItem(DEFAULT_GRAVITE.toString())))
            .andExpect(jsonPath("$.[*].criticite").value(hasItem(DEFAULT_CRITICITE)))
            .andExpect(jsonPath("$.[*].maitriseExistante").value(hasItem(DEFAULT_MAITRISE_EXISTANTE)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
}
