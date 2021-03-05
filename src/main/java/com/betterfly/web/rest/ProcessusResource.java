package com.betterfly.web.rest;

import com.betterfly.domain.Processus;
import com.betterfly.repository.ProcessusRepository;
import com.betterfly.repository.search.ProcessusSearchRepository;
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
 * REST controller for managing {@link com.betterfly.domain.Processus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProcessusResource {

    private final Logger log = LoggerFactory.getLogger(ProcessusResource.class);

    private static final String ENTITY_NAME = "processus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessusRepository processusRepository;

    private final ProcessusSearchRepository processusSearchRepository;

    public ProcessusResource(ProcessusRepository processusRepository, ProcessusSearchRepository processusSearchRepository) {
        this.processusRepository = processusRepository;
        this.processusSearchRepository = processusSearchRepository;
    }

    /**
     * {@code POST  /processuses} : Create a new processus.
     *
     * @param processus the processus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processus, or with status {@code 400 (Bad Request)} if the processus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/processuses")
    public ResponseEntity<Processus> createProcessus(@RequestBody Processus processus) throws URISyntaxException {
        log.debug("REST request to save Processus : {}", processus);
        if (processus.getId() != null) {
            throw new BadRequestAlertException("A new processus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Processus result = processusRepository.save(processus);
        processusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/processuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /processuses} : Updates an existing processus.
     *
     * @param processus the processus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processus,
     * or with status {@code 400 (Bad Request)} if the processus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/processuses")
    public ResponseEntity<Processus> updateProcessus(@RequestBody Processus processus) throws URISyntaxException {
        log.debug("REST request to update Processus : {}", processus);
        if (processus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Processus result = processusRepository.save(processus);
        processusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /processuses} : get all the processuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processuses in body.
     */
    @GetMapping("/processuses")
    public ResponseEntity<List<Processus>> getAllProcessuses(Pageable pageable) {
        log.debug("REST request to get a page of Processuses");
        Page<Processus> page = processusRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /processuses/:id} : get the "id" processus.
     *
     * @param id the id of the processus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processuses/{id}")
    public ResponseEntity<Processus> getProcessus(@PathVariable Long id) {
        log.debug("REST request to get Processus : {}", id);
        Optional<Processus> processus = processusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(processus);
    }

    /**
     * {@code DELETE  /processuses/:id} : delete the "id" processus.
     *
     * @param id the id of the processus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/processuses/{id}")
    public ResponseEntity<Void> deleteProcessus(@PathVariable Long id) {
        log.debug("REST request to delete Processus : {}", id);
        processusRepository.deleteById(id);
        processusSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/processuses?query=:query} : search for the processus corresponding
     * to the query.
     *
     * @param query the query of the processus search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/processuses")
    public ResponseEntity<List<Processus>> searchProcessuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Processuses for query {}", query);
        Page<Processus> page = processusSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
