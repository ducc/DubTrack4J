package pw.sponges.dubtrack4j.api;

import pw.sponges.dubtrack4j.Dubtrack;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private Dubtrack dubtrack;
    private final String url, id;
    private Song current = null;

    //              id
    private Map<String, User> users;

    public Room(Dubtrack dubtrack, String url, String id) {
        this.dubtrack = dubtrack;
        this.url = url;
        this.id = id;

        this.users = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public User getUserByUsername(String username) {
        for (User user : users.values()) {
            if (user.getName().equalsIgnoreCase(username)) {
                return user;
            }
        }

        return null;
    }

    public User getUserById(String id) {
        return users.get(id);
    }

    public Song getCurrent() {
        return current;
    }

    public void setCurrent(Song current) {
        this.current = current;
    }

    public void sendMessage(String message) {
        dubtrack.sendMessage(this, message);
    }

}
