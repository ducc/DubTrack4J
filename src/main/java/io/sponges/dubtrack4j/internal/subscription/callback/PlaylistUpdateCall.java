package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.DubtrackAPI;
import org.json.JSONObject;
import io.sponges.dubtrack4j.api.Room;
import io.sponges.dubtrack4j.api.Song;
import io.sponges.dubtrack4j.api.SongInfo;
import io.sponges.dubtrack4j.api.User;
import io.sponges.dubtrack4j.event.SongChangeEvent;
import io.sponges.dubtrack4j.internal.impl.SongImpl;

public class PlaylistUpdateCall extends SubCallback {

    private DubtrackAPI dubtrack;

    public PlaylistUpdateCall(DubtrackAPI dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) {
        String userId = json.getJSONObject("song").getString("userid");
        String roomId = json.getJSONObject("song").getString("roomid");
        long time = json.getJSONObject("song").getLong("created");

        String songId = json.getJSONObject("song").getString("songid");
        String songName = json.getJSONObject("songInfo").getString("name");
        long songLength = json.getJSONObject("songInfo").getLong("songLength");

        Room room = dubtrack.loadRoom(roomId);
        User user = room.loadUser(dubtrack, userId);

        SongInfo songInfo = new SongInfo(songName, songLength);
        Song song = new SongImpl(dubtrack, songId, user, room, songInfo);

        Song previous = room.getCurrent();
        room.setCurrent(song);

        dubtrack.getEventManager().handle(new SongChangeEvent(previous, song, room));
    }

}
