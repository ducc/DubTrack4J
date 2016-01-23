/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Joe Burnard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.sponges.dubtrack4j;

import com.pubnub.api.PubnubException;
import io.sponges.dubtrack4j.event.framework.EventBus;
import io.sponges.dubtrack4j.framework.Room;

import java.io.IOException;
import java.util.Map;

public interface DubtrackAPI {

    /**
     * Logs into dubtrack
     * @return DubtrackAPI instance
     * @throws IOException
     */
    DubtrackAPI login() throws IOException;

    /**
     * Attempts to join the specified room
     * @param name the name of the room (name is at the end of room url)
     * @return Room instance
     */
    Room joinRoom(String name) throws IOException, PubnubException;

    /**
     * Gets the EventBus instance
     * @return EventBus instance
     */
    EventBus getEventBus();

    /**
     * Gets an instance of a room by id
     * @param id the id of the room
     * @return Room instance
     */
    Room getRoom(String id);

    /**
     * A Map of all the rooms
     * @return rooms Map
     */
    Map<String, Room> getRooms();

}
