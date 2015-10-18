package pw.sponges.dubtrack4j.event;

import pw.sponges.dubtrack4j.api.Room;
import pw.sponges.dubtrack4j.api.Song;
import pw.sponges.dubtrack4j.event.framework.Event;

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
