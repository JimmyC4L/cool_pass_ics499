package com.ics499.coolpass.service.csvimport;

import com.ics499.coolpass.domain.Environment;
import com.ics499.coolpass.domain.SharedAccount;
import com.ics499.coolpass.service.EnvironmentService;
import com.ics499.coolpass.service.SharedAccountService;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CsvImportService {

    private final
    EnvironmentService environmentService;
    private final
    SharedAccountService sharedAccountService;

    @Autowired
    public CsvImportService(EnvironmentService environmentService, SharedAccountService sharedAccountService) {
        this.environmentService = environmentService;
        this.sharedAccountService = sharedAccountService;
    }

    public void importCsv(MultipartFile  multipartFile ) throws IOException {
            File file = convertMultiPartToFile(multipartFile);
            Reader reader = new FileReader(file);

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
        file.delete();
    }

    private Environment getEnvironment(Long environmentId){
        if(environmentService.findOne(environmentId).isPresent()){
            return environmentService.findOne(environmentId).get();
        } else {
            throw new NullPointerException("SharedAccount Environment does not exist in database");
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
        File convFile = File.createTempFile(file.getOriginalFilename(), "temp");
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
