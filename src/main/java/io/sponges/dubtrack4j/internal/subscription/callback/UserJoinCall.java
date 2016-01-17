package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.DubtrackAPI;
import org.json.JSONObject;
import io.sponges.dubtrack4j.api.Room;
import io.sponges.dubtrack4j.api.User;
import io.sponges.dubtrack4j.event.UserJoinEvent;

public class UserJoinCall extends SubCallback {

    private DubtrackAPI dubtrack;

    public UserJoinCall(DubtrackAPI dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) {
        String username = json.getJSONObject("user").getString("username");
        String userId = json.getJSONObject("user").getString("_id");
        String roomId = json.getJSONObject("roomUser").getString("roomid");

        Room room = dubtrack.loadRoom(roomId);
        User user = room.loadUser(userId, username);

        dubtrack.getEventManager().handle(new UserJoinEvent(user, room));
    }
}
