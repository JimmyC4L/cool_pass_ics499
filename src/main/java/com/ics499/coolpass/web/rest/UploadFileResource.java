package com.ics499.coolpass.web.rest;

import com.ics499.coolpass.service.csvimport.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing UploadFile.
 */
@Controller
@RequestMapping("/api")
public class UploadFileResource {

    @Autowired
    CsvImportService csvImportService;

    /**
     * POST  /upload-file : Upload a file.
     *
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new sharedAccount, or with status 400 (Bad Request) if the sharedAccount has already an ID
     */
    @RequestMapping(value ="/upload-file", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    @ResponseBody
    public ResponseEntity<String> saveUserDataAndFile(
        @RequestParam(value = "file" ) MultipartFile file) {
        String message;
        try {
            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            csvImportService.importCsv(file);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//
//    public ResponseEntity<String> uploadFile(@FormDataParam("file") InputStream uploadInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail throws URISyntaxException {
//        log.debug("REST request to to upload");
//        if (saveFileOnServer(uploadedInputStream, fileDetail) == false) {
//            throw new BadRequestAlertException("Unable to upload file");
//        }
//
//        return ResponseEntity.ok("Upload Successful");
//    }


}
