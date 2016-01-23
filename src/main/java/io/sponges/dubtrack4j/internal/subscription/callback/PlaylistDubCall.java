package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.event.UserDubEvent;
import io.sponges.dubtrack4j.framework.DubType;
import io.sponges.dubtrack4j.framework.User;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import io.sponges.dubtrack4j.internal.impl.SongImpl;
import org.json.JSONObject;

import java.io.IOException;

public class PlaylistDubCall extends SubCallback {

    private final DubtrackAPIImpl dubtrack;

    public PlaylistDubCall(DubtrackAPIImpl dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) throws IOException {
        String username = json.getJSONObject("user").getString("username");
        String userId = json.getJSONObject("user").getString("_id");
        String roomId = json.getJSONObject("playlist").getString("roomid");

        int currentUps = json.getJSONObject("playlist").getInt("updubs");
        int currentDowns = json.getJSONObject("playlist").getInt("downdubs");

        DubType type = DubType.valueOf(json.getString("dubtype").toUpperCase());

        RoomImpl room = dubtrack.loadRoom(roomId);
        User user = room.loadUser(userId, username);
        SongImpl song = (SongImpl) room.getCurrent();
        if (song == null) return;

        song.setUpdubs(currentUps);
        song.setDowndubs(currentDowns);

        dubtrack.getEventManager().handle(new UserDubEvent(song, user, room, type));
    }

}
