package com.codebase101.covidtestmgt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codebase101.covidtestmgt.pojos.AccessToken;
import com.codebase101.covidtestmgt.pojos.Creds;
import com.codebase101.covidtestmgt.services.UserLogin;


@RestController
public class TestController {

  @Autowired
  UserLogin userLogin;

  @GetMapping("/")
  public String hello() {
    return "Access Granted: Hello World!";
  }

  @PostMapping("/login")
  public ResponseEntity<AccessToken> login(@RequestBody Creds creds) {
    String accessToken = userLogin.byUsernameAndPassword(creds.username(), creds.password());
    if(accessToken == null)
      return ResponseEntity.badRequest().body(new AccessToken(creds.username(), accessToken));
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(new AccessToken(creds.username(), accessToken));
  }

}
