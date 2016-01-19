package io.sponges.dubtrack4j.internal.impl;

import io.sponges.dubtrack4j.DubtrackAPI;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.Song;
import io.sponges.dubtrack4j.framework.User;
import io.sponges.dubtrack4j.internal.request.BanUserRequest;
import io.sponges.dubtrack4j.internal.request.KickUserRequest;
import io.sponges.dubtrack4j.internal.request.RoomPlaylistRequest;
import io.sponges.dubtrack4j.internal.request.UserInfoRequest;
import io.sponges.dubtrack4j.util.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RoomImpl implements Room {

    private final Map<String, User> users = new HashMap<>();

    private final DubtrackAPI dubtrack;
    private final String url, id;

    private final AtomicReference<String> atomicPlaylistId = new AtomicReference<>();
    private final AtomicReference<Song> current = new AtomicReference<>();

    public RoomImpl(DubtrackAPI dubtrack, String url, String id) {
        this.dubtrack = dubtrack;
        this.url = url;
        this.id = id;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPlaylistId() {
        String playlistId = atomicPlaylistId.get();

        if (playlistId == null) {
            try {
                playlistId = new RoomPlaylistRequest(id, dubtrack.getAccount()).request().getJSONObject("data").getJSONObject("song").getString("_id");
                atomicPlaylistId.set(playlistId);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return playlistId;
    }

    public AtomicReference<String> getAtomicPlaylistId() {
        return atomicPlaylistId;
    }

    public void setPlaylistId(String playlistId) {
        atomicPlaylistId.set(playlistId);
    }

    @Override
    public Map<String, User> getUsers() {
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        for (User user : users.values()) {
            if (user.getName().equalsIgnoreCase(username)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public User getUserById(String id) {
        return users.get(id);
    }

    @Override
    public Song getCurrent() {
        return current.get();
    }

    @Override
    public void setCurrent(Song current) {
        this.current.set(current);
    }

    @Override
    public void sendMessage(String message) {
        dubtrack.sendMessage(this, message);
    }

    public User loadUser(String id, String username) {
        User user = getUserById(id);

        if (user == null) {
            getUsers().put(id, new UserImpl(id, username));
            user = getUserById(id);
        }

        return user;
    }
    
    public User loadUser(DubtrackAPI dubtrack, String id) {
        User user = getUserById(id);

        if (user == null) {
            JSONObject userInfo = null;
            try {
                userInfo = new UserInfoRequest(dubtrack, id, dubtrack.getAccount()).request();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            String username = userInfo.getJSONObject("data").getString("username");

            getUsers().put(id, new UserImpl(id, username));
            user = getUserById(id);
        }

        return user;
    }

    @Override
    public void kickUser(String username) {
        User user = getUserByUsername(username);
        kickUser(user);
    }

    @Override
    public void kickUser(User user) {
        try {
            JSONObject jsonObject = new KickUserRequest(dubtrack, dubtrack.getAccount(), id, url, user.getId()).request();
            Logger.debug(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void banUser(String username) {
        User user = getUserByUsername(username);
        banUser(user, 0);
    }

    @Override
    public void banUser(String username, int length) {
        User user = getUserByUsername(username);
        banUser(user, length);
    }

    @Override
    public void banUser(User user) {
        banUser(user, 0);
    }

    @Override
    public void banUser(User user, int length) {
        try {
            JSONObject jsonObject = new BanUserRequest(dubtrack, dubtrack.getAccount(), id, url, user.getId(), length).request();
            Logger.debug(true, jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
