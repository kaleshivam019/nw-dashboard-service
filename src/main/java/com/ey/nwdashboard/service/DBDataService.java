package com.ey.nwdashboard.service;

import com.ey.nwdashboard.model.DBDataRequestResponse;
import com.ey.nwdashboard.model.MessageModelResponse;
import org.springframework.http.ResponseEntity;

public interface DBDataService {
    ResponseEntity<DBDataRequestResponse> retrieveDBData();
    ResponseEntity<MessageModelResponse> insertDBData(DBDataRequestResponse dbDataRequestResponse);
}
