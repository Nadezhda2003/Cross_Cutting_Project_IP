package com.bazhanova.restApi.controllers;

import com.bazhanova.logic.archivesAndEncrypt.encryptAndDecrypt;
import com.bazhanova.restApi.responses.FileUploadResponse;
import com.bazhanova.restApi.utils.constants.Constants;
import com.bazhanova.restApi.utils.FileDeleteUtil;
import com.bazhanova.restApi.utils.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class EncryptController {
    protected final String uploadPath = Constants.FILE_UPLOAD_PATH;
    protected final String downloadPath = Constants.FILE_DOWNLOAD_PATH;
    protected final Key key = new DecryptEncrypt.getKey("12345");

    @PostMapping("/encrypt")
    public ResponseEntity<UploadResponse> encrypt(@RequestParam(value= "file") MultipartFile inputFile,
                                                @RequestParam(value = "outputfile") String outputFile) throws IOException {
        File file = null;
        try {
            file = new File(uploadPath + outputFile);
            DecryptEncrypt.encrypt(uploadPath + inputFile.getOriginalFilename(), file.getAbsolutePath(), key);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body();
        }
        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(outputFile)
                .downloadPath(downloadPath+outputFile)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/decrypt")
    public ResponseEntity<FileUploadResponse> decrypt(@RequestParam(value= "file") MultipartFile inputFile,
                                                      @RequestParam(value = "outputfile") String outputFile) throws IOException {
        UploadUtil.saveFile(uploadPath, inputFile);
        File file = null;
        try {
            file = new File(uploadPath + outputFile);
            DecryptEncrypt.decrypt(uploadPath + inputFile.getOriginalFilename(), file.getAbsolutePath(), key);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body();
        }
        FileUploadResponse response = FileUploadResponse.builder()
            .fileName(outputFile)
            .downloadPath(downloadPath + outputFile)
            .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}