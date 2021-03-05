package com.betterfly.web.rest;

import com.betterfly.domain.BesoinPI;
import com.betterfly.repository.BesoinPIRepository;
import com.betterfly.repository.search.BesoinPISearchRepository;
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
 * REST controller for managing {@link com.betterfly.domain.BesoinPI}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BesoinPIResource {

    private final Logger log = LoggerFactory.getLogger(BesoinPIResource.class);

    private static final String ENTITY_NAME = "besoinPI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BesoinPIRepository besoinPIRepository;

    private final BesoinPISearchRepository besoinPISearchRepository;

    public BesoinPIResource(BesoinPIRepository besoinPIRepository, BesoinPISearchRepository besoinPISearchRepository) {
        this.besoinPIRepository = besoinPIRepository;
        this.besoinPISearchRepository = besoinPISearchRepository;
    }

    /**
     * {@code POST  /besoin-pis} : Create a new besoinPI.
     *
     * @param besoinPI the besoinPI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new besoinPI, or with status {@code 400 (Bad Request)} if the besoinPI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/besoin-pis")
    public ResponseEntity<BesoinPI> createBesoinPI(@RequestBody BesoinPI besoinPI) throws URISyntaxException {
        log.debug("REST request to save BesoinPI : {}", besoinPI);
        if (besoinPI.getId() != null) {
            throw new BadRequestAlertException("A new besoinPI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BesoinPI result = besoinPIRepository.save(besoinPI);
        besoinPISearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/besoin-pis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /besoin-pis} : Updates an existing besoinPI.
     *
     * @param besoinPI the besoinPI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated besoinPI,
     * or with status {@code 400 (Bad Request)} if the besoinPI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the besoinPI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/besoin-pis")
    public ResponseEntity<BesoinPI> updateBesoinPI(@RequestBody BesoinPI besoinPI) throws URISyntaxException {
        log.debug("REST request to update BesoinPI : {}", besoinPI);
        if (besoinPI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BesoinPI result = besoinPIRepository.save(besoinPI);
        besoinPISearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, besoinPI.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /besoin-pis} : get all the besoinPIS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of besoinPIS in body.
     */
    @GetMapping("/besoin-pis")
    public ResponseEntity<List<BesoinPI>> getAllBesoinPIS(Pageable pageable) {
        log.debug("REST request to get a page of BesoinPIS");
        Page<BesoinPI> page = besoinPIRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /besoin-pis/:id} : get the "id" besoinPI.
     *
     * @param id the id of the besoinPI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the besoinPI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/besoin-pis/{id}")
    public ResponseEntity<BesoinPI> getBesoinPI(@PathVariable Long id) {
        log.debug("REST request to get BesoinPI : {}", id);
        Optional<BesoinPI> besoinPI = besoinPIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(besoinPI);
    }

    /**
     * {@code DELETE  /besoin-pis/:id} : delete the "id" besoinPI.
     *
     * @param id the id of the besoinPI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/besoin-pis/{id}")
    public ResponseEntity<Void> deleteBesoinPI(@PathVariable Long id) {
        log.debug("REST request to delete BesoinPI : {}", id);
        besoinPIRepository.deleteById(id);
        besoinPISearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/besoin-pis?query=:query} : search for the besoinPI corresponding
     * to the query.
     *
     * @param query the query of the besoinPI search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/besoin-pis")
    public ResponseEntity<List<BesoinPI>> searchBesoinPIS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BesoinPIS for query {}", query);
        Page<BesoinPI> page = besoinPISearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
