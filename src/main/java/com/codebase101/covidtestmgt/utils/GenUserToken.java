package com.codebase101.covidtestmgt.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.codebase101.covidtestmgt.config.AppCredentials;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

@Deprecated
public class GenUserToken {
  
  private static String USERPOOL_CLIENT_ID = "59q9j7cs029ohtheb3d4f2dht";
  private static String USERPOOL_CLIENT_SECRET = "eavr4s5lcq60igui9ubt1s1u6h3ocmqsv9f6f7q5jeo7vasauvb";
  
  public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) {
    final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    
    SecretKeySpec signingKey = new SecretKeySpec(
      userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
      HMAC_SHA256_ALGORITHM);
      try {
        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        mac.update(userName.getBytes(StandardCharsets.UTF_8));
        byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(rawHmac);
      } catch (IllegalStateException | InvalidKeyException | NoSuchAlgorithmException e) {
        throw new RuntimeException("Error while calculating ");
    }
  }
  
  public static String byUsernameAndPassword(String username, String password) {
    USERPOOL_CLIENT_ID = AppCredentials.USERS_APP_CLIENT_ID.get();
    USERPOOL_CLIENT_SECRET = AppCredentials.USERS_APP_CLIENT_SECRET.get();

    CognitoIdentityProviderClient client = CognitoIdentityProviderClient
        .builder()
        .region(Region.EU_WEST_2)
        .build();
    String secretHash = calculateSecretHash(USERPOOL_CLIENT_ID, USERPOOL_CLIENT_SECRET, username);

    Map<String, String> authParams = new HashMap<>();
    authParams.put("USERNAME", username);
//    authParams.put("EMAIL", "burabaritech@gmail.com");
    authParams.put("PASSWORD", password);
    authParams.put("SECRET_HASH", secretHash);

    InitiateAuthRequest request = InitiateAuthRequest
            .builder()
            .clientId(USERPOOL_CLIENT_ID)
            .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
            .authParameters(authParams)
            .build();

    try {
      InitiateAuthResponse response = client.initiateAuth(request);
      return response.authenticationResult().accessToken();
    } catch (CognitoIdentityProviderException ex) {
      // System.err.println("ERR-code --> " + ex.awsErrorDetails().errorCode());
      // System.err.println("ERR-message --> " + ex.awsErrorDetails().errorMessage());
      // System.err.println("ERR-service name --> " + ex.awsErrorDetails().serviceName());
      return null;
    }
  }
  
}
