package com.ics499.coolpass.web.rest;

import com.ics499.coolpass.service.csvexport.CsvExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * REST controller for managing UploadFile.
 */
@Controller
@RequestMapping("/api")
public class ExportFileResource {

    @Autowired
    CsvExportService csvExportService;

    /**
     * POST  /upload-file : Upload a file.
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new sharedAccount, or with status 400 (Bad Request) if the sharedAccount has already an ID
     */
    @RequestMapping(value = "/export-csv", method = RequestMethod.GET)
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=sharedAccountsExport.zip");
        this.csvExportService.exportCsv(response.getOutputStream());
    }
}
