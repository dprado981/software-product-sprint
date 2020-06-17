/**
* The Comment class stores the data for a comment on the webpage
*/
package com.google.sps.data;

public final class Comment {
  
  private final String username;
  private final String text;
  private final long timestamp;

  public Comment(String username, String text, long timestamp) {
    this.username = username;
    this.text = text;
    this.timestamp = timestamp;
  }

}