package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.event.UserDubEvent;
import io.sponges.dubtrack4j.framework.DubType;
import io.sponges.dubtrack4j.framework.Song;
import io.sponges.dubtrack4j.framework.User;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import io.sponges.dubtrack4j.util.Logger;
import org.json.JSONObject;

public class PlaylistDubCall extends SubCallback {

    private final DubtrackAPIImpl dubtrack;

    public PlaylistDubCall(DubtrackAPIImpl dubtrack) {
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

        RoomImpl room = dubtrack.loadRoom(roomId);
        User user = room.loadUser(userId, username);
        Song song = room.getCurrent();
        if (song == null) return;

        song.setUpdubs(currentUps);
        song.setDowndubs(currentDowns);

        //if (type == UserDubEvent.DubType.UPDUB) song.addUpdub();
        //else song.addDowndub();

        dubtrack.getEventManager().handle(new UserDubEvent(song, user, room, type));
    }

}
