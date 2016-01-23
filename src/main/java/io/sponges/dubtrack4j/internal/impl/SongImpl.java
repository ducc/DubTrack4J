package io.sponges.dubtrack4j.internal.impl;

import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.framework.*;
import io.sponges.dubtrack4j.internal.request.SkipSongRequest;
import io.sponges.dubtrack4j.internal.request.SongDubRequest;

import java.io.IOException;

public class SongImpl implements Song {

    private final DubtrackAPIImpl dubtrack;

    private final String id;
    private final User user;
    private final Room room;
    private final SongInfo songInfo;

    private int updubs, downdubs = 0; // TODO make these atomic?

    public SongImpl(DubtrackAPIImpl dubtrack, String id, User user, Room room, SongInfo songInfo) {
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
    public int getDowndubs() {
        return downdubs;
    }

    // setters not in interface to avoid confusion in the API
    public void setUpdubs(int updubs) {
        this.updubs = updubs;
    }

    public void setDowndubs(int downdubs) {
        this.downdubs = downdubs;
    }

    @Override
    public void updub() {
        try {
            new SongDubRequest(room.getId(), DubType.UPDUB, dubtrack).request();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downdub() {
        try {
            new SongDubRequest(room.getId(), DubType.DOWNDUB, dubtrack).request();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void skip() {
        try {
            new SkipSongRequest(room.getId(), room.getPlaylistId(), dubtrack).request();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
