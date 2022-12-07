package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.service.DownloadUserManualService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class DownloadUserManualServiceImpl implements DownloadUserManualService {

    @Value("${usermanual.dir.path}")
    private String DIR_PATH;

    @Value("${usermanual.filename}")
    private String FILE_NAME;

    @Override
    public ResponseEntity downloadFile() {
        byte[] byteArray;  // data comes from external service call in byte[]
        try {
            byteArray = Files.readAllBytes(Paths.get(DIR_PATH + FILE_NAME));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FILE_NAME)
                    .body(byteArray);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
