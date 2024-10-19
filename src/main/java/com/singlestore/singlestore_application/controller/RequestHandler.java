package com.singlestore.singlestore_application.controller;

import com.singlestore.singlestore_application.dao.ApplicationDAO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/** The request controller. */
@RestController
@RequestMapping("/request/api/v1")
@Tag(name = "Request Handler", description = "Handles manual request made to system by user")
public class RequestHandler {

    @Autowired
    private ApplicationDAO dao;

    @GetMapping("/get-health-check")
    public JSONObject healthCheck() {
        return new JSONObject(HttpStatus.OK);
    }

    @GetMapping("/get-access-token")
    public ResponseEntity<String> getAccessToken(@RequestParam("user") String userName){
        return ResponseEntity.ok(dao.getAccessToken(userName));
    }

}