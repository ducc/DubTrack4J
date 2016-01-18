package io.sponges.dubtrack4j.event;

import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;
import io.sponges.dubtrack4j.event.framework.Event;

public class UserLeaveEvent implements Event {

    private final User user;
    private final Room room;

    public UserLeaveEvent(User user, Room room) {
        this.user = user;
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }
}
