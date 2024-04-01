package com.codebase101.covidtestmgt.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codebase101.covidtestmgt.pojos.Creds;

@RestController
public class TestController {

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Creds creds){
    return new ResponseEntity<String>("Access Granted: To login endpoint: "+creds, HttpStatus.CREATED);
  }
  
}
