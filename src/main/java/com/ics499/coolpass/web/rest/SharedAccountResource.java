package com.ics499.coolpass.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ics499.coolpass.domain.Environment;
import com.ics499.coolpass.domain.SharedAccount;
import com.ics499.coolpass.service.SharedAccountService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SharedAccount.
 */
@RestController
@RequestMapping("/api")
public class SharedAccountResource {

    private final Logger log = LoggerFactory.getLogger(SharedAccountResource.class);

    private static final String ENTITY_NAME = "sharedAccount";

    private final SharedAccountService sharedAccountService;

    public SharedAccountResource(SharedAccountService sharedAccountService) {
        this.sharedAccountService = sharedAccountService;
    }

    /**
     * POST  /shared-accounts : Create a new sharedAccount.
     *
     * @param sharedAccount the sharedAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sharedAccount, or with status 400 (Bad Request) if the sharedAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shared-accounts")
    @Timed
    public ResponseEntity<SharedAccount> createSharedAccount(@Valid @RequestBody SharedAccount sharedAccount) throws URISyntaxException {
        log.debug("REST request to save SharedAccount : {}", sharedAccount);
        if (sharedAccount.getId() != null) {
            throw new BadRequestAlertException("A new sharedAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SharedAccount result = sharedAccountService.save(sharedAccount);
        return ResponseEntity.created(new URI("/api/shared-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shared-accounts : Updates an existing sharedAccount.
     *
     * @param sharedAccount the sharedAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sharedAccount,
     * or with status 400 (Bad Request) if the sharedAccount is not valid,
     * or with status 500 (Internal Server Error) if the sharedAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shared-accounts")
    @Timed
    public ResponseEntity<SharedAccount> updateSharedAccount(@Valid @RequestBody SharedAccount sharedAccount) throws URISyntaxException {
        log.debug("REST request to update SharedAccount : {}", sharedAccount);
        if (sharedAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SharedAccount result = sharedAccountService.save(sharedAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sharedAccount.getId().toString()))
            .body(result);
    }

    @GetMapping("/shared-accounts-no-page")
    @Timed
    public ResponseEntity<List<SharedAccount>> getAllEnvironments() {
        log.debug("REST request to get all of Environments");
        List<SharedAccount> data = sharedAccountService.findAll();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/shared-accounts/get-all-by-env-id/{environmentId}")
    @Timed
    public ResponseEntity<List<SharedAccount>> getAllSharedAccountsByEnvironmentId(Pageable pageable, @PathVariable Long environmentId){
        log.debug("REST request to get all shared accounts by environmentId");
        Page<SharedAccount> page  = sharedAccountService.findAllByEnvironment(pageable, environmentId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shared-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/shared-accounts/get-all-by-login/{login}")
    @Timed
    public ResponseEntity<List<SharedAccount>> getAllSharedAccountsByLogin(Pageable pageable, @PathVariable String login){
        log.debug("REST request to get all shared accounts by login");
        Page<SharedAccount> page  = sharedAccountService.findAllByLogin(pageable, login);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shared-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/shared-accounts/get-all-by-login/{login}/{id}")
    @Timed
    public ResponseEntity<List<SharedAccount>> getAllSharedAccountsByEnvironmentIdAndByLogin(Pageable pageable, @RequestParam(value="param", required=false) String login, Long id){
        log.debug("REST request to get all shared accounts by login and env id");
        Page<SharedAccount> page  = sharedAccountService.findAllByEnvironmentAndByLogin(pageable, login, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "get-all-by-login-and-id");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /shared-accounts : get all the sharedAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sharedAccounts in body
     */
    @GetMapping("/shared-accounts")
    @Timed
    public ResponseEntity<List<SharedAccount>> getAllSharedAccounts(Pageable pageable) {
        log.debug("REST request to get a page of SharedAccounts");
        Page<SharedAccount> page = sharedAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shared-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shared-accounts/:id : get the "id" sharedAccount.
     *
     * @param id the id of the sharedAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sharedAccount, or with status 404 (Not Found)
     */
    @GetMapping("/shared-accounts/{id}")
    @Timed
    public ResponseEntity<SharedAccount> getSharedAccount( @PathVariable Long id) {
        log.debug("REST request to get SharedAccount : {}", id);
        Optional<SharedAccount> sharedAccount = sharedAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sharedAccount);
    }

    /**
     * DELETE  /shared-accounts/:id : delete the "id" sharedAccount.
     *
     * @param id the id of the sharedAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shared-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteSharedAccount(@PathVariable Long id) {
        log.debug("REST request to delete SharedAccount : {}", id);
        sharedAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
