package com.bennu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bennu.constants.Constants;

@RestController 
@RequestMapping(path = Constants.BASE_API + "/common", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonController {
    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
