package com.betterfly.web.rest;

import com.betterfly.BetterFlyApp;
import com.betterfly.domain.Document;
import com.betterfly.repository.DocumentRepository;
import com.betterfly.repository.search.DocumentSearchRepository;

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
import org.springframework.util.Base64Utils;
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

import com.betterfly.domain.enumeration.TypeDocument;
/**
 * Integration tests for the {@link DocumentResource} REST controller.
 */
@SpringBootTest(classes = BetterFlyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DocumentResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INTITULE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final TypeDocument DEFAULT_TYPE = TypeDocument.PROECDURE;
    private static final TypeDocument UPDATED_TYPE = TypeDocument.INSTRUCTION;

    private static final byte[] DEFAULT_PIECE_JOINTE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PIECE_JOINTE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PIECE_JOINTE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PIECE_JOINTE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_EN_APPLICATION = false;
    private static final Boolean UPDATED_EN_APPLICATION = true;

    private static final Boolean DEFAULT_APPOUVE = false;
    private static final Boolean UPDATED_APPOUVE = true;

    @Autowired
    private DocumentRepository documentRepository;

    /**
     * This repository is mocked in the com.betterfly.repository.search test package.
     *
     * @see com.betterfly.repository.search.DocumentSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentSearchRepository mockDocumentSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentMockMvc;

    private Document document;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .date(DEFAULT_DATE)
            .intitule(DEFAULT_INTITULE)
            .code(DEFAULT_CODE)
            .version(DEFAULT_VERSION)
            .type(DEFAULT_TYPE)
            .pieceJointe(DEFAULT_PIECE_JOINTE)
            .pieceJointeContentType(DEFAULT_PIECE_JOINTE_CONTENT_TYPE)
            .enApplication(DEFAULT_EN_APPLICATION)
            .appouve(DEFAULT_APPOUVE);
        return document;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createUpdatedEntity(EntityManager em) {
        Document document = new Document()
            .date(UPDATED_DATE)
            .intitule(UPDATED_INTITULE)
            .code(UPDATED_CODE)
            .version(UPDATED_VERSION)
            .type(UPDATED_TYPE)
            .pieceJointe(UPDATED_PIECE_JOINTE)
            .pieceJointeContentType(UPDATED_PIECE_JOINTE_CONTENT_TYPE)
            .enApplication(UPDATED_EN_APPLICATION)
            .appouve(UPDATED_APPOUVE);
        return document;
    }

    @BeforeEach
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocument() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();
        // Create the Document
        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate + 1);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDocument.getIntitule()).isEqualTo(DEFAULT_INTITULE);
        assertThat(testDocument.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDocument.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testDocument.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDocument.getPieceJointe()).isEqualTo(DEFAULT_PIECE_JOINTE);
        assertThat(testDocument.getPieceJointeContentType()).isEqualTo(DEFAULT_PIECE_JOINTE_CONTENT_TYPE);
        assertThat(testDocument.isEnApplication()).isEqualTo(DEFAULT_EN_APPLICATION);
        assertThat(testDocument.isAppouve()).isEqualTo(DEFAULT_APPOUVE);

        // Validate the Document in Elasticsearch
        verify(mockDocumentSearchRepository, times(1)).save(testDocument);
    }

    @Test
    @Transactional
    public void createDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // Create the Document with an existing ID
        document.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Document in Elasticsearch
        verify(mockDocumentSearchRepository, times(0)).save(document);
    }


    @Test
    @Transactional
    public void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc.perform(get("/api/documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pieceJointeContentType").value(hasItem(DEFAULT_PIECE_JOINTE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pieceJointe").value(hasItem(Base64Utils.encodeToString(DEFAULT_PIECE_JOINTE))))
            .andExpect(jsonPath("$.[*].enApplication").value(hasItem(DEFAULT_EN_APPLICATION.booleanValue())))
            .andExpect(jsonPath("$.[*].appouve").value(hasItem(DEFAULT_APPOUVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.pieceJointeContentType").value(DEFAULT_PIECE_JOINTE_CONTENT_TYPE))
            .andExpect(jsonPath("$.pieceJointe").value(Base64Utils.encodeToString(DEFAULT_PIECE_JOINTE)))
            .andExpect(jsonPath("$.enApplication").value(DEFAULT_EN_APPLICATION.booleanValue()))
            .andExpect(jsonPath("$.appouve").value(DEFAULT_APPOUVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document
        Document updatedDocument = documentRepository.findById(document.getId()).get();
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .date(UPDATED_DATE)
            .intitule(UPDATED_INTITULE)
            .code(UPDATED_CODE)
            .version(UPDATED_VERSION)
            .type(UPDATED_TYPE)
            .pieceJointe(UPDATED_PIECE_JOINTE)
            .pieceJointeContentType(UPDATED_PIECE_JOINTE_CONTENT_TYPE)
            .enApplication(UPDATED_EN_APPLICATION)
            .appouve(UPDATED_APPOUVE);

        restDocumentMockMvc.perform(put("/api/documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocument)))
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDocument.getIntitule()).isEqualTo(UPDATED_INTITULE);
        assertThat(testDocument.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDocument.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testDocument.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDocument.getPieceJointe()).isEqualTo(UPDATED_PIECE_JOINTE);
        assertThat(testDocument.getPieceJointeContentType()).isEqualTo(UPDATED_PIECE_JOINTE_CONTENT_TYPE);
        assertThat(testDocument.isEnApplication()).isEqualTo(UPDATED_EN_APPLICATION);
        assertThat(testDocument.isAppouve()).isEqualTo(UPDATED_APPOUVE);

        // Validate the Document in Elasticsearch
        verify(mockDocumentSearchRepository, times(1)).save(testDocument);
    }

    @Test
    @Transactional
    public void updateNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc.perform(put("/api/documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Document in Elasticsearch
        verify(mockDocumentSearchRepository, times(0)).save(document);
    }

    @Test
    @Transactional
    public void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Delete the document
        restDocumentMockMvc.perform(delete("/api/documents/{id}", document.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Document in Elasticsearch
        verify(mockDocumentSearchRepository, times(1)).deleteById(document.getId());
    }

    @Test
    @Transactional
    public void searchDocument() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        documentRepository.saveAndFlush(document);
        when(mockDocumentSearchRepository.search(queryStringQuery("id:" + document.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(document), PageRequest.of(0, 1), 1));

        // Search the document
        restDocumentMockMvc.perform(get("/api/_search/documents?query=id:" + document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pieceJointeContentType").value(hasItem(DEFAULT_PIECE_JOINTE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pieceJointe").value(hasItem(Base64Utils.encodeToString(DEFAULT_PIECE_JOINTE))))
            .andExpect(jsonPath("$.[*].enApplication").value(hasItem(DEFAULT_EN_APPLICATION.booleanValue())))
            .andExpect(jsonPath("$.[*].appouve").value(hasItem(DEFAULT_APPOUVE.booleanValue())));
    }
}
