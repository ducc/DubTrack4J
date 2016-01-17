package io.sponges.dubtrack4j.event;

import io.sponges.dubtrack4j.api.DubType;
import io.sponges.dubtrack4j.api.Room;
import io.sponges.dubtrack4j.api.Song;
import io.sponges.dubtrack4j.event.framework.Event;
import io.sponges.dubtrack4j.api.User;

public class UserDubEvent implements Event {

    private final Song song;
    private final User user;
    private final Room room;
    private final DubType type;

    public UserDubEvent(Song song, User user, Room room, DubType type) {
        this.song = song;
        this.user = user;
        this.room = room;
        this.type = type;
    }

    public Song getSong() {
        return song;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public DubType getType() {
        return type;
    }

}
