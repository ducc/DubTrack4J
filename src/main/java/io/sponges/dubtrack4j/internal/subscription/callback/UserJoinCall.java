package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.event.UserJoinEvent;
import io.sponges.dubtrack4j.framework.User;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import org.json.JSONObject;

import java.io.IOException;

public class UserJoinCall extends SubCallback {

    private final DubtrackAPIImpl dubtrack;

    public UserJoinCall(DubtrackAPIImpl dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) throws IOException {
        String username = json.getJSONObject("user").getString("username");
        String userId = json.getJSONObject("user").getString("_id");
        String roomId = json.getJSONObject("roomUser").getString("roomid");

        RoomImpl room = dubtrack.loadRoom(roomId);
        User user = room.loadUser(userId, username);

        dubtrack.getEventManager().handle(new UserJoinEvent(user, room));
    }
}
