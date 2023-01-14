package com.bazhanova.restApi.controllers;

import com.bazhanova.restApi.responses.FileUploadResponse;
import com.bazhanova.restApi.utils.constants.Constants;
import com.bazhanova.restApi.utils.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {
    protected final String uploadPath = Constants.FILE_UPLOAD_PATH;
    protected final String downloadPath = Constants.FILE_DOWNLOAD_PATH;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try{
            if (file.isEmpty)
            return ResponseEntity.badRequest().body("File is empty");
        File convertFile = new File(uploadPath + file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();

        UploadResponse response = UploadResponse.builder()
            .fileName(convertFile)
            .downloadPath(downloadPath)
            .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new IOException(file + " cannot be saved", e);
        }
    }
}
