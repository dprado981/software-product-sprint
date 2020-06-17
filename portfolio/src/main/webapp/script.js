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

function getMessages() {
  fetch('/data').then((response) => response.json()).then((messages) => {    
    // Convert message JSON array to HTML list and to page
    const messageListElement = document.getElementById('message-container');
    messageListElement.innerHTML = "";
    for(var i = 0; i < messages.length; i++) {
      messageListElement.appendChild(createListElement(messages[i]));
    }
  });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
