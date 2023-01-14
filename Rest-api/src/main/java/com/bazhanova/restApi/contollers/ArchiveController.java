package com.bazhanova.restApi.contollers;

import com.bazhanova.logic.utils.archives.*;
import com.bazhanova.logic.readers.*;
import com.bazhanova.restApi.utils.CreateZipName;
import com.bazhanova.restApi.responses.FileUploadResponse;
import com.bazhanova.restApi.utils.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.io.File;
import java.io.IOException;


public class FileArchivingController {
    protected final String uploadPath = Constants.FILE_UPLOAD_PATH;
    protected final String downloadPath = Constants.FILE_DOWNLOAD_PATH;

    @PostMapping("/zip")
    public ResponseEntity<UploadResponse> zip(@RequestParam(value= "file") MultipartFile inputFile) throws IOException {
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
        try
        {
            String Filename = CreateZipName.createZipName(inputFile.getOriginalFilename());
            ArchiveZip.archivate(uploadPath + Filename, uploadFile);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body();
        }
        File fileToDelete = new File(uploadPath + "\\"+inputFile.getOriginalFilename());
        fileToDelete.delete();

        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(Filename)
                .downloadPath(downloadPath + Filename)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/unrar")
    public ResponseEntity<FileUploadResponse> unRar(@RequestParam(value="file") MultipartFile inputFile, @RequestParam(value="outputfile") String outputFilename) throws IOException {
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
        File file = null;
        try {
            file = new File(uploadPath + outputFilename);
            String text = ArchiveRar.unarchivate(uploadPath + inputFile.getOriginalFilename(), false);
            WriteUtil.Writefile(file.getName(), text);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body();
        }
        File fileToDelete = new File(uploadPath+"\\"+ inputFile.getOriginalFilename());
        fileToDelete.delete();

        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(outputFilename)
                .downloadUri(downloadPath + outputFilename)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    }

    @PostMapping("/unzip")
    public ResponseEntity<FileUploadResponse> unZip(@RequestParam(value= "file") MultipartFile inputFile, @RequestParam(value= "outputfile") String outputFilename) throws IOException {
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
        File file = null;
        try {
            file = new File(uploadPath + outputFilename);
            String text = ArchiveZip.unarchivate(uploadPath + inputFile.getOriginalFilename(), false);
            WriteUtil.Writefile(file.getName(), text);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body();
        }
        File fileToDelete = new File(uploadPath+"\\"+ inputFile.getOriginalFilename());
        fileToDelete.delete();

        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(outputFilename)
                .downloadUri(downloadPath + outputFilename)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}