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

        RoomImpl room = dubtrack.loadRoom(roomId);
        User user = room.loadUser(dubtrack, userId);

        SongInfo sInfo = new SongInfo(songName, songLength);
        Song s = new SongImpl(dubtrack, songId, user, room, sInfo);

        Song previous = room.getCurrent();
        room.setCurrent(s);
        room.setPlaylistId(playlistId);

        dubtrack.getEventBus().post(new SongChangeEvent(previous, s, room));
    }

}
