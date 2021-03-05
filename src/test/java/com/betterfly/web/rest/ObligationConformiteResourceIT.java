package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.ObligationConformite;
import com.betterfly.repository.ObligationConformiteRepository;
import com.betterfly.repository.search.ObligationConformiteSearchRepository;

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

import com.betterfly.domain.enumeration.Rubrique;
/**
 * Integration tests for the {@link ObligationConformiteResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ObligationConformiteResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Rubrique DEFAULT_RUBRIQUE = Rubrique.RUBRIQUE;
    private static final Rubrique UPDATED_RUBRIQUE = Rubrique.NETTOYAGE;

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM = 1;
    private static final Integer UPDATED_NUM = 2;

    private static final String DEFAULT_EXIGENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXIGENCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPLICABLE = false;
    private static final Boolean UPDATED_APPLICABLE = true;

    private static final Boolean DEFAULT_CONFORME = false;
    private static final Boolean UPDATED_CONFORME = true;

    private static final Integer DEFAULT_STATUT = 1;
    private static final Integer UPDATED_STATUT = 2;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESSUS = "AAAAAAAAAA";
    private static final String UPDATED_PROCESSUS = "BBBBBBBBBB";

    private static final String DEFAULT_O_RIGINE = "AAAAAAAAAA";
    private static final String UPDATED_O_RIGINE = "BBBBBBBBBB";

    @Autowired
    private ObligationConformiteRepository obligationConformiteRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.ObligationConformiteSearchRepositoryMockConfiguration
     */
    @Autowired
    private ObligationConformiteSearchRepository mockObligationConformiteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObligationConformiteMockMvc;

    private ObligationConformite obligationConformite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObligationConformite createEntity(EntityManager em) {
        ObligationConformite obligationConformite = new ObligationConformite()
            .date(DEFAULT_DATE)
            .rubrique(DEFAULT_RUBRIQUE)
            .reference(DEFAULT_REFERENCE)
            .num(DEFAULT_NUM)
            .exigence(DEFAULT_EXIGENCE)
            .applicable(DEFAULT_APPLICABLE)
            .conforme(DEFAULT_CONFORME)
            .statut(DEFAULT_STATUT)
            .observation(DEFAULT_OBSERVATION)
            .processus(DEFAULT_PROCESSUS)
            .oRIGINE(DEFAULT_O_RIGINE);
        return obligationConformite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObligationConformite createUpdatedEntity(EntityManager em) {
        ObligationConformite obligationConformite = new ObligationConformite()
            .date(UPDATED_DATE)
            .rubrique(UPDATED_RUBRIQUE)
            .reference(UPDATED_REFERENCE)
            .num(UPDATED_NUM)
            .exigence(UPDATED_EXIGENCE)
            .applicable(UPDATED_APPLICABLE)
            .conforme(UPDATED_CONFORME)
            .statut(UPDATED_STATUT)
            .observation(UPDATED_OBSERVATION)
            .processus(UPDATED_PROCESSUS)
            .oRIGINE(UPDATED_O_RIGINE);
        return obligationConformite;
    }

    @BeforeEach
    public void initTest() {
        obligationConformite = createEntity(em);
    }

    @Test
    @Transactional
    public void createObligationConformite() throws Exception {
        int databaseSizeBeforeCreate = obligationConformiteRepository.findAll().size();
        // Create the ObligationConformite
        restObligationConformiteMockMvc.perform(post("/api/obligation-conformites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(obligationConformite)))
            .andExpect(status().isCreated());

        // Validate the ObligationConformite in the database
        List<ObligationConformite> obligationConformiteList = obligationConformiteRepository.findAll();
        assertThat(obligationConformiteList).hasSize(databaseSizeBeforeCreate + 1);
        ObligationConformite testObligationConformite = obligationConformiteList.get(obligationConformiteList.size() - 1);
        assertThat(testObligationConformite.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testObligationConformite.getRubrique()).isEqualTo(DEFAULT_RUBRIQUE);
        assertThat(testObligationConformite.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testObligationConformite.getNum()).isEqualTo(DEFAULT_NUM);
        assertThat(testObligationConformite.getExigence()).isEqualTo(DEFAULT_EXIGENCE);
        assertThat(testObligationConformite.isApplicable()).isEqualTo(DEFAULT_APPLICABLE);
        assertThat(testObligationConformite.isConforme()).isEqualTo(DEFAULT_CONFORME);
        assertThat(testObligationConformite.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testObligationConformite.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testObligationConformite.getProcessus()).isEqualTo(DEFAULT_PROCESSUS);
        assertThat(testObligationConformite.getoRIGINE()).isEqualTo(DEFAULT_O_RIGINE);

        // Validate the ObligationConformite in Elasticsearch
        verify(mockObligationConformiteSearchRepository, times(1)).save(testObligationConformite);
    }

    @Test
    @Transactional
    public void createObligationConformiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = obligationConformiteRepository.findAll().size();

        // Create the ObligationConformite with an existing ID
        obligationConformite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObligationConformiteMockMvc.perform(post("/api/obligation-conformites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(obligationConformite)))
            .andExpect(status().isBadRequest());

        // Validate the ObligationConformite in the database
        List<ObligationConformite> obligationConformiteList = obligationConformiteRepository.findAll();
        assertThat(obligationConformiteList).hasSize(databaseSizeBeforeCreate);

        // Validate the ObligationConformite in Elasticsearch
        verify(mockObligationConformiteSearchRepository, times(0)).save(obligationConformite);
    }


    @Test
    @Transactional
    public void getAllObligationConformites() throws Exception {
        // Initialize the database
        obligationConformiteRepository.saveAndFlush(obligationConformite);

        // Get all the obligationConformiteList
        restObligationConformiteMockMvc.perform(get("/api/obligation-conformites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obligationConformite.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].rubrique").value(hasItem(DEFAULT_RUBRIQUE.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].exigence").value(hasItem(DEFAULT_EXIGENCE)))
            .andExpect(jsonPath("$.[*].applicable").value(hasItem(DEFAULT_APPLICABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].conforme").value(hasItem(DEFAULT_CONFORME.booleanValue())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].oRIGINE").value(hasItem(DEFAULT_O_RIGINE)));
    }
    
    @Test
    @Transactional
    public void getObligationConformite() throws Exception {
        // Initialize the database
        obligationConformiteRepository.saveAndFlush(obligationConformite);

        // Get the obligationConformite
        restObligationConformiteMockMvc.perform(get("/api/obligation-conformites/{id}", obligationConformite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(obligationConformite.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.rubrique").value(DEFAULT_RUBRIQUE.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.exigence").value(DEFAULT_EXIGENCE))
            .andExpect(jsonPath("$.applicable").value(DEFAULT_APPLICABLE.booleanValue()))
            .andExpect(jsonPath("$.conforme").value(DEFAULT_CONFORME.booleanValue()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.processus").value(DEFAULT_PROCESSUS))
            .andExpect(jsonPath("$.oRIGINE").value(DEFAULT_O_RIGINE));
    }
    @Test
    @Transactional
    public void getNonExistingObligationConformite() throws Exception {
        // Get the obligationConformite
        restObligationConformiteMockMvc.perform(get("/api/obligation-conformites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObligationConformite() throws Exception {
        // Initialize the database
        obligationConformiteRepository.saveAndFlush(obligationConformite);

        int databaseSizeBeforeUpdate = obligationConformiteRepository.findAll().size();

        // Update the obligationConformite
        ObligationConformite updatedObligationConformite = obligationConformiteRepository.findById(obligationConformite.getId()).get();
        // Disconnect from session so that the updates on updatedObligationConformite are not directly saved in db
        em.detach(updatedObligationConformite);
        updatedObligationConformite
            .date(UPDATED_DATE)
            .rubrique(UPDATED_RUBRIQUE)
            .reference(UPDATED_REFERENCE)
            .num(UPDATED_NUM)
            .exigence(UPDATED_EXIGENCE)
            .applicable(UPDATED_APPLICABLE)
            .conforme(UPDATED_CONFORME)
            .statut(UPDATED_STATUT)
            .observation(UPDATED_OBSERVATION)
            .processus(UPDATED_PROCESSUS)
            .oRIGINE(UPDATED_O_RIGINE);

        restObligationConformiteMockMvc.perform(put("/api/obligation-conformites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedObligationConformite)))
            .andExpect(status().isOk());

        // Validate the ObligationConformite in the database
        List<ObligationConformite> obligationConformiteList = obligationConformiteRepository.findAll();
        assertThat(obligationConformiteList).hasSize(databaseSizeBeforeUpdate);
        ObligationConformite testObligationConformite = obligationConformiteList.get(obligationConformiteList.size() - 1);
        assertThat(testObligationConformite.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testObligationConformite.getRubrique()).isEqualTo(UPDATED_RUBRIQUE);
        assertThat(testObligationConformite.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testObligationConformite.getNum()).isEqualTo(UPDATED_NUM);
        assertThat(testObligationConformite.getExigence()).isEqualTo(UPDATED_EXIGENCE);
        assertThat(testObligationConformite.isApplicable()).isEqualTo(UPDATED_APPLICABLE);
        assertThat(testObligationConformite.isConforme()).isEqualTo(UPDATED_CONFORME);
        assertThat(testObligationConformite.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testObligationConformite.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testObligationConformite.getProcessus()).isEqualTo(UPDATED_PROCESSUS);
        assertThat(testObligationConformite.getoRIGINE()).isEqualTo(UPDATED_O_RIGINE);

        // Validate the ObligationConformite in Elasticsearch
        verify(mockObligationConformiteSearchRepository, times(1)).save(testObligationConformite);
    }

    @Test
    @Transactional
    public void updateNonExistingObligationConformite() throws Exception {
        int databaseSizeBeforeUpdate = obligationConformiteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObligationConformiteMockMvc.perform(put("/api/obligation-conformites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(obligationConformite)))
            .andExpect(status().isBadRequest());

        // Validate the ObligationConformite in the database
        List<ObligationConformite> obligationConformiteList = obligationConformiteRepository.findAll();
        assertThat(obligationConformiteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ObligationConformite in Elasticsearch
        verify(mockObligationConformiteSearchRepository, times(0)).save(obligationConformite);
    }

    @Test
    @Transactional
    public void deleteObligationConformite() throws Exception {
        // Initialize the database
        obligationConformiteRepository.saveAndFlush(obligationConformite);

        int databaseSizeBeforeDelete = obligationConformiteRepository.findAll().size();

        // Delete the obligationConformite
        restObligationConformiteMockMvc.perform(delete("/api/obligation-conformites/{id}", obligationConformite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ObligationConformite> obligationConformiteList = obligationConformiteRepository.findAll();
        assertThat(obligationConformiteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ObligationConformite in Elasticsearch
        verify(mockObligationConformiteSearchRepository, times(1)).deleteById(obligationConformite.getId());
    }

    @Test
    @Transactional
    public void searchObligationConformite() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        obligationConformiteRepository.saveAndFlush(obligationConformite);
        when(mockObligationConformiteSearchRepository.search(queryStringQuery("id:" + obligationConformite.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(obligationConformite), PageRequest.of(0, 1), 1));

        // Search the obligationConformite
        restObligationConformiteMockMvc.perform(get("/api/_search/obligation-conformites?query=id:" + obligationConformite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obligationConformite.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].rubrique").value(hasItem(DEFAULT_RUBRIQUE.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].exigence").value(hasItem(DEFAULT_EXIGENCE)))
            .andExpect(jsonPath("$.[*].applicable").value(hasItem(DEFAULT_APPLICABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].conforme").value(hasItem(DEFAULT_CONFORME.booleanValue())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].processus").value(hasItem(DEFAULT_PROCESSUS)))
            .andExpect(jsonPath("$.[*].oRIGINE").value(hasItem(DEFAULT_O_RIGINE)));
    }
}
