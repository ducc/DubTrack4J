package io.sponges.dubtrack4j;

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
    Room joinRoom(String name);

    void sendMessage(Room room, String message);

    EventManager getEventManager();

    Room getRoom(String id);

    Map<String, Room> getRooms();

    DubAccount getAccount();

}
