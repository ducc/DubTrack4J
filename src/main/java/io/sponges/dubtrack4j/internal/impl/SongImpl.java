package io.sponges.dubtrack4j.internal.impl;

import io.sponges.dubtrack4j.DubtrackAPI;
import io.sponges.dubtrack4j.framework.*;
import io.sponges.dubtrack4j.internal.request.SkipSongRequest;
import io.sponges.dubtrack4j.internal.request.SongDubRequest;
import io.sponges.dubtrack4j.util.Logger;
import org.jsoup.Connection;

import java.io.IOException;

public class SongImpl implements Song {

    private final DubtrackAPI dubtrack;

    private final String id;
    private final User user;
    private final Room room;
    private final SongInfo songInfo;

    private int updubs, downdubs = 0;

    public SongImpl(DubtrackAPI dubtrack, String id, User user, Room room, SongInfo songInfo) {
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
        try {
            Connection.Response r = new SongDubRequest(room.getId(), DubType.UPDUB, dubtrack.getAccount()).request();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downdub() {
        try {
            Connection.Response r = new SongDubRequest(room.getId(), DubType.DOWNDUB, dubtrack.getAccount()).request();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void skip() {
        try {
            Connection.Response r = new SkipSongRequest(room.getId(), room.getPlaylistId(), dubtrack.getAccount()).request();
            Logger.debug("Skip response: " + r.statusCode() + " - " + r.statusMessage() + "\n" + r.body() + "\n" + r.headers() + "\n" + r.cookies());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
