package pw.sponges.dubtrack4j.internal.subscription.callback;

import org.json.JSONObject;
import pw.sponges.dubtrack4j.Dubtrack;
import pw.sponges.dubtrack4j.api.Room;
import pw.sponges.dubtrack4j.api.Song;
import pw.sponges.dubtrack4j.api.SongInfo;
import pw.sponges.dubtrack4j.api.User;
import pw.sponges.dubtrack4j.event.SongChangeEvent;

public class PlaylistUpdateCall extends SubCallback {

    private Dubtrack dubtrack;

    public PlaylistUpdateCall(Dubtrack dubtrack) {
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

        Room room = dubtrack.getRoom(dubtrack, roomId);
        User user = dubtrack.getUser(dubtrack, room, userId);

        SongInfo songInfo = new SongInfo(songName, songLength);
        Song song = new Song(songId, user, room, songInfo);

        Song previous = room.getCurrent();
        room.setCurrent(song);

        dubtrack.getEventManager().handle(new SongChangeEvent(previous, song, room));
    }

}
