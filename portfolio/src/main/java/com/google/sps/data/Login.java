package com.google.sps.data;

/** The Login class stores the data for a user's email address and log in/out link. */
public final class Login {
  
  private final String emailAddress;
  private final String url;

  public Login(String emailAddress, String url) {
    this.emailAddress = emailAddress;
    this.url = url;
  }

}
