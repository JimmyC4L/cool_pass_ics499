package com.ics499.coolpass.web.rest;

import com.ics499.coolpass.CoolPassApp;

import com.ics499.coolpass.domain.SharedAccount;
import com.ics499.coolpass.domain.Environment;
import com.ics499.coolpass.repository.SharedAccountRepository;
import com.ics499.coolpass.service.SharedAccountService;
import com.ics499.coolpass.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.ics499.coolpass.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SharedAccountResource REST controller.
 *
 * @see SharedAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoolPassApp.class)
public class SharedAccountResourceIntTest {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private SharedAccountRepository sharedAccountRepository;
    
    @Autowired
    private SharedAccountService sharedAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSharedAccountMockMvc;

    private SharedAccount sharedAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SharedAccountResource sharedAccountResource = new SharedAccountResource(sharedAccountService);
        this.restSharedAccountMockMvc = MockMvcBuilders.standaloneSetup(sharedAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SharedAccount createEntity(EntityManager em) {
        SharedAccount sharedAccount = new SharedAccount()
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD);
        // Add required entity
        Environment environment = EnvironmentResourceIntTest.createEntity(em);
        em.persist(environment);
        em.flush();
        sharedAccount.setEnvironment(environment);
        return sharedAccount;
    }

    @Before
    public void initTest() {
        sharedAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createSharedAccount() throws Exception {
        int databaseSizeBeforeCreate = sharedAccountRepository.findAll().size();

        // Create the SharedAccount
        restSharedAccountMockMvc.perform(post("/api/shared-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAccount)))
            .andExpect(status().isCreated());

        // Validate the SharedAccount in the database
        List<SharedAccount> sharedAccountList = sharedAccountRepository.findAll();
        assertThat(sharedAccountList).hasSize(databaseSizeBeforeCreate + 1);
        SharedAccount testSharedAccount = sharedAccountList.get(sharedAccountList.size() - 1);
        assertThat(testSharedAccount.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testSharedAccount.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createSharedAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sharedAccountRepository.findAll().size();

        // Create the SharedAccount with an existing ID
        sharedAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSharedAccountMockMvc.perform(post("/api/shared-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAccount)))
            .andExpect(status().isBadRequest());

        // Validate the SharedAccount in the database
        List<SharedAccount> sharedAccountList = sharedAccountRepository.findAll();
        assertThat(sharedAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSharedAccounts() throws Exception {
        // Initialize the database
        sharedAccountRepository.saveAndFlush(sharedAccount);

        // Get all the sharedAccountList
        restSharedAccountMockMvc.perform(get("/api/shared-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sharedAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    
    @Test
    @Transactional
    public void getSharedAccount() throws Exception {
        // Initialize the database
        sharedAccountRepository.saveAndFlush(sharedAccount);

        // Get the sharedAccount
        restSharedAccountMockMvc.perform(get("/api/shared-accounts/{id}", sharedAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sharedAccount.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSharedAccount() throws Exception {
        // Get the sharedAccount
        restSharedAccountMockMvc.perform(get("/api/shared-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSharedAccount() throws Exception {
        // Initialize the database
        sharedAccountService.save(sharedAccount);

        int databaseSizeBeforeUpdate = sharedAccountRepository.findAll().size();

        // Update the sharedAccount
        SharedAccount updatedSharedAccount = sharedAccountRepository.findById(sharedAccount.getId()).get();
        // Disconnect from session so that the updates on updatedSharedAccount are not directly saved in db
        em.detach(updatedSharedAccount);
        updatedSharedAccount
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD);

        restSharedAccountMockMvc.perform(put("/api/shared-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSharedAccount)))
            .andExpect(status().isOk());

        // Validate the SharedAccount in the database
        List<SharedAccount> sharedAccountList = sharedAccountRepository.findAll();
        assertThat(sharedAccountList).hasSize(databaseSizeBeforeUpdate);
        SharedAccount testSharedAccount = sharedAccountList.get(sharedAccountList.size() - 1);
        assertThat(testSharedAccount.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testSharedAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingSharedAccount() throws Exception {
        int databaseSizeBeforeUpdate = sharedAccountRepository.findAll().size();

        // Create the SharedAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSharedAccountMockMvc.perform(put("/api/shared-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sharedAccount)))
            .andExpect(status().isBadRequest());

        // Validate the SharedAccount in the database
        List<SharedAccount> sharedAccountList = sharedAccountRepository.findAll();
        assertThat(sharedAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSharedAccount() throws Exception {
        // Initialize the database
        sharedAccountService.save(sharedAccount);

        int databaseSizeBeforeDelete = sharedAccountRepository.findAll().size();

        // Get the sharedAccount
        restSharedAccountMockMvc.perform(delete("/api/shared-accounts/{id}", sharedAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SharedAccount> sharedAccountList = sharedAccountRepository.findAll();
        assertThat(sharedAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SharedAccount.class);
        SharedAccount sharedAccount1 = new SharedAccount();
        sharedAccount1.setId(1L);
        SharedAccount sharedAccount2 = new SharedAccount();
        sharedAccount2.setId(sharedAccount1.getId());
        assertThat(sharedAccount1).isEqualTo(sharedAccount2);
        sharedAccount2.setId(2L);
        assertThat(sharedAccount1).isNotEqualTo(sharedAccount2);
        sharedAccount1.setId(null);
        assertThat(sharedAccount1).isNotEqualTo(sharedAccount2);
    }
}
