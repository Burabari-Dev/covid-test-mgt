package com.codebase101.covidtestmgt.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codebase101.covidtestmgt.config.InitCredentials;
import com.codebase101.covidtestmgt.pojos.Creds;

@RestController
public class TestController {

  @GetMapping("/")
  public String hello(){
    return "Access Granted: Hello World!";
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Creds creds){
    // InitCredentials.newInstance();
    // System.out.println(InitCredentials.USERS_APP_CLIENT_ID.orElse("USERS_APP_CLIENT_ID"));
    // System.out.println(InitCredentials.USERS_APP_CLIENT_NAME.orElse("USERS_APP_CLIENT_NAME"));
    // System.out.println(InitCredentials.USERS_APP_CLIENT_SECRET.orElse("USERS_APP_CLIENT_SECRET"));
    // System.out.println(InitCredentials.USER_POOL_ID.orElse("USER_POOL_ID"));
    // System.out.println(InitCredentials.USER_POOL_NAME.orElse("USER_POOL_NAME"));
    return new ResponseEntity<String>("Access Granted: To login endpoint: "+creds, HttpStatus.CREATED);
  }
  
}
