package io.sponges.dubtrack4j.framework;

import java.util.Map;

public interface Room {

    // TODO change to getName
    String getUrl();

    String getId();

    String getPlaylistId();

    Map<String, User> getUsers();

    User getUserByUsername(String username);

    User getUserById(String id);

    Song getCurrent();

    void setCurrent(Song current);

    void sendMessage(String message);

    void kickUser(String username);

    void kickUser(User user);

    void banUser(String username);

    void banUser(String username, int length);

    void banUser(User user);

    void banUser(User user, int length);

}
