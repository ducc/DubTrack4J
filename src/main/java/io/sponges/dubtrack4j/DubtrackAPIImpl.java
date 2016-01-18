package io.sponges.dubtrack4j;

import com.pubnub.api.PubnubException;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.event.framework.EventManager;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import io.sponges.dubtrack4j.internal.request.JoinRoomRequest;
import io.sponges.dubtrack4j.internal.request.RoomInfoRequest;
import io.sponges.dubtrack4j.internal.request.SendMessageRequest;
import io.sponges.dubtrack4j.internal.subscription.Subscribe;
import io.sponges.dubtrack4j.util.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DubtrackAPIImpl implements DubtrackAPI {

    private static Logger.LoggingMode loggingMode = Logger.LoggingMode.NORMAL;

    private final String username, password;

    private final DubAccount account;
    private final EventManager eventManager;

    //              id  room
    private Map<String, Room> rooms;

    DubtrackAPIImpl(String username, String password) {
        this.username = username;
        this.password = password;

        this.account = new DubAccount(this, username, password);
        this.eventManager = new EventManager();

        this.rooms = new HashMap<>();
    }

    @Override
    public DubtrackAPIImpl login() throws IOException {
        account.login();
        return this;
    }

    @Override
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

    @Override
    public void sendMessage(Room room, String message) {
        try {
            new SendMessageRequest(room.getId(), message, account);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public Room getRoom(String id) {
        return rooms.get(id);
    }

    @Override
    public Map<String, Room> getRooms() {
        return rooms;
    }

    @Override
    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    @Override
    public DubAccount getAccount() {
        return account;
    }

    public RoomImpl loadRoom(String id) {
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

        return (RoomImpl) room;
    }

    public static Logger.LoggingMode getLoggingMode() {
        return loggingMode;
    }

    public void setLoggingMode(Logger.LoggingMode loggingMode) {
        DubtrackAPIImpl.loggingMode = loggingMode;
    }

}
