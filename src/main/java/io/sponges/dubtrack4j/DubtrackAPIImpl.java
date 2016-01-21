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
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class DubtrackAPIImpl implements DubtrackAPI {

    private static Logger.LoggingMode loggingMode = Logger.LoggingMode.NORMAL;

    //              id  room
    private final Map<String, Room> rooms = new HashMap<>();

    private final String username, password;

    private final DubAccount account;
    private final EventManager eventManager;

    private final OkHttpClient httpClient;

    DubtrackAPIImpl(String username, String password) {
        this.username = username;
        this.password = password;

        this.account = new DubAccount(this, username, password);
        this.eventManager = new EventManager();

        this.httpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    private final Map<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<>();
                    }
                })
                .build();
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public DubtrackAPI login() throws IOException {
        account.login();
        return this;
    }

    @Override
    public Room joinRoom(String name) {
        Room room;
        try {
            room = new JoinRoomRequest(this, name, account).request();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            new Subscribe(this, room.getName());
        } catch (PubnubException e) {
            Logger.warning("Could not subscribe to pubnub when joining the room!");
            e.printStackTrace();
            return null;
        }

        return room;
    }

    @Override
    public void sendMessage(Room room, String message) {
        try {
            new SendMessageRequest(room.getId(), message, this);
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
