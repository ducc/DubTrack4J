package io.sponges.dubtrack4j.framework;

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

    /**
     * The user who sent the message
     * @return User instance
     */
    public User getUser() {
        return user;
    }

    /**
     * The room that the message was sent in
     * @return Room instance
     */
    public Room getRoom() {
        return room;
    }

    /**
     * The time that the message was sent at in milliseconds
     * @return message sent time
     */
    public long getTime() {
        return time;
    }

    /**
     * The content of the message
     * @return message content
     */
    public String getContent() {
        return content;
    }
}
