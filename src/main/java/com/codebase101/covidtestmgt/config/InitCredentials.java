package com.codebase101.covidtestmgt.config;

import java.util.Optional;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;

public class InitCredentials { // -> This class is implemented as a singleton
  // -> The credentials to be initialized
  public static Optional<String> USER_POOL_NAME;
  public static Optional<String> USER_POOL_ID;
  public static Optional<String> USERS_APP_CLIENT_NAME;
  public static Optional<String> USERS_APP_CLIENT_ID;
  public static Optional<String> USERS_APP_CLIENT_SECRET;
  // TODO
  // public static String ADMINS_APP_CLIENT_NAME;
  // public static String ADMINS_APP_CLIENT_ID;
  // public static String ADMINS_APP_CLIENT_SECRET;

  private static InitCredentials instance; // -> private static variable to hold the singleton instance

  private InitCredentials() {              // -> constructor is called only once if instance == null
    Region AWS_REGION = Region.EU_WEST_2;
    SsmClient client = SsmClient.builder()
        .region(AWS_REGION) // TODO -> Hardcoded region -> See if it can be provided by property file
        .build();

    USER_POOL_NAME = getParameter(client, "/ctm/USER_POOL_NAME");
    USER_POOL_ID = getParameter(client, "/ctm/USER_POOL_ID");
    USERS_APP_CLIENT_NAME = getParameter(client, "/ctm/USERS_APP_CLIENT_NAME");
    USERS_APP_CLIENT_ID = getParameter(client, "/ctm/USERS_APP_CLIENT_ID");
    USERS_APP_CLIENT_SECRET = getParameter(client, "/ctm/USERS_APP_CLIENT_SECRET");
  }

  public static void newInstance() {      // -> ensures that only one instance of InitCredentials is created
    if (instance == null)
      instance = new InitCredentials();
    // return instance;
  }

  private Optional<String> getParameter(SsmClient client, String paramName) {
    try {
      GetParameterRequest request = GetParameterRequest.builder()
          .name(paramName)
          .build();

      GetParameterResponse response = client.getParameter(request);
      return Optional.ofNullable(response.parameter().value());

    } catch (SsmException e) {
      System.err.println(e.awsErrorDetails().errorMessage());
      return Optional.empty();
    }
  }

}
