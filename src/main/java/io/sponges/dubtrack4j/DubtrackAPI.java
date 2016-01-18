package io.sponges.dubtrack4j;

import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.event.framework.EventManager;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface DubtrackAPI {

    DubtrackAPI login() throws IOException;

    Room joinRoom(String name);

    void sendMessage(Room room, String message);

    EventManager getEventManager();

    Room getRoom(String id);

    Map<String, Room> getRooms();

    Collection<Room> getAllRooms();

    DubAccount getAccount();

}
