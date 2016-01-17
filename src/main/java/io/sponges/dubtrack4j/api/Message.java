package io.sponges.dubtrack4j.api;

public class Message {

    private final User user;
    private final Room room;
    private final long time;
    private final String content;

    public Message(User user, Room room, long time, String content) {
        this.user = user;
        this.room = room;
        this.time = time;
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public long getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
