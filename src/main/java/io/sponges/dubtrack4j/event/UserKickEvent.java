package io.sponges.dubtrack4j.event;

import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.event.framework.Event;
import io.sponges.dubtrack4j.framework.User;

public class UserKickEvent implements Event {

    /*
    Not implemented as trying to find method to get the room id from user-kick real time callback.
     */

    private final User kicker, kicked;
    private final Room room;

    public UserKickEvent(User kicker, User kicked, Room room) {
        this.kicker = kicker;
        this.kicked = kicked;
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public User getKicker() {
        return kicker;
    }

    public User getKicked() {
        return kicked;
    }
}
