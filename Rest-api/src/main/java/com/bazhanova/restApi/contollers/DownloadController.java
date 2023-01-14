package com.bazhanova.restApi.controllers;

import com.bazhanova.restApi.utils.FileDownloadUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DownloadController {

@RequestMapping(value = "/download", method = RequestMethod.GET) 
    public ResponseEntity<?> downloadFile(@PathVariable("file") String filename) throws IOException  {
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
      
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()+"\""));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
      
        ResponseEntity<?> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
                                        MediaType.parseMediaType("application/txt")).body(resource);
      
        return responseEntity;
    }
}