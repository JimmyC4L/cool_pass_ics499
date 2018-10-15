package com.ics499.coolpass.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ics499.coolpass.domain.Environment;
import com.ics499.coolpass.service.EnvironmentService;
import com.ics499.coolpass.web.rest.errors.BadRequestAlertException;
import com.ics499.coolpass.web.rest.util.HeaderUtil;
import com.ics499.coolpass.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Environment.
 */
@RestController
@RequestMapping("/api")
public class EnvironmentResource {

    private final Logger log = LoggerFactory.getLogger(EnvironmentResource.class);

    private static final String ENTITY_NAME = "environment";

    private final EnvironmentService environmentService;

    public EnvironmentResource(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    /**
     * POST  /environments : Create a new environment.
     *
     * @param environment the environment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new environment, or with status 400 (Bad Request) if the environment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/environments")
    @Timed
    public ResponseEntity<Environment> createEnvironment(@RequestBody Environment environment) throws URISyntaxException {
        log.debug("REST request to save Environment : {}", environment);
        if (environment.getId() != null) {
            throw new BadRequestAlertException("A new environment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Environment result = environmentService.save(environment);
        return ResponseEntity.created(new URI("/api/environments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /environments : Updates an existing environment.
     *
     * @param environment the environment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated environment,
     * or with status 400 (Bad Request) if the environment is not valid,
     * or with status 500 (Internal Server Error) if the environment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/environments")
    @Timed
    public ResponseEntity<Environment> updateEnvironment(@RequestBody Environment environment) throws URISyntaxException {
        log.debug("REST request to update Environment : {}", environment);
        if (environment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Environment result = environmentService.save(environment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, environment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /environments : get all the environments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of environments in body
     */
    @GetMapping("/environments")
    @Timed
    public ResponseEntity<List<Environment>> getAllEnvironments(Pageable pageable) {
        log.debug("REST request to get a page of Environments");
        Page<Environment> page = environmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/environments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/environments-no-page")
    @Timed
    public ResponseEntity<List<Environment>> getAllEnvironments() {
        log.debug("REST request to get all of Environments");
        List<Environment> data = environmentService.findAll();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * GET  /environments/:id : get the "id" environment.
     *
     * @param id the id of the environment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the environment, or with status 404 (Not Found)
     */
    @GetMapping("/environments/{id}")
    @Timed
    public ResponseEntity<Environment> getEnvironment(@PathVariable Long id) {
        log.debug("REST request to get Environment : {}", id);
        Optional<Environment> environment = environmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(environment);
    }

    /**
     * DELETE  /environments/:id : delete the "id" environment.
     *
     * @param id the id of the environment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/environments/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnvironment(@PathVariable Long id) {
        log.debug("REST request to delete Environment : {}", id);
        environmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
