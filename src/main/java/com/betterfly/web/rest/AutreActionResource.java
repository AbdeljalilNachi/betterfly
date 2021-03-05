package com.betterfly.web.rest;

import com.betterfly.domain.AutreAction;
import com.betterfly.repository.AutreActionRepository;
import com.betterfly.repository.search.AutreActionSearchRepository;
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
 * REST controller for managing {@link com.betterfly.domain.AutreAction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AutreActionResource {

    private final Logger log = LoggerFactory.getLogger(AutreActionResource.class);

    private static final String ENTITY_NAME = "autreAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutreActionRepository autreActionRepository;

    private final AutreActionSearchRepository autreActionSearchRepository;

    public AutreActionResource(AutreActionRepository autreActionRepository, AutreActionSearchRepository autreActionSearchRepository) {
        this.autreActionRepository = autreActionRepository;
        this.autreActionSearchRepository = autreActionSearchRepository;
    }

    /**
     * {@code POST  /autre-actions} : Create a new autreAction.
     *
     * @param autreAction the autreAction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autreAction, or with status {@code 400 (Bad Request)} if the autreAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/autre-actions")
    public ResponseEntity<AutreAction> createAutreAction(@RequestBody AutreAction autreAction) throws URISyntaxException {
        log.debug("REST request to save AutreAction : {}", autreAction);
        if (autreAction.getId() != null) {
            throw new BadRequestAlertException("A new autreAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutreAction result = autreActionRepository.save(autreAction);
        autreActionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/autre-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /autre-actions} : Updates an existing autreAction.
     *
     * @param autreAction the autreAction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autreAction,
     * or with status {@code 400 (Bad Request)} if the autreAction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autreAction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/autre-actions")
    public ResponseEntity<AutreAction> updateAutreAction(@RequestBody AutreAction autreAction) throws URISyntaxException {
        log.debug("REST request to update AutreAction : {}", autreAction);
        if (autreAction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutreAction result = autreActionRepository.save(autreAction);
        autreActionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autreAction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /autre-actions} : get all the autreActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autreActions in body.
     */
    @GetMapping("/autre-actions")
    public ResponseEntity<List<AutreAction>> getAllAutreActions(Pageable pageable) {
        log.debug("REST request to get a page of AutreActions");
        Page<AutreAction> page = autreActionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autre-actions/:id} : get the "id" autreAction.
     *
     * @param id the id of the autreAction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autreAction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/autre-actions/{id}")
    public ResponseEntity<AutreAction> getAutreAction(@PathVariable Long id) {
        log.debug("REST request to get AutreAction : {}", id);
        Optional<AutreAction> autreAction = autreActionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(autreAction);
    }

    /**
     * {@code DELETE  /autre-actions/:id} : delete the "id" autreAction.
     *
     * @param id the id of the autreAction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/autre-actions/{id}")
    public ResponseEntity<Void> deleteAutreAction(@PathVariable Long id) {
        log.debug("REST request to delete AutreAction : {}", id);
        autreActionRepository.deleteById(id);
        autreActionSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/autre-actions?query=:query} : search for the autreAction corresponding
     * to the query.
     *
     * @param query the query of the autreAction search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/autre-actions")
    public ResponseEntity<List<AutreAction>> searchAutreActions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AutreActions for query {}", query);
        Page<AutreAction> page = autreActionSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
