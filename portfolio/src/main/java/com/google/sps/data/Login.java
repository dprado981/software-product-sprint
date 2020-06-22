/**
* The Login class stores the data for a user's email address and log in/out link
*/
package com.google.sps.data;

public final class Login {
  
  private final String emailAddress;
  private final String URL;

  public Login(String emailAddress, String URL) {
    this.emailAddress = emailAddress;
    this.URL = URL;
  }

}