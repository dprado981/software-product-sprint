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
import java.util.Arrays;
import java.util.ArrayList;

public final class FindMeetingQuery {

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // If no attendees, all times work
    if (request.getAttendees().isEmpty()) {
      return Arrays.asList(TimeRange.WHOLE_DAY);
    }

    // If a request lasts for longer than the day, no times work
    if(request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
      return Arrays.asList();
    }


    // Get all event times
    ArrayList<TimeRange> eventTimeRanges = new ArrayList<>();
    for(Event event : events.toArray(new Event[0])) {
      eventTimeRanges.add(event.getWhen());
    } 

    Collections.sort(eventTimeRanges, TimeRange.ORDER_BY_START);


    // If single event, invert time
    if(events.size() == 1) {
      return invertTimeRange(eventTimeRanges.get(0));
    } 
/*
    // If times overlap, get the start and end of them, then invert
    System.out.println(hasBlockOverlap(eventTimeRanges));
    if (hasBlockOverlap(eventTimeRanges)) {
      return invertTimeRange(mergeTimeRanges(eventTimeRanges));
    }
*/
    return null;
  }

  // Returns the TimeRanges that do not include the passed in timeRange
  private Collection<TimeRange> invertTimeRange(TimeRange timeRange) {
    TimeRange before = TimeRange.fromStartEnd(TimeRange.START_OF_DAY, timeRange.start(), false);
    TimeRange after = TimeRange.fromStartEnd(timeRange.end(), TimeRange.END_OF_DAY, true);
    return Arrays.asList(before, after);

  }

  // Checks if all TimeRanges overlap to form one block
  private boolean hasBlockOverlap(ArrayList<TimeRange> ranges) {
    Collections.sort(ranges, TimeRange.ORDER_BY_START);
    for(int i = 0; i < ranges.size() - 1; i++) {
      if (!ranges.get(i).overlaps(ranges.get(i+1))) {
        return false;
      }
    }
    return true;
  }

  // Combines block overlapping TimeRanges into one
  private TimeRange mergeTimeRanges(Collection<TimeRange> ranges) {
    int start = TimeRange.START_OF_DAY;
    int end = TimeRange.END_OF_DAY;
    for(TimeRange timeRange : ranges) {
      if (timeRange.start() > start) {
        start = timeRange.start();
      }
      if (timeRange.end() < end) {
        end = timeRange.end();
      }
    }
    return TimeRange.fromStartEnd(start, end, true);
  }


}
