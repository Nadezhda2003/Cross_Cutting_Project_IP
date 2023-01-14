package com.bazhanova.restApi.controllers;

import com.bazhanova.logic.utils.ciphers.*;
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
@RequestMapping("api/v1/")
public class FileEncryptionController {
    protected final String uploadPath = Constants.FILE_UPLOAD_PATH;
    protected final String downloadUri = Constants.DOWNLOAD_URI;
    @PostMapping("/encrypt")
    public ResponseEntity<FileUploadResponse> encrypt(@RequestParam(value= "file") MultipartFile inputFile,
                                                      @RequestParam(value = "outputfile") String outputFilename,
                                                      @RequestParam(value="key") String key) throws IOException {
        File file = null;
        try {
            file = new File(uploadPath + outputFilename);
            DecryptEncrypt.encrypt(uploadPath + inputFile.getOriginalFilename(), file.getAbsolutePath(), key);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        } finally {
            FileDeleteUtil.deleteFile(uploadPath, inputFile.getOriginalFilename());
        }
        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(outputFilename)
                .size(file.getTotalSpace())
                .downloadUri(downloadUri + outputFilename)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/decrypt")
    public ResponseEntity<FileUploadResponse> decrypt(@RequestParam(value= "file") MultipartFile inputFile,
                                                      @RequestParam(value = "outputfile") String outputFilename,
                                                      @RequestParam(value="key") String key) throws IOException {
        if (!KeyValidation.isValidDecryptionKey(key)) {
            return ResponseEntity.badRequest().build();
        }
        FileUploadUtil.saveFile(uploadPath, inputFile);
        File file = null;
        try {
            file = new File(uploadPath + outputFilename);
            DecryptEncrypt.decrypt(uploadPath + inputFile.getOriginalFilename(), file.getAbsolutePath(), key);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        } finally {
            FileDeleteUtil.deleteFile(uploadPath, inputFile.getOriginalFilename());
        }
        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(outputFilename)
                .size(file.getTotalSpace())
                .downloadUri(downloadUri + outputFilename)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}