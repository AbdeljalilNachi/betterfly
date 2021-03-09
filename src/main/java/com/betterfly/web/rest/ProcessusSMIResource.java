package com.betterfly.web.rest;

import com.betterfly.domain.ProcessusSMI;
import com.betterfly.domain.User;
import com.betterfly.repository.ProcessusSMIRepository;
import com.betterfly.repository.search.ProcessusSMISearchRepository;
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
 * REST controller for managing {@link com.betterfly.domain.ProcessusSMI}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProcessusSMIResource {

    private final Logger log = LoggerFactory.getLogger(ProcessusSMIResource.class);

    private static final String ENTITY_NAME = "processusSMI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessusSMIRepository processusSMIRepository;

    private final ProcessusSMISearchRepository processusSMISearchRepository;

    public ProcessusSMIResource(ProcessusSMIRepository processusSMIRepository, ProcessusSMISearchRepository processusSMISearchRepository) {
        this.processusSMIRepository = processusSMIRepository;
        this.processusSMISearchRepository = processusSMISearchRepository;
    }

    /**
     * {@code POST  /processus-smis} : Create a new processusSMI.
     *
     * @param processusSMI the processusSMI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processusSMI, or with status {@code 400 (Bad Request)} if the processusSMI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/processus-smis")
    public ResponseEntity<ProcessusSMI> createProcessusSMI(@RequestBody ProcessusSMI processusSMI) throws URISyntaxException {
        log.debug("REST request to save ProcessusSMI : {}", processusSMI);
        if (processusSMI.getId() != null) {
            throw new BadRequestAlertException("A new processusSMI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessusSMI result = processusSMIRepository.save(processusSMI);
        processusSMISearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/processus-smis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /processus-smis} : Updates an existing processusSMI.
     *
     * @param processusSMI the processusSMI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processusSMI,
     * or with status {@code 400 (Bad Request)} if the processusSMI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processusSMI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/processus-smis")
    public ResponseEntity<ProcessusSMI> updateProcessusSMI(@RequestBody ProcessusSMI processusSMI) throws URISyntaxException {
        log.debug("REST request to update ProcessusSMI : {}", processusSMI);
        if (processusSMI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessusSMI result = processusSMIRepository.save(processusSMI);
        processusSMISearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processusSMI.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /processus-smis} : get all the processusSMIS.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processusSMIS in body.
     */
    @GetMapping("/processus-smis")
    public ResponseEntity<List<ProcessusSMI>> getAllProcessusSMIS(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ProcessusSMIS");
        Page<ProcessusSMI> page;
        if (eagerload) {
            page = processusSMIRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = processusSMIRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/processus-names")
    public ResponseEntity<List<String>> getAllProcessusNames() {
       final List<String> procNames = processusSMIRepository.findAll().stream().map(ProcessusSMI::getProcessus).collect(Collectors.toList());
        return new ResponseEntity<>(procNames,  HttpStatus.OK);
    }
    
    


    /**
     * {@code GET  /processus-smis/:id} : get the "id" processusSMI.
     *
     * @param id the id of the processusSMI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processusSMI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processus-smis/{id}")
    public ResponseEntity<ProcessusSMI> getProcessusSMI(@PathVariable Long id) {
        log.debug("REST request to get ProcessusSMI : {}", id);
        Optional<ProcessusSMI> processusSMI = processusSMIRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(processusSMI);
    }

    /**
     * {@code DELETE  /processus-smis/:id} : delete the "id" processusSMI.
     *
     * @param id the id of the processusSMI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/processus-smis/{id}")
    public ResponseEntity<Void> deleteProcessusSMI(@PathVariable Long id) {
        log.debug("REST request to delete ProcessusSMI : {}", id);
        processusSMIRepository.deleteById(id);
        processusSMISearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/processus-smis?query=:query} : search for the processusSMI corresponding
     * to the query.
     *
     * @param query the query of the processusSMI search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/processus-smis")
    public ResponseEntity<List<ProcessusSMI>> searchProcessusSMIS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProcessusSMIS for query {}", query);
        Page<ProcessusSMI> page = processusSMISearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
