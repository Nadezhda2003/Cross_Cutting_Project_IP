package com.bazhanova.restApi.controllers;

import com.bazhanova.logic.archivesAndEncrypt.encryptAndDecrypt;
import com.bazhanova.restApi.responses.FileUploadResponse;
import com.bazhanova.restApi.utils.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        File fileToDelete = new File(uploadPath + "\\"+inputFile.getOriginalFilename());
        fileToDelete.delete();
        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(outputFile)
                .downloadPath(downloadPath+outputFile)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/decrypt")
    public ResponseEntity<FileUploadResponse> decrypt(@RequestParam(value= "file") MultipartFile inputFile,
                                                      @RequestParam(value = "outputfile") String outputFile) throws IOException {
        try
        {
            if (file.isEmpty)
            return ResponseEntity.badRequest().body("File is empty");
            File convertFile = new File(uploadPath + file.getOriginalFilename());
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();
        }
        catch (Exception e)
        {
            throw new IOException(file + " cannot be saved", e);
        }
        File file = null;
        try {
            file = new File(uploadPath + outputFile);
            DecryptEncrypt.decrypt(uploadPath + inputFile.getOriginalFilename(), file.getAbsolutePath(), key);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body();
        }
        File fileToDelete = new File(uploadPath + "\\"+ inputFile.getOriginalFilename());
        fileToDelete.delete();
        FileUploadResponse response = FileUploadResponse.builder()
            .fileName(outputFile)
            .downloadPath(downloadPath + outputFile)
            .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}