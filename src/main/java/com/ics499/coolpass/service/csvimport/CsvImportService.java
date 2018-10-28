package com.ics499.coolpass.service.csvimport;

import com.ics499.coolpass.domain.Environment;
import com.ics499.coolpass.domain.SharedAccount;
import com.ics499.coolpass.service.EnvironmentService;
import com.ics499.coolpass.service.SharedAccountService;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CsvImportService {

    private final
    EnvironmentService environmentService;
    private final
    SharedAccountService sharedAccountService;

    private static final String SAMPLE_CSV_FILE_PATH = "D:\\JHipster\\ICS499_cool_pass\\src\\main\\resources\\config\\liquibase\\sample_shared_account.csv";

    @Autowired
    public CsvImportService(EnvironmentService environmentService, SharedAccountService sharedAccountService) {
        this.environmentService = environmentService;
        this.sharedAccountService = sharedAccountService;
    }

    public void importCsv() throws IOException {
            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));

            List<SharedAccountCsv> sharedAccountsCsv = new CsvToBeanBuilder(reader)
                .withType(SharedAccountCsv.class).build().parse();

            List<SharedAccount> sharedAccounts = new ArrayList<>();

            for(SharedAccountCsv sharedAccountcsv: sharedAccountsCsv){
                SharedAccount sharedAccount = new SharedAccount();
                sharedAccount.setLogin(sharedAccountcsv.getLogin());
                sharedAccount.setPassword(sharedAccountcsv.getPassword());
                sharedAccount.setEnvironment(getEnvironment(sharedAccountcsv.getEnvironmentId()));
                sharedAccounts.add(sharedAccount);
            }
        for(SharedAccount sharedAccount: sharedAccounts){
            sharedAccountService.save(sharedAccount);
        }
    }

    private Environment getEnvironment(Long environmentId){
        if(environmentService.findOne(environmentId).isPresent()){
            return environmentService.findOne(environmentId).get();
        } else {
            throw new NullPointerException("SharedAccount Environment does not exist in database");
        }
    }
}
