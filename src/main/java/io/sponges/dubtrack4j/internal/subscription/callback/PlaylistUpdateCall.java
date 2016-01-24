/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Joe Burnard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.event.SongChangeEvent;
import io.sponges.dubtrack4j.framework.Song;
import io.sponges.dubtrack4j.framework.SongInfo;
import io.sponges.dubtrack4j.framework.User;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import io.sponges.dubtrack4j.internal.impl.SongImpl;
import org.json.JSONObject;

import java.io.IOException;

public class PlaylistUpdateCall extends SubCallback {

    private final DubtrackAPIImpl dubtrack;

    public PlaylistUpdateCall(DubtrackAPIImpl dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) throws IOException {
        JSONObject song = json.getJSONObject("song");
        JSONObject songInfo = json.getJSONObject("songInfo");

        String playlistId = song.getString("_id");
        String userId = song.getString("userid");
        String roomId = song.getString("roomid");
        long time = song.getLong("created");
        String songId = song.getString("songid");

        String songName = songInfo.getString("name");
        long songLength = songInfo.getLong("songLength");

        String sourceTypeId = songInfo.getString("type");
        SongInfo.SourceType sourceType = SongInfo.SourceType.valueOf(sourceTypeId.toUpperCase());
        String sourceId = songInfo.getString("fkid");

        RoomImpl room = dubtrack.loadRoom(roomId);
        User user = room.getOrLoadUser(userId);

        SongInfo sInfo = new SongInfo(songName, songLength, sourceType);
        if (sourceType == SongInfo.SourceType.YOUTUBE) {
            sInfo.setYoutubeId(sourceId);
        } else {
            sInfo.setSoundcloudId(sourceId);
        }

        Song s = new SongImpl(dubtrack, songId, user, room, sInfo);

        Song previous = room.getCurrentSong();
        room.setCurrent(s);
        room.setPlaylistId(playlistId);

        dubtrack.getEventBus().post(new SongChangeEvent(previous, s, room));
    }

}
