package pw.sponges.dubtrack4j.internal.impl;

import pw.sponges.dubtrack4j.Dubtrack;
import pw.sponges.dubtrack4j.api.Room;
import pw.sponges.dubtrack4j.api.Song;
import pw.sponges.dubtrack4j.api.SongInfo;
import pw.sponges.dubtrack4j.api.User;

public class SongImpl implements Song {

    private Dubtrack dubtrack;

    private final String id;
    private final User user;
    private final Room room;
    private final SongInfo songInfo;

    private int updubs, downdubs = 0;

    public SongImpl(Dubtrack dubtrack, String id, User user, Room room, SongInfo songInfo) {
        this.dubtrack = dubtrack;
        this.id = id;
        this.user = user;
        this.room = room;
        this.songInfo = songInfo;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Room getRoom() {
        return room;
    }

    @Override
    public SongInfo getSongInfo() {
        return songInfo;
    }

    @Override
    public int getUpdubs() {
        return updubs;
    }

    @Override
    public void setUpdubs(int updubs) {
        this.updubs = updubs;
    }

    @Override
    public int getDowndubs() {
        return downdubs;
    }

    @Override
    public void setDowndubs(int downdubs) {
        this.downdubs = downdubs;
    }

    @Override
    public void updub() {
        dubtrack.updub(this);
    }

    @Override
    public void downdub() {
        dubtrack.downdub(this);
    }

}
