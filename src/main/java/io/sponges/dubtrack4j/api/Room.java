package io.sponges.dubtrack4j.api;

import io.sponges.dubtrack4j.DubtrackAPI;

import java.util.Map;

public interface Room {

    // TODO change to getName
    String getUrl();

    String getId();

    Map<String, User> getUsers();

    User getUserByUsername(String username);

    User getUserById(String id);

    Song getCurrent();

    void setCurrent(Song current);

    void sendMessage(String message);

    User loadUser(String id, String username);

    User loadUser(DubtrackAPI dubtrack, String id);

    void kickUser(String username);

    void kickUser(User user);

    void banUser(String username);

    void banUser(String username, int length);

    void banUser(User user);

    void banUser(User user, int length);

}
