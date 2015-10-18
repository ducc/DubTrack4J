package pw.sponges.dubtrack4j.event;

import pw.sponges.dubtrack4j.api.Room;
import pw.sponges.dubtrack4j.api.User;
import pw.sponges.dubtrack4j.framework.event.Event;

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
