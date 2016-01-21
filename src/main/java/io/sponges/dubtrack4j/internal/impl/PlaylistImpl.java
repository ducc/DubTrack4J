package io.sponges.dubtrack4j.internal.impl;

import io.sponges.dubtrack4j.framework.Playlist;

public class PlaylistImpl implements Playlist {

    // TODO implementation of Playlist

    private final String id;

    public PlaylistImpl(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
