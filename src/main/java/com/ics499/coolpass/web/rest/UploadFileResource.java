package com.ics499.coolpass.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.File;

/**
 * REST controller for managing UploadFile.
 */
@RestController
@RequestMapping("/api")
public class UploadFileResource {

    

    /**
     * POST  /upload-file : Upload a file.
     *
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new sharedAccount, or with status 400 (Bad Request) if the sharedAccount has already an ID
     */
    @PostMapping("/upload-file")
    @Timed
    public ResponseEntity<String> saveUserDataAndFile(
        @RequestParam(value = "file") File file) {
        String rootDirectory = "c:/";
        try {

            new File(rootDirectory  + file.getName());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
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
