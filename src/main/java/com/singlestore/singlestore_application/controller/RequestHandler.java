package com.singlestore.singlestore_application.controller;


import com.singlestore.singlestore_application.config.AppConfig;
import com.singlestore.singlestore_application.model.FactModel;
import com.singlestore.singlestore_application.service.S2ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** The request controller. */

@RestController
@RequestMapping("/api/v1/controller")
@Tag(name = "Request Handler", description = "Handles get/post request made to system by user")
public class RequestHandler {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private S2ApplicationService service;

    @GetMapping("/health-check")
    public ResponseEntity<String> getHealthCheckStatus() {
        return ResponseEntity.ok("application started successfully on port:: " + appConfig.getServerPort());
    }

    @GetMapping("/get-records-by-waitover-flag/{waitoverFlag}")
    public List<FactModel> getRecordsByWaitoverFlag(@PathVariable String waitoverFlag){
        return service.getRecordsByWaitoverFlage(waitoverFlag);
    }

    @GetMapping("/get-all-records")
    public List<FactModel> getAllRecords(){
        return service.getAllRecords();
    }

    @GetMapping("/produce-message")
    public void produceMessage() {
        service.fetchAndProduceRecords();
    }

}