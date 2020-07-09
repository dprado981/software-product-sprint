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

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;

public final class FindMeetingQuery {

  private static final boolean INCLUSIVE = true;
  private static final boolean EXCLUSIVE = false;

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // If a request lasts for longer than the day, no times work
    if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
      return Collections.emptyList();
    }
    
    Collection<String> requestingAttendees = request.getAttendees();
    ArrayList<TimeRange> relevantTimes = getRelevantTimes(events, requestingAttendees);

    ArrayList<TimeRange> blockTimes = mergeBlocks(relevantTimes);
    
    ArrayList<TimeRange> invertedTimeRanges = invertTimeRanges(blockTimes);

    ArrayList<TimeRange> possibleTimeRanges = getTimesLongerThan(request.getDuration(), invertedTimeRanges);

    return possibleTimeRanges;
  }

  /** Returns only the TimeRanges that correspond with events that an attendee from requestingAttendees is attending. */
  private ArrayList<TimeRange> getRelevantTimes(Collection<Event> events, Collection<String> requestingAttendees) {
    ArrayList<TimeRange> relevantTimes = new ArrayList<>();
    for (Event event : events) {
      Collection<String> eventAttendees = event.getAttendees();
      // Add to relevantTimes if a requesting attendee is in the event
      for (String requestingAttendee : requestingAttendees) {
        if (eventAttendees.contains(requestingAttendee)) {
          relevantTimes.add(event.getWhen());
          break;
        }
      }
    }
    return relevantTimes;
  }

  /** Returns the result of merging TimeRanges from relevantTimes. */
  private ArrayList<TimeRange> mergeBlocks(ArrayList<TimeRange> relevantTimes) {
    // Sort TimeRanges by their start time for easier merging of blocks
    Collections.sort(relevantTimes, TimeRange.ORDER_BY_START);
    ArrayList<TimeRange> blockTimes = new ArrayList<>();
    for (TimeRange time : relevantTimes) {
      // Check if time with any existing events, if so replace event
      TimeRange timeToAdd = time;
      boolean blockTimeAdded = false;
      for (int i = 0; i < blockTimes.size(); i++) {
        // Use TimeRange.overlaps(TimeRange) to see if they overlap
        TimeRange oldEventTime = blockTimes.get(i);
        TimeRange newEventTime = time;
        if(oldEventTime.overlaps(newEventTime)) {
          // Make new TimeRange that spans both TimeRanges
          int newStart = Math.min(oldEventTime.start(), newEventTime.start());
          int newEnd = Math.max(oldEventTime.end(), newEventTime.end());
          TimeRange newRange = TimeRange.fromStartEnd(newStart, newEnd, EXCLUSIVE);

          // Replace old TimeRange with new block TimeRange
          blockTimes.set(i, newRange);
          blockTimeAdded = true;
          // Ensures that it only merges with the first block TimeRange
          break;
        }
      }
      // Add to end of list if a block was not added
      if (!blockTimeAdded) {
        blockTimes.add(time);
      }
    }
    return blockTimes;
  }

  /** Returns the inverse of TimeRanges from blockTimes. */
  private ArrayList<TimeRange> invertTimeRanges(ArrayList<TimeRange> blockTimes) {
    ArrayList<TimeRange> invertedTimeRanges = new ArrayList<>();
    int lastEndTime = TimeRange.START_OF_DAY;
    // Creates new TimeRanges that have the same start time as the previous end time
    // and the same end time as the next start time, or the end of the day
    for (TimeRange blockTime : blockTimes) {
      int start = lastEndTime;
      int end = blockTime.start();
      invertedTimeRanges.add(TimeRange.fromStartEnd(start, end, EXCLUSIVE));
      lastEndTime = end + blockTime.duration();
    }
    invertedTimeRanges.add(TimeRange.fromStartEnd(lastEndTime, TimeRange.END_OF_DAY, INCLUSIVE));
    return invertedTimeRanges;
  }

  /** Returns TimeRanges with a duration greater than or equal to the specified duration. */
  private ArrayList<TimeRange> getTimesLongerThan(long duration, ArrayList<TimeRange> timeRanges){
    ArrayList<TimeRange> longTimeRanges = new ArrayList<>();
    for (TimeRange range : timeRanges) {
      if (range.duration() >= duration) {
        longTimeRanges.add(range);
      }
    }
    return longTimeRanges;
  }
}
