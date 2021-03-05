package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.Risque;
import com.betterfly.repository.RisqueRepository;
import com.betterfly.repository.search.RisqueSearchRepository;

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

import com.betterfly.domain.enumeration.TypeRisque;
import com.betterfly.domain.enumeration.EnumFive;
import com.betterfly.domain.enumeration.EnumFive;
import com.betterfly.domain.enumeration.Traitement;
/**
 * Integration tests for the {@link RisqueResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RisqueResourceIT {

    private static final String DEFAULT_PROCESSUS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_IDENTIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_IDENTIFICATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CAUSE_POTENTIELLE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE_POTENTIELLE = "BBBBBBBBBB";

    private static final String DEFAULT_EFFET_POTENTIEL = "AAAAAAAAAA";
    private static final String UPDATED_EFFET_POTENTIEL = "BBBBBBBBBB";

    private static final TypeRisque DEFAULT_TYPE = TypeRisque.MENACE;
    private static final TypeRisque UPDATED_TYPE = TypeRisque.OPPORTUNITE;

    private static final EnumFive DEFAULT_GRAVITE = EnumFive.ONE;
    private static final EnumFive UPDATED_GRAVITE = EnumFive.TWO;

    private static final EnumFive DEFAULT_PROBABILITE = EnumFive.ONE;
    private static final EnumFive UPDATED_PROBABILITE = EnumFive.TWO;

    private static final Integer DEFAULT_CRITICITE = 1;
    private static final Integer UPDATED_CRITICITE = 2;

    private static final Traitement DEFAULT_TRAITEMENT = Traitement.ACCEPTE;
    private static final Traitement UPDATED_TRAITEMENT = Traitement.REFUSE;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE = "BBBBBBBBBB";

    @Autowired
    private RisqueRepository risqueRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.RisqueSearchRepositoryMockConfiguration
     */
    @Autowired
    private RisqueSearchRepository mockRisqueSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRisqueMockMvc;

    private Risque risque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Risque createEntity(EntityManager em) {
        Risque risque = new Risque()
            .processus(DEFAULT_PROCESSUS)
            .dateIdentification(DEFAULT_DATE_IDENTIFICATION)
            .description(DEFAULT_DESCRIPTION)
            .causePotentielle(DEFAULT_CAUSE_POTENTIELLE)
            .effetPotentiel(DEFAULT_EFFET_POTENTIEL)
            .type(DEFAULT_TYPE)
            .gravite(DEFAULT_GRAVITE)
            .probabilite(DEFAULT_PROBABILITE)
            .criticite(DEFAULT_CRITICITE)
            .traitement(DEFAULT_TRAITEMENT)
            .commentaire(DEFAULT_COMMENTAIRE)
            .origine(DEFAULT_ORIGINE);
        return risque;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Risque createUpdatedEntity(EntityManager em) {
        Risque risque = new Risque()
            .processus(UPDATED_PROCESSUS)
            .dateIdentification(UPDATED_DATE_IDENTIFICATION)
            .description(UPDATED_DESCRIPTION)
            .causePotentielle(UPDATED_CAUSE_POTENTIELLE)
            .effetPotentiel(UPDATED_EFFET_POTENTIEL)
            .type(UPDATED_TYPE)
            .gravite(UPDATED_GRAVITE)
            .probabilite(UPDATED_PROBABILITE)
            .criticite(UPDATED_CRITICITE)
            .traitement(UPDATED_TRAITEMENT)
            .commentaire(UPDATED_COMMENTAIRE)
            .origine(UPDATED_ORIGINE);
        return risque;
    }

    @BeforeEach
    public void initTest() {
        risque = createEntity(em);
    }

    @Test
    @Transactional
    public void createRisque() throws Exception {
        int databaseSizeBeforeCreate = risqueRepository.findAll().size();
        // Create the Risque
        restRisqueMockMvc.perform(post("/api/risques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(risque)))
            .andExpect(status().isCreated());

        // Validate the Risque in the database
        List<Risque> risqueList = risqueRepository.findAll();
        assertThat(risqueList).hasSize(databaseSizeBeforeCreate + 1);
        Risque testRisque = risqueList.get(risqueList.size() - 1);
        assertThat(testRisque.getProcessus()).isEqualTo(DEFAULT_PROCESSUS);
        assertThat(testRisque.getDateIdentification()).isEqualTo(DEFAULT_DATE_IDENTIFICATION);
        assertThat(testRisque.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRisque.getCausePotentielle()).isEqualTo(DEFAULT_CAUSE_POTENTIELLE);
        assertThat(testRisque.getEffetPotentiel()).isEqualTo(DEFAULT_EFFET_POTENTIEL);
        assertThat(testRisque.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRisque.getGravite()).isEqualTo(DEFAULT_GRAVITE);
        assertThat(testRisque.getProbabilite()).isEqualTo(DEFAULT_PROBABILITE);
        assertThat(testRisque.getCriticite()).isEqualTo(DEFAULT_CRITICITE);
        assertThat(testRisque.getTraitement()).isEqualTo(DEFAULT_TRAITEMENT);
        assertThat(testRisque.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testRisque.getOrigine()).isEqualTo(DEFAULT_ORIGINE);

        // Validate the Risque in Elasticsearch
        verify(mockRisqueSearchRepository, times(1)).save(testRisque);
    }

    @Test
    @Transactional
    public void createRisqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = risqueRepository.findAll().size();

        // Create the Risque with an existing ID
        risque.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRisqueMockMvc.perform(post("/api/risques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(risque)))
            .andExpect(status().isBadRequest());

        // Validate the Risque in the database
        List<Risque> risqueList = risqueRepository.findAll();
        assertThat(risqueList).hasSize(databaseSizeBeforeCreate);

        // Validate the Risque in Elasticsearch
        verify(mockRisqueSearchRepository, times(0)).save(risque);
    }


    @Test
    @Transactional
    public void getAllRisques() throws Exception {
        // Initialize the database
        risqueRepository.saveAndFlush(risque);

        // Get all the risqueList
        restRisqueMockMvc.perform(get("/api/risques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(risque.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].dateIdentification").value(hasItem(DEFAULT_DATE_IDENTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].causePotentielle").value(hasItem(DEFAULT_CAUSE_POTENTIELLE)))
            .andExpect(jsonPath("$.[*].effetPotentiel").value(hasItem(DEFAULT_EFFET_POTENTIEL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gravite").value(hasItem(DEFAULT_GRAVITE.toString())))
            .andExpect(jsonPath("$.[*].probabilite").value(hasItem(DEFAULT_PROBABILITE.toString())))
            .andExpect(jsonPath("$.[*].criticite").value(hasItem(DEFAULT_CRITICITE)))
            .andExpect(jsonPath("$.[*].traitement").value(hasItem(DEFAULT_TRAITEMENT.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
    
    @Test
    @Transactional
    public void getRisque() throws Exception {
        // Initialize the database
        risqueRepository.saveAndFlush(risque);

        // Get the risque
        restRisqueMockMvc.perform(get("/api/risques/{id}", risque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(risque.getId().intValue()))
            .andExpect(jsonPath("$.processus").value(DEFAULT_PROCESSUS))
            .andExpect(jsonPath("$.dateIdentification").value(DEFAULT_DATE_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.causePotentielle").value(DEFAULT_CAUSE_POTENTIELLE))
            .andExpect(jsonPath("$.effetPotentiel").value(DEFAULT_EFFET_POTENTIEL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.gravite").value(DEFAULT_GRAVITE.toString()))
            .andExpect(jsonPath("$.probabilite").value(DEFAULT_PROBABILITE.toString()))
            .andExpect(jsonPath("$.criticite").value(DEFAULT_CRITICITE))
            .andExpect(jsonPath("$.traitement").value(DEFAULT_TRAITEMENT.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE));
    }
    @Test
    @Transactional
    public void getNonExistingRisque() throws Exception {
        // Get the risque
        restRisqueMockMvc.perform(get("/api/risques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRisque() throws Exception {
        // Initialize the database
        risqueRepository.saveAndFlush(risque);

        int databaseSizeBeforeUpdate = risqueRepository.findAll().size();

        // Update the risque
        Risque updatedRisque = risqueRepository.findById(risque.getId()).get();
        // Disconnect from session so that the updates on updatedRisque are not directly saved in db
        em.detach(updatedRisque);
        updatedRisque
            .processus(UPDATED_PROCESSUS)
            .dateIdentification(UPDATED_DATE_IDENTIFICATION)
            .description(UPDATED_DESCRIPTION)
            .causePotentielle(UPDATED_CAUSE_POTENTIELLE)
            .effetPotentiel(UPDATED_EFFET_POTENTIEL)
            .type(UPDATED_TYPE)
            .gravite(UPDATED_GRAVITE)
            .probabilite(UPDATED_PROBABILITE)
            .criticite(UPDATED_CRITICITE)
            .traitement(UPDATED_TRAITEMENT)
            .commentaire(UPDATED_COMMENTAIRE)
            .origine(UPDATED_ORIGINE);

        restRisqueMockMvc.perform(put("/api/risques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRisque)))
            .andExpect(status().isOk());

        // Validate the Risque in the database
        List<Risque> risqueList = risqueRepository.findAll();
        assertThat(risqueList).hasSize(databaseSizeBeforeUpdate);
        Risque testRisque = risqueList.get(risqueList.size() - 1);
        assertThat(testRisque.getProcessus()).isEqualTo(UPDATED_PROCESSUS);
        assertThat(testRisque.getDateIdentification()).isEqualTo(UPDATED_DATE_IDENTIFICATION);
        assertThat(testRisque.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRisque.getCausePotentielle()).isEqualTo(UPDATED_CAUSE_POTENTIELLE);
        assertThat(testRisque.getEffetPotentiel()).isEqualTo(UPDATED_EFFET_POTENTIEL);
        assertThat(testRisque.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRisque.getGravite()).isEqualTo(UPDATED_GRAVITE);
        assertThat(testRisque.getProbabilite()).isEqualTo(UPDATED_PROBABILITE);
        assertThat(testRisque.getCriticite()).isEqualTo(UPDATED_CRITICITE);
        assertThat(testRisque.getTraitement()).isEqualTo(UPDATED_TRAITEMENT);
        assertThat(testRisque.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testRisque.getOrigine()).isEqualTo(UPDATED_ORIGINE);

        // Validate the Risque in Elasticsearch
        verify(mockRisqueSearchRepository, times(1)).save(testRisque);
    }

    @Test
    @Transactional
    public void updateNonExistingRisque() throws Exception {
        int databaseSizeBeforeUpdate = risqueRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRisqueMockMvc.perform(put("/api/risques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(risque)))
            .andExpect(status().isBadRequest());

        // Validate the Risque in the database
        List<Risque> risqueList = risqueRepository.findAll();
        assertThat(risqueList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Risque in Elasticsearch
        verify(mockRisqueSearchRepository, times(0)).save(risque);
    }

    @Test
    @Transactional
    public void deleteRisque() throws Exception {
        // Initialize the database
        risqueRepository.saveAndFlush(risque);

        int databaseSizeBeforeDelete = risqueRepository.findAll().size();

        // Delete the risque
        restRisqueMockMvc.perform(delete("/api/risques/{id}", risque.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Risque> risqueList = risqueRepository.findAll();
        assertThat(risqueList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Risque in Elasticsearch
        verify(mockRisqueSearchRepository, times(1)).deleteById(risque.getId());
    }

    @Test
    @Transactional
    public void searchRisque() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        risqueRepository.saveAndFlush(risque);
        when(mockRisqueSearchRepository.search(queryStringQuery("id:" + risque.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(risque), PageRequest.of(0, 1), 1));

        // Search the risque
        restRisqueMockMvc.perform(get("/api/_search/risques?query=id:" + risque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(risque.getId().intValue())))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].dateIdentification").value(hasItem(DEFAULT_DATE_IDENTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].causePotentielle").value(hasItem(DEFAULT_CAUSE_POTENTIELLE)))
            .andExpect(jsonPath("$.[*].effetPotentiel").value(hasItem(DEFAULT_EFFET_POTENTIEL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gravite").value(hasItem(DEFAULT_GRAVITE.toString())))
            .andExpect(jsonPath("$.[*].probabilite").value(hasItem(DEFAULT_PROBABILITE.toString())))
            .andExpect(jsonPath("$.[*].criticite").value(hasItem(DEFAULT_CRITICITE)))
            .andExpect(jsonPath("$.[*].traitement").value(hasItem(DEFAULT_TRAITEMENT.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
}
