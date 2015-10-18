package pw.sponges.dubtrack4j;

import com.pubnub.api.PubnubException;
import org.json.JSONObject;
import pw.sponges.dubtrack4j.api.DubType;
import pw.sponges.dubtrack4j.api.Room;
import pw.sponges.dubtrack4j.api.Song;
import pw.sponges.dubtrack4j.api.User;
import pw.sponges.dubtrack4j.framework.event.EventManager;
import pw.sponges.dubtrack4j.internal.request.*;
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

    public Room getRoom(Dubtrack dubtrack, String id) {
        Room room = dubtrack.getRoom(id);

        if (room == null) {
            String name = null;
            try {
                JSONObject roomInfo = new RoomInfoRequest(dubtrack, id, dubtrack.getAccount()).request();
                name = roomInfo.getJSONObject("data").getString("roomUrl");
            } catch (IOException e) {
                e.printStackTrace();
            }

            dubtrack.getRooms().put(id, new Room(dubtrack, name, id));
            room = dubtrack.getRoom(id);
        }

        return room;
    }

    public User getUser(Room room, String id, String username) {
        User user = room.getUserById(id);

        if (user == null) {
            room.getUsers().put(id, new User(id, username));
            user = room.getUserById(id);
        }

        return user;
    }

    public User getUser(Dubtrack dubtrack, Room room, String id) {
        User user = room.getUserById(id);

        if (user == null) {
            JSONObject userInfo = null;
            try {
                userInfo = new UserInfoRequest(dubtrack, id, dubtrack.getAccount()).request();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String username = userInfo.getJSONObject("data").getString("username");

            room.getUsers().put(id, new User(id, username));
            user = room.getUserById(id);
        }

        return user;
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
