package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.Action;
import com.betterfly.repository.ActionRepository;
import com.betterfly.repository.search.ActionSearchRepository;

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

import com.betterfly.domain.enumeration.Statut;
/**
 * Integration tests for the {@link ActionResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ActionResourceIT {

    private static final LocalDate DEFAULT_DATE_PLANIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PLANIFICATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DELAI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELAI = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_AVANCEMENT = "AAAAAAAAAA";
    private static final String UPDATED_AVANCEMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REALISEE = false;
    private static final Boolean UPDATED_REALISEE = true;

    private static final String DEFAULT_CRITERE_RESULTAT = "AAAAAAAAAA";
    private static final String UPDATED_CRITERE_RESULTAT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EFFICACE = false;
    private static final Boolean UPDATED_EFFICACE = true;

    private static final String DEFAULT_RESSOURCES_NECESSAIRES = "AAAAAAAAAA";
    private static final String UPDATED_RESSOURCES_NECESSAIRES = "BBBBBBBBBB";

    private static final Statut DEFAULT_STATUT = Statut.EN_COURS;
    private static final Statut UPDATED_STATUT = Statut.PLANIFIEE;

    @Autowired
    private ActionRepository actionRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.ActionSearchRepositoryMockConfiguration
     */
    @Autowired
    private ActionSearchRepository mockActionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActionMockMvc;

    private Action action;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createEntity(EntityManager em) {
        Action action = new Action()
            .datePlanification(DEFAULT_DATE_PLANIFICATION)
            .action(DEFAULT_ACTION)
            .type(DEFAULT_TYPE)
            .delai(DEFAULT_DELAI)
            .avancement(DEFAULT_AVANCEMENT)
            .realisee(DEFAULT_REALISEE)
            .critereResultat(DEFAULT_CRITERE_RESULTAT)
            .efficace(DEFAULT_EFFICACE)
            .ressourcesNecessaires(DEFAULT_RESSOURCES_NECESSAIRES)
            .statut(DEFAULT_STATUT);
        return action;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createUpdatedEntity(EntityManager em) {
        Action action = new Action()
            .datePlanification(UPDATED_DATE_PLANIFICATION)
            .action(UPDATED_ACTION)
            .type(UPDATED_TYPE)
            .delai(UPDATED_DELAI)
            .avancement(UPDATED_AVANCEMENT)
            .realisee(UPDATED_REALISEE)
            .critereResultat(UPDATED_CRITERE_RESULTAT)
            .efficace(UPDATED_EFFICACE)
            .ressourcesNecessaires(UPDATED_RESSOURCES_NECESSAIRES)
            .statut(UPDATED_STATUT);
        return action;
    }

    @BeforeEach
    public void initTest() {
        action = createEntity(em);
    }

    @Test
    @Transactional
    public void createAction() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();
        // Create the Action
        restActionMockMvc.perform(post("/api/actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isCreated());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate + 1);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getDatePlanification()).isEqualTo(DEFAULT_DATE_PLANIFICATION);
        assertThat(testAction.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testAction.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAction.getDelai()).isEqualTo(DEFAULT_DELAI);
        assertThat(testAction.getAvancement()).isEqualTo(DEFAULT_AVANCEMENT);
        assertThat(testAction.isRealisee()).isEqualTo(DEFAULT_REALISEE);
        assertThat(testAction.getCritereResultat()).isEqualTo(DEFAULT_CRITERE_RESULTAT);
        assertThat(testAction.isEfficace()).isEqualTo(DEFAULT_EFFICACE);
        assertThat(testAction.getRessourcesNecessaires()).isEqualTo(DEFAULT_RESSOURCES_NECESSAIRES);
        assertThat(testAction.getStatut()).isEqualTo(DEFAULT_STATUT);

        // Validate the Action in Elasticsearch
        verify(mockActionSearchRepository, times(1)).save(testAction);
    }

    @Test
    @Transactional
    public void createActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action with an existing ID
        action.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionMockMvc.perform(post("/api/actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Action in Elasticsearch
        verify(mockActionSearchRepository, times(0)).save(action);
    }


    @Test
    @Transactional
    public void getAllActions() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList
        restActionMockMvc.perform(get("/api/actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
            .andExpect(jsonPath("$.[*].datePlanification").value(hasItem(DEFAULT_DATE_PLANIFICATION.toString())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI.toString())))
            .andExpect(jsonPath("$.[*].avancement").value(hasItem(DEFAULT_AVANCEMENT)))
            .andExpect(jsonPath("$.[*].realisee").value(hasItem(DEFAULT_REALISEE.booleanValue())))
            .andExpect(jsonPath("$.[*].critereResultat").value(hasItem(DEFAULT_CRITERE_RESULTAT)))
            .andExpect(jsonPath("$.[*].efficace").value(hasItem(DEFAULT_EFFICACE.booleanValue())))
            .andExpect(jsonPath("$.[*].ressourcesNecessaires").value(hasItem(DEFAULT_RESSOURCES_NECESSAIRES)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())));
    }
    
    @Test
    @Transactional
    public void getAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", action.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(action.getId().intValue()))
            .andExpect(jsonPath("$.datePlanification").value(DEFAULT_DATE_PLANIFICATION.toString()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.delai").value(DEFAULT_DELAI.toString()))
            .andExpect(jsonPath("$.avancement").value(DEFAULT_AVANCEMENT))
            .andExpect(jsonPath("$.realisee").value(DEFAULT_REALISEE.booleanValue()))
            .andExpect(jsonPath("$.critereResultat").value(DEFAULT_CRITERE_RESULTAT))
            .andExpect(jsonPath("$.efficace").value(DEFAULT_EFFICACE.booleanValue()))
            .andExpect(jsonPath("$.ressourcesNecessaires").value(DEFAULT_RESSOURCES_NECESSAIRES))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAction() throws Exception {
        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action
        Action updatedAction = actionRepository.findById(action.getId()).get();
        // Disconnect from session so that the updates on updatedAction are not directly saved in db
        em.detach(updatedAction);
        updatedAction
            .datePlanification(UPDATED_DATE_PLANIFICATION)
            .action(UPDATED_ACTION)
            .type(UPDATED_TYPE)
            .delai(UPDATED_DELAI)
            .avancement(UPDATED_AVANCEMENT)
            .realisee(UPDATED_REALISEE)
            .critereResultat(UPDATED_CRITERE_RESULTAT)
            .efficace(UPDATED_EFFICACE)
            .ressourcesNecessaires(UPDATED_RESSOURCES_NECESSAIRES)
            .statut(UPDATED_STATUT);

        restActionMockMvc.perform(put("/api/actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAction)))
            .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getDatePlanification()).isEqualTo(UPDATED_DATE_PLANIFICATION);
        assertThat(testAction.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testAction.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAction.getDelai()).isEqualTo(UPDATED_DELAI);
        assertThat(testAction.getAvancement()).isEqualTo(UPDATED_AVANCEMENT);
        assertThat(testAction.isRealisee()).isEqualTo(UPDATED_REALISEE);
        assertThat(testAction.getCritereResultat()).isEqualTo(UPDATED_CRITERE_RESULTAT);
        assertThat(testAction.isEfficace()).isEqualTo(UPDATED_EFFICACE);
        assertThat(testAction.getRessourcesNecessaires()).isEqualTo(UPDATED_RESSOURCES_NECESSAIRES);
        assertThat(testAction.getStatut()).isEqualTo(UPDATED_STATUT);

        // Validate the Action in Elasticsearch
        verify(mockActionSearchRepository, times(1)).save(testAction);
    }

    @Test
    @Transactional
    public void updateNonExistingAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionMockMvc.perform(put("/api/actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Action in Elasticsearch
        verify(mockActionSearchRepository, times(0)).save(action);
    }

    @Test
    @Transactional
    public void deleteAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeDelete = actionRepository.findAll().size();

        // Delete the action
        restActionMockMvc.perform(delete("/api/actions/{id}", action.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Action in Elasticsearch
        verify(mockActionSearchRepository, times(1)).deleteById(action.getId());
    }

    @Test
    @Transactional
    public void searchAction() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        actionRepository.saveAndFlush(action);
        when(mockActionSearchRepository.search(queryStringQuery("id:" + action.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(action), PageRequest.of(0, 1), 1));

        // Search the action
        restActionMockMvc.perform(get("/api/_search/actions?query=id:" + action.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
            .andExpect(jsonPath("$.[*].datePlanification").value(hasItem(DEFAULT_DATE_PLANIFICATION.toString())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI.toString())))
            .andExpect(jsonPath("$.[*].avancement").value(hasItem(DEFAULT_AVANCEMENT)))
            .andExpect(jsonPath("$.[*].realisee").value(hasItem(DEFAULT_REALISEE.booleanValue())))
            .andExpect(jsonPath("$.[*].critereResultat").value(hasItem(DEFAULT_CRITERE_RESULTAT)))
            .andExpect(jsonPath("$.[*].efficace").value(hasItem(DEFAULT_EFFICACE.booleanValue())))
            .andExpect(jsonPath("$.[*].ressourcesNecessaires").value(hasItem(DEFAULT_RESSOURCES_NECESSAIRES)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())));
    }
}
