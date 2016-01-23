package io.sponges.dubtrack4j;

import com.pubnub.api.PubnubException;
import io.sponges.dubtrack4j.event.framework.EventManager;
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
     * Gets the EventManager instance
     * @return EventManager instance
     */
    EventManager getEventManager();

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
