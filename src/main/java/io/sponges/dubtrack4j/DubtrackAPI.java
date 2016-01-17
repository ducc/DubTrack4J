package io.sponges.dubtrack4j;

import com.pubnub.api.PubnubException;
import io.sponges.dubtrack4j.internal.subscription.Subscribe;
import org.json.JSONObject;
import io.sponges.dubtrack4j.api.Room;
import io.sponges.dubtrack4j.event.framework.EventManager;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import io.sponges.dubtrack4j.internal.request.JoinRoomRequest;
import io.sponges.dubtrack4j.internal.request.RoomInfoRequest;
import io.sponges.dubtrack4j.internal.request.SendMessageRequest;
import io.sponges.dubtrack4j.util.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DubtrackAPI {

    private static Logger.LoggingMode loggingMode = Logger.LoggingMode.NORMAL;

    private String username, password;

    private DubAccount account;
    private EventManager eventManager;

    //              id  room
    private Map<String, Room> rooms;

    public DubtrackAPI(String username, String password) {
        this.username = username;
        this.password = password;

        this.account = new DubAccount(this, username, password);
        this.eventManager = new EventManager();

        this.rooms = new HashMap<>();
    }

    public static Logger.LoggingMode getLoggingMode() {
        return loggingMode;
    }

    public static void setLoggingMode(Logger.LoggingMode loggingMode) {
        DubtrackAPI.loggingMode = loggingMode;
    }

    public DubtrackAPI login() throws IOException {
        account.login();
        return this;
    }

    public Room joinRoom(String name) {
        Room room = null;
        try {
            room = new JoinRoomRequest(this, name, account).request();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            new Subscribe(this, room.getUrl());
        } catch (PubnubException e) {
            e.printStackTrace();
        }

        return room;
    }

    public void sendMessage(Room room, String message) {
        try {
            new SendMessageRequest(room.getId(), message, account);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public Room getRoom(String id) {
        return rooms.get(id);
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    public DubAccount getAccount() {
        return account;
    }

    public Room loadRoom(String id) {
        Room room = getRoom(id);

        if (room == null) {
            String name = null;
            try {
                JSONObject roomInfo = new RoomInfoRequest(this, id, account).request();
                name = roomInfo.getJSONObject("data").getString("roomUrl");
            } catch (IOException e) {
                e.printStackTrace();
            }

            getRooms().put(id, new RoomImpl(this, name, id));
            room = getRoom(id);
        }

        return room;
    }

}
