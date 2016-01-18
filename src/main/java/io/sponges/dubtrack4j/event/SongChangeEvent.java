package io.sponges.dubtrack4j.event;

import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.Song;
import io.sponges.dubtrack4j.event.framework.Event;

public class SongChangeEvent implements Event {

    private final Song lastSong, newSong;
    private final Room room;

    public SongChangeEvent(Song lastSong, Song newSong, Room room) {
        this.lastSong = lastSong;
        this.newSong = newSong;
        this.room = room;
    }

    public Song getLastSong() {
        return lastSong;
    }

    public Song getNewSong() {
        return newSong;
    }

    public Room getRoom() {
        return room;
    }
}
