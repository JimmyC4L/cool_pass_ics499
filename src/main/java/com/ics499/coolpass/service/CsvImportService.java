package com.ics499.coolpass.service;

import com.ics499.coolpass.domain.SharedAccount;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvImportService {
    private static final String SAMPLE_CSV_FILE_PATH = "E:\\fjm\\ICS499\\src\\main\\resources\\config\\liquibase\\sample_shared_account.csv";

    public static void main(String[] args) throws IOException {
            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
            List<SharedAccount> sharedAccounts = new CsvToBeanBuilder(reader)
                .withType(SharedAccount.class).build().parse() ;

            for(SharedAccount sharedAccount: sharedAccounts){
                System.out.print(sharedAccount.getId()+"\n");
                System.out.print(sharedAccount.getPassword()+"\n");
                System.out.print(sharedAccount.getLogin()+"\n");
                System.out.print(sharedAccount.getEnvironment()+"\n");
            }
    }
}
