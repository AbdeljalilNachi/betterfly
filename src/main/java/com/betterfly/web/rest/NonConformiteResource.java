package com.betterfly.web.rest;

import com.betterfly.domain.NonConformite;
import com.betterfly.repository.NonConformiteRepository;
import com.betterfly.repository.search.NonConformiteSearchRepository;
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
 * REST controller for managing {@link com.betterfly.domain.NonConformite}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NonConformiteResource {

    private final Logger log = LoggerFactory.getLogger(NonConformiteResource.class);

    private static final String ENTITY_NAME = "nonConformite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NonConformiteRepository nonConformiteRepository;

    private final NonConformiteSearchRepository nonConformiteSearchRepository;

    public NonConformiteResource(NonConformiteRepository nonConformiteRepository, NonConformiteSearchRepository nonConformiteSearchRepository) {
        this.nonConformiteRepository = nonConformiteRepository;
        this.nonConformiteSearchRepository = nonConformiteSearchRepository;
    }

    /**
     * {@code POST  /non-conformites} : Create a new nonConformite.
     *
     * @param nonConformite the nonConformite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nonConformite, or with status {@code 400 (Bad Request)} if the nonConformite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/non-conformites")
    public ResponseEntity<NonConformite> createNonConformite(@RequestBody NonConformite nonConformite) throws URISyntaxException {
        log.debug("REST request to save NonConformite : {}", nonConformite);
        if (nonConformite.getId() != null) {
            throw new BadRequestAlertException("A new nonConformite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NonConformite result = nonConformiteRepository.save(nonConformite);
        nonConformiteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/non-conformites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /non-conformites} : Updates an existing nonConformite.
     *
     * @param nonConformite the nonConformite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nonConformite,
     * or with status {@code 400 (Bad Request)} if the nonConformite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nonConformite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/non-conformites")
    public ResponseEntity<NonConformite> updateNonConformite(@RequestBody NonConformite nonConformite) throws URISyntaxException {
        log.debug("REST request to update NonConformite : {}", nonConformite);
        if (nonConformite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NonConformite result = nonConformiteRepository.save(nonConformite);
        nonConformiteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nonConformite.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /non-conformites} : get all the nonConformites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nonConformites in body.
     */
    @GetMapping("/non-conformites")
    public ResponseEntity<List<NonConformite>> getAllNonConformites(Pageable pageable) {
        log.debug("REST request to get a page of NonConformites");
        Page<NonConformite> page = nonConformiteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /non-conformites/:id} : get the "id" nonConformite.
     *
     * @param id the id of the nonConformite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nonConformite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/non-conformites/{id}")
    public ResponseEntity<NonConformite> getNonConformite(@PathVariable Long id) {
        log.debug("REST request to get NonConformite : {}", id);
        Optional<NonConformite> nonConformite = nonConformiteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nonConformite);
    }

    /**
     * {@code DELETE  /non-conformites/:id} : delete the "id" nonConformite.
     *
     * @param id the id of the nonConformite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/non-conformites/{id}")
    public ResponseEntity<Void> deleteNonConformite(@PathVariable Long id) {
        log.debug("REST request to delete NonConformite : {}", id);
        nonConformiteRepository.deleteById(id);
        nonConformiteSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/non-conformites?query=:query} : search for the nonConformite corresponding
     * to the query.
     *
     * @param query the query of the nonConformite search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/non-conformites")
    public ResponseEntity<List<NonConformite>> searchNonConformites(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of NonConformites for query {}", query);
        Page<NonConformite> page = nonConformiteSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
