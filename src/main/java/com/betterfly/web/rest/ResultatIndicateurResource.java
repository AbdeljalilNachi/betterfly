package com.betterfly.web.rest;

import com.betterfly.domain.ResultatIndicateur;
import com.betterfly.repository.ResultatIndicateurRepository;
import com.betterfly.repository.search.ResultatIndicateurSearchRepository;
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
 * REST controller for managing {@link com.betterfly.domain.ResultatIndicateur}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResultatIndicateurResource {

    private final Logger log = LoggerFactory.getLogger(ResultatIndicateurResource.class);

    private static final String ENTITY_NAME = "resultatIndicateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultatIndicateurRepository resultatIndicateurRepository;

    private final ResultatIndicateurSearchRepository resultatIndicateurSearchRepository;

    public ResultatIndicateurResource(ResultatIndicateurRepository resultatIndicateurRepository, ResultatIndicateurSearchRepository resultatIndicateurSearchRepository) {
        this.resultatIndicateurRepository = resultatIndicateurRepository;
        this.resultatIndicateurSearchRepository = resultatIndicateurSearchRepository;
    }

    /**
     * {@code POST  /resultat-indicateurs} : Create a new resultatIndicateur.
     *
     * @param resultatIndicateur the resultatIndicateur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultatIndicateur, or with status {@code 400 (Bad Request)} if the resultatIndicateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultat-indicateurs")
    public ResponseEntity<ResultatIndicateur> createResultatIndicateur(@RequestBody ResultatIndicateur resultatIndicateur) throws URISyntaxException {
        log.debug("REST request to save ResultatIndicateur : {}", resultatIndicateur);
        if (resultatIndicateur.getId() != null) {
            throw new BadRequestAlertException("A new resultatIndicateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultatIndicateur result = resultatIndicateurRepository.save(resultatIndicateur);
        resultatIndicateurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/resultat-indicateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultat-indicateurs} : Updates an existing resultatIndicateur.
     *
     * @param resultatIndicateur the resultatIndicateur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultatIndicateur,
     * or with status {@code 400 (Bad Request)} if the resultatIndicateur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultatIndicateur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultat-indicateurs")
    public ResponseEntity<ResultatIndicateur> updateResultatIndicateur(@RequestBody ResultatIndicateur resultatIndicateur) throws URISyntaxException {
        log.debug("REST request to update ResultatIndicateur : {}", resultatIndicateur);
        if (resultatIndicateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultatIndicateur result = resultatIndicateurRepository.save(resultatIndicateur);
        resultatIndicateurSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultatIndicateur.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resultat-indicateurs} : get all the resultatIndicateurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultatIndicateurs in body.
     */
    @GetMapping("/resultat-indicateurs")
    public ResponseEntity<List<ResultatIndicateur>> getAllResultatIndicateurs(Pageable pageable) {
        log.debug("REST request to get a page of ResultatIndicateurs");
        Page<ResultatIndicateur> page = resultatIndicateurRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resultat-indicateurs/:id} : get the "id" resultatIndicateur.
     *
     * @param id the id of the resultatIndicateur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultatIndicateur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultat-indicateurs/{id}")
    public ResponseEntity<ResultatIndicateur> getResultatIndicateur(@PathVariable Long id) {
        log.debug("REST request to get ResultatIndicateur : {}", id);
        Optional<ResultatIndicateur> resultatIndicateur = resultatIndicateurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultatIndicateur);
    }

    /**
     * {@code DELETE  /resultat-indicateurs/:id} : delete the "id" resultatIndicateur.
     *
     * @param id the id of the resultatIndicateur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultat-indicateurs/{id}")
    public ResponseEntity<Void> deleteResultatIndicateur(@PathVariable Long id) {
        log.debug("REST request to delete ResultatIndicateur : {}", id);
        resultatIndicateurRepository.deleteById(id);
        resultatIndicateurSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/resultat-indicateurs?query=:query} : search for the resultatIndicateur corresponding
     * to the query.
     *
     * @param query the query of the resultatIndicateur search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/resultat-indicateurs")
    public ResponseEntity<List<ResultatIndicateur>> searchResultatIndicateurs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ResultatIndicateurs for query {}", query);
        Page<ResultatIndicateur> page = resultatIndicateurSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
