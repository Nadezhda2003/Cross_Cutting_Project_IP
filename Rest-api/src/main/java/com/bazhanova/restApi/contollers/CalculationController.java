package com.bazhanova.restApi.controllers;

import com.bazhanova.logic.interfaces.IFileReadingWriting;
import com.bazhanova.logic.readers.*;
import com.bazhanova.logic.calculate.*;
import com.bazhanova.logic.utils.*;
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
import java.util.List;



@RestController
public class FileCalculationController {
    protected final String uploadPath = Constants.FILE_UPLOAD_PATH;
    protected final String downloadPath = Constants.DOWNLOAD_URI;

    @PostMapping("/calculate/Function")
    public ResponseEntity<UploadResponse> calculateByFunction(@RequestParam("inputfile") MultipartFile inputFile,
                                                        @RequestParam(value = "outputfile") String outputFile,
                                                        @RequestParam(value = "iszipped", required = false) boolean isZipped,
                                                        @RequestParam(value = "isencrypt", required = false) boolean isEncrypt) throws IOException {
        
        
        try
        {
            if (inputFile.isEmpty())
                return ResponseEntity.badRequest().body("File is empty");
            File convertFile = new File(uploadPath + inputFile.getOriginalFilename());
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();
        }
        catch (Exception e)
        {
            throw new IOException(file + " cannot be saved", e);
        }
        String text = "";
        try {
            if (isZipped == true)
            {
                text = ArchiveZip.unarchivate(uploadPath + inputFile.getOriginalFilename(), isEncrypt);
            }
            else
            {
                text = ReadUtil.readfile(uploadPath + inputFile.getOriginalFilename(), isEncrypt);
            }
            String result = CalculationRun.calculateByFunction(text);
            WriteUtil.Writefile(downloadPath+outputFile, text);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
        File fileToDelete = new File(uploadPath + "\\" + inputFile.getOriginalFilename());
        fileToDelete.delete();
        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(outputFilename)
                .downloadUri(downloadPath + outputFilename)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/calculate/Library")
    public ResponseEntity<UploadResponse> calculateByLibrary(@RequestParam("inputfile") MultipartFile inputFile,
                                                        @RequestParam(value = "outputfile") String outputFile,
                                                        @RequestParam(value = "iszipped", required = false) boolean isZipped,
                                                        @RequestParam(value = "isencrypt", required = false) boolean isEncrypt) throws IOException {
        
        
        try
        {
            if (inputFile.isEmpty())
                return ResponseEntity.badRequest().body("File is empty");
            File convertFile = new File(uploadPath + inputFile.getOriginalFilename());
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();
        }
        catch (Exception e)
        {
            throw new IOException(file + " cannot be saved", e);
        }
        String text = "";
        try {
            if (isZipped == true)
            {
                text = ArchiveZip.unarchivate(uploadPath + inputFile.getOriginalFilename(), isEncrypt);
            }
            else
            {
                text = ReadUtil.readfile(uploadPath + inputFile.getOriginalFilename(), isEncrypt);
            }
            String result = CalculationRun.calculateByLibrary(text);
            WriteUtil.Writefile(downloadPath+outputFile, text);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
        File fileToDelete = new File(uploadPath + "\\" + inputFile.getOriginalFilename());
        fileToDelete.delete();
        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(outputFilename)
                .downloadUri(downloadPath + outputFilename)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
