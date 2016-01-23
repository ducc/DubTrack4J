package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.event.UserLeaveEvent;
import io.sponges.dubtrack4j.framework.User;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import org.json.JSONObject;

import java.io.IOException;

public class UserLeaveCall extends SubCallback {

    private final DubtrackAPIImpl dubtrack;

    public UserLeaveCall(DubtrackAPIImpl dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) throws IOException {
        // I don't know if this actually works, it rarely gets the type user-leave

        String username = json.getJSONObject("user").getString("username");
        String userId = json.getJSONObject("user").getString("_id");
        String roomId = json.getJSONObject("roomUser").getString("roomid");

        RoomImpl room = dubtrack.loadRoom(roomId);
        User user = room.loadUser(userId, username);

        dubtrack.getEventBus().post(new UserLeaveEvent(user, room));
    }
}
