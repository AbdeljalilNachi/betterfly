package com.betterfly.web.rest;

import com.betterfly.domain.AnalyseSST;
import com.betterfly.repository.AnalyseSSTRepository;
import com.betterfly.repository.search.AnalyseSSTSearchRepository;
import com.betterfly.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.betterfly.domain.AnalyseSST}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnalyseSSTResource {

    private final Logger log = LoggerFactory.getLogger(AnalyseSSTResource.class);

    private static final String ENTITY_NAME = "analyseSST";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalyseSSTRepository analyseSSTRepository;

    private final AnalyseSSTSearchRepository analyseSSTSearchRepository;

    public AnalyseSSTResource(AnalyseSSTRepository analyseSSTRepository, AnalyseSSTSearchRepository analyseSSTSearchRepository) {
        this.analyseSSTRepository = analyseSSTRepository;
        this.analyseSSTSearchRepository = analyseSSTSearchRepository;
    }

    /**
     * {@code POST  /analyse-ssts} : Create a new analyseSST.
     *
     * @param analyseSST the analyseSST to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analyseSST, or with status {@code 400 (Bad Request)} if the analyseSST has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analyse-ssts")
    public ResponseEntity<AnalyseSST> createAnalyseSST(@RequestBody AnalyseSST analyseSST) throws URISyntaxException {
        log.debug("REST request to save AnalyseSST : {}", analyseSST);
        if (analyseSST.getId() != null) {
            throw new BadRequestAlertException("A new analyseSST cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        int f = analyseSST.getFrequence().ordinal()+1 ; 
        int d = analyseSST.getDureeExposition().ordinal()+1 ; 
        int m = analyseSST.getCoefficientMaitrise().ordinal()+1 ; 
        int g = analyseSST.getGravite().ordinal()+1 ; 
        
        analyseSST.setCriticite(f*d*m*g);
        
        
        AnalyseSST result = analyseSSTRepository.save(analyseSST);
        analyseSSTSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/analyse-ssts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analyse-ssts} : Updates an existing analyseSST.
     *
     * @param analyseSST the analyseSST to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analyseSST,
     * or with status {@code 400 (Bad Request)} if the analyseSST is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analyseSST couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analyse-ssts")
    public ResponseEntity<AnalyseSST> updateAnalyseSST(@RequestBody AnalyseSST analyseSST) throws URISyntaxException {
        log.debug("REST request to update AnalyseSST : {}", analyseSST);
        if (analyseSST.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        int f = analyseSST.getFrequence().ordinal()+1 ; 
        int d = analyseSST.getDureeExposition().ordinal()+1 ; 
        int m = analyseSST.getCoefficientMaitrise().ordinal()+1 ; 
        int g = analyseSST.getGravite().ordinal()+1 ; 
        
        analyseSST.setCriticite(f*d*m*g);
        
        AnalyseSST result = analyseSSTRepository.save(analyseSST);
        analyseSSTSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analyseSST.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /analyse-ssts} : get all the analyseSSTS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analyseSSTS in body.
     */
    @GetMapping("/analyse-ssts")
    public ResponseEntity<List<AnalyseSST>> getAllAnalyseSSTS(Pageable pageable) {
        log.debug("REST request to get a page of AnalyseSSTS");
        Page<AnalyseSST> page = analyseSSTRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /analyse-ssts/:id} : get the "id" analyseSST.
     *
     * @param id the id of the analyseSST to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analyseSST, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analyse-ssts/{id}")
    public ResponseEntity<AnalyseSST> getAnalyseSST(@PathVariable Long id) {
        log.debug("REST request to get AnalyseSST : {}", id);
        Optional<AnalyseSST> analyseSST = analyseSSTRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(analyseSST);
    }

    /**
     * {@code DELETE  /analyse-ssts/:id} : delete the "id" analyseSST.
     *
     * @param id the id of the analyseSST to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analyse-ssts/{id}")
    public ResponseEntity<Void> deleteAnalyseSST(@PathVariable Long id) {
        log.debug("REST request to delete AnalyseSST : {}", id);
        analyseSSTRepository.deleteById(id);
        analyseSSTSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/analyse-ssts?query=:query} : search for the analyseSST corresponding
     * to the query.
     *
     * @param query the query of the analyseSST search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/analyse-ssts")
    public ResponseEntity<List<AnalyseSST>> searchAnalyseSSTS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AnalyseSSTS for query {}", query);
        Page<AnalyseSST> page = analyseSSTSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
