package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.IndicateurSMI;
import com.betterfly.repository.IndicateurSMIRepository;
import com.betterfly.repository.search.IndicateurSMISearchRepository;

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
 * Integration tests for the {@link IndicateurSMIResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class IndicateurSMIResourceIT {

    private static final String DEFAULT_PROCESSUS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_IDENTIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_IDENTIFICATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INDICATEUR = "AAAAAAAAAA";
    private static final String UPDATED_INDICATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_FORMULE_CALCUL = "AAAAAAAAAA";
    private static final String UPDATED_FORMULE_CALCUL = "BBBBBBBBBB";

    private static final Float DEFAULT_CIBLE = 1F;
    private static final Float UPDATED_CIBLE = 2F;

    private static final Float DEFAULT_SEUIL_TOLERANCE = 1F;
    private static final Float UPDATED_SEUIL_TOLERANCE = 2F;

    private static final String DEFAULT_UNITE = "AAAAAAAAAA";
    private static final String UPDATED_UNITE = "BBBBBBBBBB";

    private static final String DEFAULT_PERIODICITE = "AAAAAAAAAA";
    private static final String UPDATED_PERIODICITE = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSABLE_CALCUL = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSABLE_CALCUL = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VIGUEUR = false;
    private static final Boolean UPDATED_VIGUEUR = true;

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    @Autowired
    private IndicateurSMIRepository indicateurSMIRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.IndicateurSMISearchRepositoryMockConfiguration
     */
    @Autowired
    private IndicateurSMISearchRepository mockIndicateurSMISearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicateurSMIMockMvc;

    private IndicateurSMI indicateurSMI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicateurSMI createEntity(EntityManager em) {
        IndicateurSMI indicateurSMI = new IndicateurSMI()
            .processus(DEFAULT_PROCESSUS)
            .dateIdentification(DEFAULT_DATE_IDENTIFICATION)
            .indicateur(DEFAULT_INDICATEUR)
            .formuleCalcul(DEFAULT_FORMULE_CALCUL)
            .cible(DEFAULT_CIBLE)
            .seuilTolerance(DEFAULT_SEUIL_TOLERANCE)
            .unite(DEFAULT_UNITE)
            .periodicite(DEFAULT_PERIODICITE)
            .responsableCalcul(DEFAULT_RESPONSABLE_CALCUL)
            .observations(DEFAULT_OBSERVATIONS)
            .vigueur(DEFAULT_VIGUEUR)
            .annee(DEFAULT_ANNEE)
            .observation(DEFAULT_OBSERVATION);
        return indicateurSMI;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicateurSMI createUpdatedEntity(EntityManager em) {
        IndicateurSMI indicateurSMI = new IndicateurSMI()
            .processus(UPDATED_PROCESSUS)
            .dateIdentification(UPDATED_DATE_IDENTIFICATION)
            .indicateur(UPDATED_INDICATEUR)
            .formuleCalcul(UPDATED_FORMULE_CALCUL)
            .cible(UPDATED_CIBLE)
            .seuilTolerance(UPDATED_SEUIL_TOLERANCE)
            .unite(UPDATED_UNITE)
            .periodicite(UPDATED_PERIODICITE)
            .responsableCalcul(UPDATED_RESPONSABLE_CALCUL)
            .observations(UPDATED_OBSERVATIONS)
            .vigueur(UPDATED_VIGUEUR)
            .annee(UPDATED_ANNEE)
            .observation(UPDATED_OBSERVATION);
        return indicateurSMI;
    }

    @BeforeEach
    public void initTest() {
        indicateurSMI = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndicateurSMI() throws Exception {
        int databaseSizeBeforeCreate = indicateurSMIRepository.findAll().size();
        // Create the IndicateurSMI
        restIndicateurSMIMockMvc.perform(post("/api/indicateur-smis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicateurSMI)))
            .andExpect(status().isCreated());

        // Validate the IndicateurSMI in the database
        List<IndicateurSMI> indicateurSMIList = indicateurSMIRepository.findAll();
        assertThat(indicateurSMIList).hasSize(databaseSizeBeforeCreate + 1);
        IndicateurSMI testIndicateurSMI = indicateurSMIList.get(indicateurSMIList.size() - 1);
        assertThat(testIndicateurSMI.getProcessus()).isEqualTo(DEFAULT_PROCESSUS);
        assertThat(testIndicateurSMI.getDateIdentification()).isEqualTo(DEFAULT_DATE_IDENTIFICATION);
        assertThat(testIndicateurSMI.getIndicateur()).isEqualTo(DEFAULT_INDICATEUR);
        assertThat(testIndicateurSMI.getFormuleCalcul()).isEqualTo(DEFAULT_FORMULE_CALCUL);
        assertThat(testIndicateurSMI.getCible()).isEqualTo(DEFAULT_CIBLE);
        assertThat(testIndicateurSMI.getSeuilTolerance()).isEqualTo(DEFAULT_SEUIL_TOLERANCE);
        assertThat(testIndicateurSMI.getUnite()).isEqualTo(DEFAULT_UNITE);
        assertThat(testIndicateurSMI.getPeriodicite()).isEqualTo(DEFAULT_PERIODICITE);
        assertThat(testIndicateurSMI.getResponsableCalcul()).isEqualTo(DEFAULT_RESPONSABLE_CALCUL);
        assertThat(testIndicateurSMI.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);
        assertThat(testIndicateurSMI.isVigueur()).isEqualTo(DEFAULT_VIGUEUR);
        assertThat(testIndicateurSMI.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testIndicateurSMI.getObservation()).isEqualTo(DEFAULT_OBSERVATION);

        // Validate the IndicateurSMI in Elasticsearch
        verify(mockIndicateurSMISearchRepository, times(1)).save(testIndicateurSMI);
    }

    @Test
    @Transactional
    public void createIndicateurSMIWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indicateurSMIRepository.findAll().size();

        // Create the IndicateurSMI with an existing ID
        indicateurSMI.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicateurSMIMockMvc.perform(post("/api/indicateur-smis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicateurSMI)))
            .andExpect(status().isBadRequest());

        // Validate the IndicateurSMI in the database
        List<IndicateurSMI> indicateurSMIList = indicateurSMIRepository.findAll();
        assertThat(indicateurSMIList).hasSize(databaseSizeBeforeCreate);

        // Validate the IndicateurSMI in Elasticsearch
        verify(mockIndicateurSMISearchRepository, times(0)).save(indicateurSMI);
    }


    @Test
    @Transactional
    public void getAllIndicateurSMIS() throws Exception {
        // Initialize the database
        indicateurSMIRepository.saveAndFlush(indicateurSMI);

        // Get all the indicateurSMIList
        restIndicateurSMIMockMvc.perform(get("/api/indicateur-smis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicateurSMI.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].dateIdentification").value(hasItem(DEFAULT_DATE_IDENTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].indicateur").value(hasItem(DEFAULT_INDICATEUR)))
            .andExpect(jsonPath("$.[*].formuleCalcul").value(hasItem(DEFAULT_FORMULE_CALCUL)))
            .andExpect(jsonPath("$.[*].cible").value(hasItem(DEFAULT_CIBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].seuilTolerance").value(hasItem(DEFAULT_SEUIL_TOLERANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].unite").value(hasItem(DEFAULT_UNITE)))
            .andExpect(jsonPath("$.[*].periodicite").value(hasItem(DEFAULT_PERIODICITE)))
            .andExpect(jsonPath("$.[*].responsableCalcul").value(hasItem(DEFAULT_RESPONSABLE_CALCUL)))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)))
            .andExpect(jsonPath("$.[*].vigueur").value(hasItem(DEFAULT_VIGUEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)));
    }
    
    @Test
    @Transactional
    public void getIndicateurSMI() throws Exception {
        // Initialize the database
        indicateurSMIRepository.saveAndFlush(indicateurSMI);

        // Get the indicateurSMI
        restIndicateurSMIMockMvc.perform(get("/api/indicateur-smis/{id}", indicateurSMI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indicateurSMI.getId().intValue()))
            .andExpect(jsonPath("$.processus").value(DEFAULT_PROCESSUS))
            .andExpect(jsonPath("$.dateIdentification").value(DEFAULT_DATE_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.indicateur").value(DEFAULT_INDICATEUR))
            .andExpect(jsonPath("$.formuleCalcul").value(DEFAULT_FORMULE_CALCUL))
            .andExpect(jsonPath("$.cible").value(DEFAULT_CIBLE.doubleValue()))
            .andExpect(jsonPath("$.seuilTolerance").value(DEFAULT_SEUIL_TOLERANCE.doubleValue()))
            .andExpect(jsonPath("$.unite").value(DEFAULT_UNITE))
            .andExpect(jsonPath("$.periodicite").value(DEFAULT_PERIODICITE))
            .andExpect(jsonPath("$.responsableCalcul").value(DEFAULT_RESPONSABLE_CALCUL))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS))
            .andExpect(jsonPath("$.vigueur").value(DEFAULT_VIGUEUR.booleanValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION));
    }
    @Test
    @Transactional
    public void getNonExistingIndicateurSMI() throws Exception {
        // Get the indicateurSMI
        restIndicateurSMIMockMvc.perform(get("/api/indicateur-smis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndicateurSMI() throws Exception {
        // Initialize the database
        indicateurSMIRepository.saveAndFlush(indicateurSMI);

        int databaseSizeBeforeUpdate = indicateurSMIRepository.findAll().size();

        // Update the indicateurSMI
        IndicateurSMI updatedIndicateurSMI = indicateurSMIRepository.findById(indicateurSMI.getId()).get();
        // Disconnect from session so that the updates on updatedIndicateurSMI are not directly saved in db
        em.detach(updatedIndicateurSMI);
        updatedIndicateurSMI
            .processus(UPDATED_PROCESSUS)
            .dateIdentification(UPDATED_DATE_IDENTIFICATION)
            .indicateur(UPDATED_INDICATEUR)
            .formuleCalcul(UPDATED_FORMULE_CALCUL)
            .cible(UPDATED_CIBLE)
            .seuilTolerance(UPDATED_SEUIL_TOLERANCE)
            .unite(UPDATED_UNITE)
            .periodicite(UPDATED_PERIODICITE)
            .responsableCalcul(UPDATED_RESPONSABLE_CALCUL)
            .observations(UPDATED_OBSERVATIONS)
            .vigueur(UPDATED_VIGUEUR)
            .annee(UPDATED_ANNEE)
            .observation(UPDATED_OBSERVATION);

        restIndicateurSMIMockMvc.perform(put("/api/indicateur-smis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndicateurSMI)))
            .andExpect(status().isOk());

        // Validate the IndicateurSMI in the database
        List<IndicateurSMI> indicateurSMIList = indicateurSMIRepository.findAll();
        assertThat(indicateurSMIList).hasSize(databaseSizeBeforeUpdate);
        IndicateurSMI testIndicateurSMI = indicateurSMIList.get(indicateurSMIList.size() - 1);
        assertThat(testIndicateurSMI.getProcessus()).isEqualTo(UPDATED_PROCESSUS);
        assertThat(testIndicateurSMI.getDateIdentification()).isEqualTo(UPDATED_DATE_IDENTIFICATION);
        assertThat(testIndicateurSMI.getIndicateur()).isEqualTo(UPDATED_INDICATEUR);
        assertThat(testIndicateurSMI.getFormuleCalcul()).isEqualTo(UPDATED_FORMULE_CALCUL);
        assertThat(testIndicateurSMI.getCible()).isEqualTo(UPDATED_CIBLE);
        assertThat(testIndicateurSMI.getSeuilTolerance()).isEqualTo(UPDATED_SEUIL_TOLERANCE);
        assertThat(testIndicateurSMI.getUnite()).isEqualTo(UPDATED_UNITE);
        assertThat(testIndicateurSMI.getPeriodicite()).isEqualTo(UPDATED_PERIODICITE);
        assertThat(testIndicateurSMI.getResponsableCalcul()).isEqualTo(UPDATED_RESPONSABLE_CALCUL);
        assertThat(testIndicateurSMI.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);
        assertThat(testIndicateurSMI.isVigueur()).isEqualTo(UPDATED_VIGUEUR);
        assertThat(testIndicateurSMI.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testIndicateurSMI.getObservation()).isEqualTo(UPDATED_OBSERVATION);

        // Validate the IndicateurSMI in Elasticsearch
        verify(mockIndicateurSMISearchRepository, times(1)).save(testIndicateurSMI);
    }

    @Test
    @Transactional
    public void updateNonExistingIndicateurSMI() throws Exception {
        int databaseSizeBeforeUpdate = indicateurSMIRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicateurSMIMockMvc.perform(put("/api/indicateur-smis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(indicateurSMI)))
            .andExpect(status().isBadRequest());

        // Validate the IndicateurSMI in the database
        List<IndicateurSMI> indicateurSMIList = indicateurSMIRepository.findAll();
        assertThat(indicateurSMIList).hasSize(databaseSizeBeforeUpdate);

        // Validate the IndicateurSMI in Elasticsearch
        verify(mockIndicateurSMISearchRepository, times(0)).save(indicateurSMI);
    }

    @Test
    @Transactional
    public void deleteIndicateurSMI() throws Exception {
        // Initialize the database
        indicateurSMIRepository.saveAndFlush(indicateurSMI);

        int databaseSizeBeforeDelete = indicateurSMIRepository.findAll().size();

        // Delete the indicateurSMI
        restIndicateurSMIMockMvc.perform(delete("/api/indicateur-smis/{id}", indicateurSMI.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndicateurSMI> indicateurSMIList = indicateurSMIRepository.findAll();
        assertThat(indicateurSMIList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the IndicateurSMI in Elasticsearch
        verify(mockIndicateurSMISearchRepository, times(1)).deleteById(indicateurSMI.getId());
    }

    @Test
    @Transactional
    public void searchIndicateurSMI() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        indicateurSMIRepository.saveAndFlush(indicateurSMI);
        when(mockIndicateurSMISearchRepository.search(queryStringQuery("id:" + indicateurSMI.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(indicateurSMI), PageRequest.of(0, 1), 1));

        // Search the indicateurSMI
        restIndicateurSMIMockMvc.perform(get("/api/_search/indicateur-smis?query=id:" + indicateurSMI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicateurSMI.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].dateIdentification").value(hasItem(DEFAULT_DATE_IDENTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].indicateur").value(hasItem(DEFAULT_INDICATEUR)))
            .andExpect(jsonPath("$.[*].formuleCalcul").value(hasItem(DEFAULT_FORMULE_CALCUL)))
            .andExpect(jsonPath("$.[*].cible").value(hasItem(DEFAULT_CIBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].seuilTolerance").value(hasItem(DEFAULT_SEUIL_TOLERANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].unite").value(hasItem(DEFAULT_UNITE)))
            .andExpect(jsonPath("$.[*].periodicite").value(hasItem(DEFAULT_PERIODICITE)))
            .andExpect(jsonPath("$.[*].responsableCalcul").value(hasItem(DEFAULT_RESPONSABLE_CALCUL)))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)))
            .andExpect(jsonPath("$.[*].vigueur").value(hasItem(DEFAULT_VIGUEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)));
    }
}
