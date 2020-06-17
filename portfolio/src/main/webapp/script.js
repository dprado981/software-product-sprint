// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random quote to the page.
 */
function addRandomQuote() {
  const quotes = [
    {text: 'Scared Potter?', 
      color: '#ad1cad'}, 
    {text: 'My cabbages!!!', 
      color: 'green'},
    {text: 'Always Blue!', 
      color: '#0505cc'},
    {text: 'Oh, Hi Mark.',
      color: '#d40909'}
  ];

  // Pick a random quote.
  quoteIndex = Math.floor(Math.random() * quotes.length)
  const quote = quotes[quoteIndex].text;

  // Add it to the page.
  const quoteContainer = document.getElementById('quote-container');
  quoteContainer.style.color = quotes[quoteIndex].color;
  quoteContainer.innerText = quote;
}

function changePic(imageContainer) {
  const path = imageContainer.src;
  const index = path.length-5;
  if(path.charAt(index) == "1") {
    imageContainer.src = path.substring(0, index) + "2" + path.substring(index+1);
  } else {
    imageContainer.src = path.substring(0, index) + "1" + path.substring(index+1);
  } 
}

function getComments() {
  fetch('/data').then((response) => response.json()).then((comments) => {    
    // Convert message JSON array to HTML elements then add to page
    const commentListElement = document.getElementById('message-container');
    for(var i = 0; i < comments.length; i++) {
      commentListElement.appendChild(createCommentElement(comments[i]));
    }
  });
}

/** Creates a <div> element containing a comment. */
function createCommentElement(comment) {
  const commentElement = document.createElement('div');
  commentElement.setAttribute("class", "comment");
  const timeAgo = getTimeAgo(Math.abs((new Date()) - (new Date(comment.timestamp))));
  
  const usernameElement = document.createElement('div');
  usernameElement.setAttribute("class", "comment-username");
  usernameElement.innerText = comment.username;

  const timeAgoElement = document.createElement('div');
  timeAgoElement.setAttribute("class", "comment-timeAgo");
  timeAgoElement.innerText = timeAgo;

  
  const textElement = document.createElement('div');
  textElement.setAttribute("class", "comment-text");
  textElement.innerText = comment.text;

  commentElement.appendChild(usernameElement);
  commentElement.appendChild(timeAgoElement);
  commentElement.appendChild(textElement);
  
  return commentElement;
}

function getTimeAgo(diffInMilli) {
  if (diffInMilli < 60000) {
    return "Less than 1 minute ago";
  }
  const minutes = diffInMilli / 60000;
  if (minutes < 60) {
    return Math.floor(minutes) + " minutes ago";
  }
  const hours = minutes / 60;
  if (hours < 24) {
    return Math.floor(hours) + " hours ago";
  }
  const days = hours / 24;
  if (days < 31) {
    return Math.floor(days) + " days ago";
  }
  const months = days / 30;
  if (months < 12) {
    return Math.floor(months) + " months ago";
  }
  return Math.floor(months / 12) + " years ago";
}
