package com.betterfly.web.rest;

import com.betterfly.domain.ObligationConformite;
import com.betterfly.repository.ObligationConformiteRepository;
import com.betterfly.repository.search.ObligationConformiteSearchRepository;
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
 * REST controller for managing {@link com.betterfly.domain.ObligationConformite}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ObligationConformiteResource {

    private final Logger log = LoggerFactory.getLogger(ObligationConformiteResource.class);

    private static final String ENTITY_NAME = "obligationConformite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObligationConformiteRepository obligationConformiteRepository;

    private final ObligationConformiteSearchRepository obligationConformiteSearchRepository;

    public ObligationConformiteResource(ObligationConformiteRepository obligationConformiteRepository, ObligationConformiteSearchRepository obligationConformiteSearchRepository) {
        this.obligationConformiteRepository = obligationConformiteRepository;
        this.obligationConformiteSearchRepository = obligationConformiteSearchRepository;
    }

    /**
     * {@code POST  /obligation-conformites} : Create a new obligationConformite.
     *
     * @param obligationConformite the obligationConformite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new obligationConformite, or with status {@code 400 (Bad Request)} if the obligationConformite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/obligation-conformites")
    public ResponseEntity<ObligationConformite> createObligationConformite(@RequestBody ObligationConformite obligationConformite) throws URISyntaxException {
        log.debug("REST request to save ObligationConformite : {}", obligationConformite);
        if (obligationConformite.getId() != null) {
            throw new BadRequestAlertException("A new obligationConformite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObligationConformite result = obligationConformiteRepository.save(obligationConformite);
        obligationConformiteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/obligation-conformites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /obligation-conformites} : Updates an existing obligationConformite.
     *
     * @param obligationConformite the obligationConformite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated obligationConformite,
     * or with status {@code 400 (Bad Request)} if the obligationConformite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the obligationConformite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/obligation-conformites")
    public ResponseEntity<ObligationConformite> updateObligationConformite(@RequestBody ObligationConformite obligationConformite) throws URISyntaxException {
        log.debug("REST request to update ObligationConformite : {}", obligationConformite);
        if (obligationConformite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObligationConformite result = obligationConformiteRepository.save(obligationConformite);
        obligationConformiteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, obligationConformite.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /obligation-conformites} : get all the obligationConformites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of obligationConformites in body.
     */
    @GetMapping("/obligation-conformites")
    public ResponseEntity<List<ObligationConformite>> getAllObligationConformites(Pageable pageable) {
        log.debug("REST request to get a page of ObligationConformites");
        Page<ObligationConformite> page = obligationConformiteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /obligation-conformites/:id} : get the "id" obligationConformite.
     *
     * @param id the id of the obligationConformite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the obligationConformite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/obligation-conformites/{id}")
    public ResponseEntity<ObligationConformite> getObligationConformite(@PathVariable Long id) {
        log.debug("REST request to get ObligationConformite : {}", id);
        Optional<ObligationConformite> obligationConformite = obligationConformiteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(obligationConformite);
    }

    /**
     * {@code DELETE  /obligation-conformites/:id} : delete the "id" obligationConformite.
     *
     * @param id the id of the obligationConformite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/obligation-conformites/{id}")
    public ResponseEntity<Void> deleteObligationConformite(@PathVariable Long id) {
        log.debug("REST request to delete ObligationConformite : {}", id);
        obligationConformiteRepository.deleteById(id);
        obligationConformiteSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/obligation-conformites?query=:query} : search for the obligationConformite corresponding
     * to the query.
     *
     * @param query the query of the obligationConformite search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/obligation-conformites")
    public ResponseEntity<List<ObligationConformite>> searchObligationConformites(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ObligationConformites for query {}", query);
        Page<ObligationConformite> page = obligationConformiteSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
