package pw.sponges.dubtrack4j;

import com.pubnub.api.PubnubException;
import org.json.JSONObject;
import pw.sponges.dubtrack4j.api.DubType;
import pw.sponges.dubtrack4j.api.Room;
import pw.sponges.dubtrack4j.internal.impl.RoomImpl;
import pw.sponges.dubtrack4j.api.Song;
import pw.sponges.dubtrack4j.event.framework.EventManager;
import pw.sponges.dubtrack4j.internal.request.JoinRoomRequest;
import pw.sponges.dubtrack4j.internal.request.RoomInfoRequest;
import pw.sponges.dubtrack4j.internal.request.SendMessageRequest;
import pw.sponges.dubtrack4j.internal.request.SongDubRequest;
import pw.sponges.dubtrack4j.internal.subscription.Subscribe;
import pw.sponges.dubtrack4j.util.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dubtrack {

    private static Logger.LoggingMode loggingMode = Logger.LoggingMode.NORMAL;

    private String username, password;

    private DubAccount account;
    private EventManager eventManager;

    //              id  room
    private Map<String, Room> rooms;

    public Dubtrack(String username, String password) {
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
        Dubtrack.loggingMode = loggingMode;
    }

    public Dubtrack login() throws IOException {
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

    public void updub(Song song) {
        dub(song.getRoom().getId(), DubType.UPDUB);
    }

    public void downdub(Song song) {
        dub(song.getRoom().getId(), DubType.DOWNDUB);
    }

    public void dub(String id, DubType type) {
        try {
            new SongDubRequest(id, type, account).request();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
