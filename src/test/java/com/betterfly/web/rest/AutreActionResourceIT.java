package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.AutreAction;
import com.betterfly.repository.AutreActionRepository;
import com.betterfly.repository.search.AutreActionSearchRepository;

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
 * Integration tests for the {@link AutreActionResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AutreActionResourceIT {

    private static final String DEFAULT_ORIGINE_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE = "BBBBBBBBBB";

    @Autowired
    private AutreActionRepository autreActionRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.AutreActionSearchRepositoryMockConfiguration
     */
    @Autowired
    private AutreActionSearchRepository mockAutreActionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutreActionMockMvc;

    private AutreAction autreAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutreAction createEntity(EntityManager em) {
        AutreAction autreAction = new AutreAction()
            .origineAction(DEFAULT_ORIGINE_ACTION)
            .origine(DEFAULT_ORIGINE);
        return autreAction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutreAction createUpdatedEntity(EntityManager em) {
        AutreAction autreAction = new AutreAction()
            .origineAction(UPDATED_ORIGINE_ACTION)
            .origine(UPDATED_ORIGINE);
        return autreAction;
    }

    @BeforeEach
    public void initTest() {
        autreAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutreAction() throws Exception {
        int databaseSizeBeforeCreate = autreActionRepository.findAll().size();
        // Create the AutreAction
        restAutreActionMockMvc.perform(post("/api/autre-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autreAction)))
            .andExpect(status().isCreated());

        // Validate the AutreAction in the database
        List<AutreAction> autreActionList = autreActionRepository.findAll();
        assertThat(autreActionList).hasSize(databaseSizeBeforeCreate + 1);
        AutreAction testAutreAction = autreActionList.get(autreActionList.size() - 1);
        assertThat(testAutreAction.getOrigineAction()).isEqualTo(DEFAULT_ORIGINE_ACTION);
        assertThat(testAutreAction.getOrigine()).isEqualTo(DEFAULT_ORIGINE);

        // Validate the AutreAction in Elasticsearch
        verify(mockAutreActionSearchRepository, times(1)).save(testAutreAction);
    }

    @Test
    @Transactional
    public void createAutreActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autreActionRepository.findAll().size();

        // Create the AutreAction with an existing ID
        autreAction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutreActionMockMvc.perform(post("/api/autre-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autreAction)))
            .andExpect(status().isBadRequest());

        // Validate the AutreAction in the database
        List<AutreAction> autreActionList = autreActionRepository.findAll();
        assertThat(autreActionList).hasSize(databaseSizeBeforeCreate);

        // Validate the AutreAction in Elasticsearch
        verify(mockAutreActionSearchRepository, times(0)).save(autreAction);
    }


    @Test
    @Transactional
    public void getAllAutreActions() throws Exception {
        // Initialize the database
        autreActionRepository.saveAndFlush(autreAction);

        // Get all the autreActionList
        restAutreActionMockMvc.perform(get("/api/autre-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autreAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].origineAction").value(hasItem(DEFAULT_ORIGINE_ACTION)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
    
    @Test
    @Transactional
    public void getAutreAction() throws Exception {
        // Initialize the database
        autreActionRepository.saveAndFlush(autreAction);

        // Get the autreAction
        restAutreActionMockMvc.perform(get("/api/autre-actions/{id}", autreAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autreAction.getId().intValue()))
            .andExpect(jsonPath("$.origineAction").value(DEFAULT_ORIGINE_ACTION))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE));
    }
    @Test
    @Transactional
    public void getNonExistingAutreAction() throws Exception {
        // Get the autreAction
        restAutreActionMockMvc.perform(get("/api/autre-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutreAction() throws Exception {
        // Initialize the database
        autreActionRepository.saveAndFlush(autreAction);

        int databaseSizeBeforeUpdate = autreActionRepository.findAll().size();

        // Update the autreAction
        AutreAction updatedAutreAction = autreActionRepository.findById(autreAction.getId()).get();
        // Disconnect from session so that the updates on updatedAutreAction are not directly saved in db
        em.detach(updatedAutreAction);
        updatedAutreAction
            .origineAction(UPDATED_ORIGINE_ACTION)
            .origine(UPDATED_ORIGINE);

        restAutreActionMockMvc.perform(put("/api/autre-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAutreAction)))
            .andExpect(status().isOk());

        // Validate the AutreAction in the database
        List<AutreAction> autreActionList = autreActionRepository.findAll();
        assertThat(autreActionList).hasSize(databaseSizeBeforeUpdate);
        AutreAction testAutreAction = autreActionList.get(autreActionList.size() - 1);
        assertThat(testAutreAction.getOrigineAction()).isEqualTo(UPDATED_ORIGINE_ACTION);
        assertThat(testAutreAction.getOrigine()).isEqualTo(UPDATED_ORIGINE);

        // Validate the AutreAction in Elasticsearch
        verify(mockAutreActionSearchRepository, times(1)).save(testAutreAction);
    }

    @Test
    @Transactional
    public void updateNonExistingAutreAction() throws Exception {
        int databaseSizeBeforeUpdate = autreActionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutreActionMockMvc.perform(put("/api/autre-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(autreAction)))
            .andExpect(status().isBadRequest());

        // Validate the AutreAction in the database
        List<AutreAction> autreActionList = autreActionRepository.findAll();
        assertThat(autreActionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AutreAction in Elasticsearch
        verify(mockAutreActionSearchRepository, times(0)).save(autreAction);
    }

    @Test
    @Transactional
    public void deleteAutreAction() throws Exception {
        // Initialize the database
        autreActionRepository.saveAndFlush(autreAction);

        int databaseSizeBeforeDelete = autreActionRepository.findAll().size();

        // Delete the autreAction
        restAutreActionMockMvc.perform(delete("/api/autre-actions/{id}", autreAction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutreAction> autreActionList = autreActionRepository.findAll();
        assertThat(autreActionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AutreAction in Elasticsearch
        verify(mockAutreActionSearchRepository, times(1)).deleteById(autreAction.getId());
    }

    @Test
    @Transactional
    public void searchAutreAction() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        autreActionRepository.saveAndFlush(autreAction);
        when(mockAutreActionSearchRepository.search(queryStringQuery("id:" + autreAction.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(autreAction), PageRequest.of(0, 1), 1));

        // Search the autreAction
        restAutreActionMockMvc.perform(get("/api/_search/autre-actions?query=id:" + autreAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autreAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].origineAction").value(hasItem(DEFAULT_ORIGINE_ACTION)))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE)));
    }
}
