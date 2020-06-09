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
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings = [
    {text: 'Scared Potter?', 
      color: '#ad1cad'}, 
    {text: 'My cabbages!!!', 
      color: 'green'},
    {text: 'Always Blue!', 
      color: '#0505cc'},
    {text: 'Oh, Hi Mark.',
      color: '#d40909'}
  ];

  // Pick a random greeting.
  greetingIndex = Math.floor(Math.random() * greetings.length)
  const greeting = greetings[greetingIndex].text;

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.style.color = greetings[greetingIndex].color;
  greetingContainer.innerText = greeting;
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
