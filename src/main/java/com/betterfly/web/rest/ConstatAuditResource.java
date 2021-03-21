package com.betterfly.web.rest;

import com.betterfly.domain.ConstatAudit;
import com.betterfly.repository.ConstatAuditRepository;
import com.betterfly.repository.search.ConstatAuditSearchRepository;
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
 * REST controller for managing {@link com.betterfly.domain.ConstatAudit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConstatAuditResource {

    private final Logger log = LoggerFactory.getLogger(ConstatAuditResource.class);

    private static final String ENTITY_NAME = "constatAudit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConstatAuditRepository constatAuditRepository;

    private final ConstatAuditSearchRepository constatAuditSearchRepository;

    public ConstatAuditResource(ConstatAuditRepository constatAuditRepository, ConstatAuditSearchRepository constatAuditSearchRepository) {
        this.constatAuditRepository = constatAuditRepository;
        this.constatAuditSearchRepository = constatAuditSearchRepository;
    }

    /**
     * {@code POST  /constat-audits} : Create a new constatAudit.
     *
     * @param constatAudit the constatAudit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new constatAudit, or with status {@code 400 (Bad Request)} if the constatAudit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/constat-audits")
    public ResponseEntity<ConstatAudit> createConstatAudit(@RequestBody ConstatAudit constatAudit) throws URISyntaxException {
        log.debug("REST request to save ConstatAudit : {}", constatAudit);
        if (constatAudit.getId() != null) {
            throw new BadRequestAlertException("A new constatAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConstatAudit result = constatAuditRepository.save(constatAudit);
        constatAuditSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/constat-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /constat-audits} : Updates an existing constatAudit.
     *
     * @param constatAudit the constatAudit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated constatAudit,
     * or with status {@code 400 (Bad Request)} if the constatAudit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the constatAudit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/constat-audits")
    public ResponseEntity<ConstatAudit> updateConstatAudit(@RequestBody ConstatAudit constatAudit) throws URISyntaxException {
        log.debug("REST request to update ConstatAudit : {}", constatAudit);
        if (constatAudit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConstatAudit result = constatAuditRepository.save(constatAudit);
        constatAuditSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, constatAudit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /constat-audits} : get all the constatAudits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of constatAudits in body.
     */
    @GetMapping("/constat-audits")
    public ResponseEntity<List<ConstatAudit>> getAllConstatAudits(Pageable pageable) {
        log.debug("REST request to get a page of ConstatAudits");
        Page<ConstatAudit> page = constatAuditRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /constat-audits/:id} : get the "id" constatAudit.
     *
     * @param id the id of the constatAudit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the constatAudit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/constat-audits/{id}")
    public ResponseEntity<ConstatAudit> getConstatAudit(@PathVariable Long id) {
        log.debug("REST request to get ConstatAudit : {}", id);
        Optional<ConstatAudit> constatAudit = constatAuditRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(constatAudit);
    }

    /**
     * {@code DELETE  /constat-audits/:id} : delete the "id" constatAudit.
     *
     * @param id the id of the constatAudit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/constat-audits/{id}")
    public ResponseEntity<Void> deleteConstatAudit(@PathVariable Long id) {
        log.debug("REST request to delete ConstatAudit : {}", id);
        constatAuditRepository.deleteById(id);
        constatAuditSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/constat-audits?query=:query} : search for the constatAudit corresponding
     * to the query.
     *
     * @param query the query of the constatAudit search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/constat-audits")
    public ResponseEntity<List<ConstatAudit>> searchConstatAudits(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConstatAudits for query {}", query);
        Page<ConstatAudit> page = constatAuditSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
