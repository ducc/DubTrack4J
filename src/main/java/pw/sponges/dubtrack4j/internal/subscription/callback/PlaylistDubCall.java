package pw.sponges.dubtrack4j.internal.subscription.callback;

import org.json.JSONObject;
import pw.sponges.dubtrack4j.Dubtrack;
import pw.sponges.dubtrack4j.api.DubType;
import pw.sponges.dubtrack4j.api.Room;
import pw.sponges.dubtrack4j.api.Song;
import pw.sponges.dubtrack4j.api.User;
import pw.sponges.dubtrack4j.event.UserDubEvent;
import pw.sponges.dubtrack4j.util.Logger;

public class PlaylistDubCall extends SubCallback {

    private Dubtrack dubtrack;

    public PlaylistDubCall(Dubtrack dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) {
        Logger.debug(false, json.toString());

        String username = json.getJSONObject("user").getString("username");
        String userId = json.getJSONObject("user").getString("_id");
        String roomId = json.getJSONObject("playlist").getString("roomid");

        int currentUps = json.getJSONObject("playlist").getInt("updubs");
        int currentDowns = json.getJSONObject("playlist").getInt("downdubs");

        DubType type = DubType.valueOf(json.getString("dubtype").toUpperCase());

        Room room = dubtrack.getRoom(dubtrack, roomId);
        User user = dubtrack.getUser(room, userId, username);
        Song song = room.getCurrent();
        if (song == null) return;

        song.setUpdubs(currentUps);
        song.setDowndubs(currentDowns);

        //if (type == UserDubEvent.DubType.UPDUB) song.addUpdub();
        //else song.addDowndub();

        dubtrack.getEventManager().handle(new UserDubEvent(song, user, room, type));
    }

}
