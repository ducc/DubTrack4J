package pw.sponges.dubtrack4j.event;

import pw.sponges.dubtrack4j.api.DubType;
import pw.sponges.dubtrack4j.api.Room;
import pw.sponges.dubtrack4j.api.Song;
import pw.sponges.dubtrack4j.api.User;
import pw.sponges.dubtrack4j.framework.event.Event;

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
