package com.codebase101.covidtestmgt.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codebase101.covidtestmgt.config.InitCredentials;
import com.codebase101.covidtestmgt.utils.HashUtils;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

@Service
public class UserLogin {
  private CognitoIdentityProviderClient client;
  private final String CLIENT_ID;
  private final String CLIENT_SECRET;

  @Autowired
  public UserLogin() {
    client = CognitoIdentityProviderClient
        .builder()
        .region(Region.EU_WEST_2)
        .build();
    InitCredentials.newInstance();                                      //-> Just in case it was not pre-initialized
    CLIENT_ID = InitCredentials.USERS_APP_CLIENT_ID.get();
    CLIENT_SECRET = InitCredentials.USERS_APP_CLIENT_SECRET.get();
  }

  public String byUsernameAndPassword(String username, String password) {
    
    String secretHash = HashUtils.calculateSecretHash(CLIENT_ID, CLIENT_SECRET, username);

    Map<String, String> authParams = new HashMap<>();
    authParams.put("USERNAME", username);
    authParams.put("PASSWORD", password);
    authParams.put("SECRET_HASH", secretHash);

    InitiateAuthRequest request = InitiateAuthRequest
        .builder()
        .clientId(CLIENT_ID)
        .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
        .authParameters(authParams)
        .build();

    try {
      InitiateAuthResponse response = client.initiateAuth(request);
      return response.authenticationResult().accessToken();
    } catch (CognitoIdentityProviderException ex) {
      //TODO: Better way to handle these errors
      return null;
    }
  }

}
