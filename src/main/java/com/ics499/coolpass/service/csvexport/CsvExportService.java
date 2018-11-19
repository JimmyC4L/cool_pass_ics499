package com.ics499.coolpass.service.csvexport;

import com.ics499.coolpass.domain.SharedAccount;
import com.ics499.coolpass.service.EnvironmentService;
import com.ics499.coolpass.service.SharedAccountService;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
public class CsvExportService {

    private final
    SharedAccountService sharedAccountService;

    @Autowired
    public CsvExportService(EnvironmentService environmentService, SharedAccountService sharedAccountService) {
        this.sharedAccountService = sharedAccountService;
    }

    public void exportCsv(OutputStream outputStream) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
        zipOutputStream.putNextEntry(new ZipEntry("sharedAccounts.csv"));

        List<SharedAccount> sharedAccounts = this.sharedAccountService.findAll();

        //Writes header
        String[] header = {"id","environment_id","login","jhi_password"};
        writeCsvLine(header, zipOutputStream);

        for (SharedAccount sharedAccount : sharedAccounts) {
            String[] line = new String[4];
            line[0] = sharedAccount.getId().toString();
            line[1] = sharedAccount.getEnvironment().getId().toString();
            line[2] = sharedAccount.getLogin();
            line[3] = sharedAccount.getPassword();
            writeCsvLine(line, zipOutputStream);
        }

        zipOutputStream.closeEntry();
        zipOutputStream.close();
    }

    private void writeCsvLine(String[] line, ZipOutputStream zipOutputStream){
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        csvWriter.writeNext(line);
        try {
            zipOutputStream.write(stringWriter.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
