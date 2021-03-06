package com.betterfly.web.rest;

import com.betterfly.domain.Action;
import com.betterfly.domain.ProcessusSMI;
import com.betterfly.domain.enumeration.Statut ; 
import com.betterfly.repository.ActionRepository;
import com.betterfly.repository.search.ActionSearchRepository;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.betterfly.domain.Action}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ActionResource {

    private final Logger log = LoggerFactory.getLogger(ActionResource.class);

    private static final String ENTITY_NAME = "action";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionRepository actionRepository;

    private final ActionSearchRepository actionSearchRepository;

    public ActionResource(ActionRepository actionRepository, ActionSearchRepository actionSearchRepository) {
        this.actionRepository = actionRepository;
        this.actionSearchRepository = actionSearchRepository;
    }

    
    private void setStatut(Action action )  
    {
    	 if ( action.isRealisee()  )
         {
         	switch (action.getEfficace())
         	{
         	case EFFICACE  :   		action.setStatut(Statut.REALISEE_EFFICACE)  ;  break  ;
         	case NON_EFFICACE  : 	action.setStatut(Statut.REALISEE_NON_EFFICACE)  ;  break  ;
         	case A_EVALUER  :  		action.setStatut(Statut.REALISEE_A_EVALUER)  ; break  ;
         	default  :  			action.setStatut(Statut.REALISEE_A_EVALUER)  ; break  ;
         	
         	}
         }
         else  if (action.getDelai().isAfter(LocalDate.now()) )
         {
         	action.setStatut(Statut.RETARD)  ;
         	
         }
         else  if (action.getAvancement() == "" )
         {
         	action.setStatut(Statut.PLANIFIEE)  ;
         	
         }
         
         else 
         {
         	action.setStatut(Statut.EN_COURS)  ;
         	
         }
    	
    }
    
    
    /**
     * {@code POST  /actions} : Create a new action.
     *
     * @param action the action to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new action, or with status {@code 400 (Bad Request)} if the action has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actions")
    public ResponseEntity<Action> createAction(@RequestBody Action action) throws URISyntaxException {
        log.debug("REST request to save Action : {}", action);
        if (action.getId() != null) {
            throw new BadRequestAlertException("A new action cannot already have an ID", ENTITY_NAME, "idexists");
        }
       
        setStatut( action )  ;
        
        
        Action result = actionRepository.save(action);
        actionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/actions-names")
    public ResponseEntity<List<String>> getAllProcessusNames() {
       final List<String> actionsNames = actionRepository.findAll().stream().map(Action::getAction).collect(Collectors.toList());
        return new ResponseEntity<>(actionsNames,  HttpStatus.OK);
    }
    

    /**
     * {@code PUT  /actions} : Updates an existing action.
     *
     * @param action the action to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated action,
     * or with status {@code 400 (Bad Request)} if the action is not valid,
     * or with status {@code 500 (Internal Server Error)} if the action couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/actions")
    public ResponseEntity<Action> updateAction(@RequestBody Action action) throws URISyntaxException {
        log.debug("REST request to update Action : {}", action);
        if (action.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        setStatut( action )  ;
        
        
        Action result = actionRepository.save(action);
        actionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, action.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /actions} : get all the actions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actions in body.
     */
    @GetMapping("/actions")
    public ResponseEntity<List<Action>> getAllActions(Pageable pageable) {
        log.debug("REST request to get a page of Actions");
        Page<Action> page = actionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /actions/:id} : get the "id" action.
     *
     * @param id the id of the action to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the action, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/actions/{id}")
    public ResponseEntity<Action> getAction(@PathVariable Long id) {
        log.debug("REST request to get Action : {}", id);
        Optional<Action> action = actionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(action);
    }

    /**
     * {@code DELETE  /actions/:id} : delete the "id" action.
     *
     * @param id the id of the action to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/actions/{id}")
    public ResponseEntity<Void> deleteAction(@PathVariable Long id) {
        log.debug("REST request to delete Action : {}", id);
        actionRepository.deleteById(id);
        actionSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

  
    /**
     * {@code SEARCH  /_search/actions?query=:query} : search for the action corresponding
     * to the query.
     *
     * @param query the query of the action search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/actions")
    public ResponseEntity<List<Action>> searchActions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Actions for query {}", query);
        Page<Action> page = actionSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
