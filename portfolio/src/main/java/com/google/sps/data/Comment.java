/**
* The Comment class stores the data for a comment on the webpage
*/
package com.google.sps.data;

public final class Comment {
  
  private final String userComment;
  private final long timestamp;

  public Comment(String userComment, long timestamp) {
    this.userComment = userComment;
    this.timestamp = timestamp;
  }

}