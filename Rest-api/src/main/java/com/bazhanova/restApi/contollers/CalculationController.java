package com.bazhanova.restApi.controllers;

import com.bazhanova.logic.builder.IFileReadingWriting;
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

public static String readfile(String name, boolean a) throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException {
    String text = "";
    Pattern pattern = Pattern.compile("\\.\\w+");
    Matcher matcher = pattern.matcher(name);
    String format = "";
    if (matcher.find()) {
        format = matcher.group();
    }
    IFileReadingWriting reader = null;
    switch (format) {
        case ".txt":
            reader = new TxtFile();
            text = reader.reading(name, a);
            break;
        case ".xml":
            reader = new XmlFile();
            text = reader.reading(name, a);
            break;
        case ".json":
            reader = new JsonFile();
            text = reader.reading(name, a);
            break;
    }
    return text;
}


@RestController
@RequestMapping("api/v1/")
public class FileCalculationController {
    protected final String uploadPath = Constants.FILE_UPLOAD_PATH;
    protected final String downloadUri = Constants.DOWNLOAD_URI;

    @PostMapping("/calculate")
    public ResponseEntity<FileUploadResponse> calculate(@RequestParam("file") MultipartFile inputFile,
                                                        @RequestParam(value = "outputfile") String outputFilename,
                                                        @RequestParam(value = "iszipped", required = false) boolean isZipped,
                                                        @RequestParam(value="decryptionkeys", required = false) List<String> decryptionKeys,
                                                        @RequestParam(value = "extension") String extension) throws IOException {
        String text = "";
        try {
            if (isZipped == true)
            {
                text = new ArchiveZip.unarchivate(uploadPath + inputFile.getOriginalFilename(), decryptionKeys);
            }
            else
            {
                text = readfile(uploadPath + inputFile.getOriginalFilename(), decryptionKeys);
            }
            IFileReadingWriting reader = builder.getFileReader();
            file = new File(uploadPath + outputFilename);
            reader.calculate(text);
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
