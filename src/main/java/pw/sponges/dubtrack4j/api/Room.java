package pw.sponges.dubtrack4j.api;

import pw.sponges.dubtrack4j.Dubtrack;

import java.util.Map;

public interface Room {

    String getUrl();

    String getId();

    Map<String, User> getUsers();

    User getUserByUsername(String username);

    User getUserById(String id);

    Song getCurrent();

    void setCurrent(Song current);

    void sendMessage(String message);

    User loadUser(String id, String username);

    User loadUser(Dubtrack dubtrack, String id);



}
