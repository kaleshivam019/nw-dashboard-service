package com.ey.nwdashboard.service;

import org.springframework.http.ResponseEntity;

public interface FetchReportService {
    ResponseEntity fetchReport();
    ResponseEntity generateReport(String startDate, String endDate, String userGPN);
    ResponseEntity downloadFile();
}
