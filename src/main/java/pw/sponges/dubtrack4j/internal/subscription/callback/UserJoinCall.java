package pw.sponges.dubtrack4j.internal.subscription.callback;

import org.json.JSONObject;
import pw.sponges.dubtrack4j.Dubtrack;
import pw.sponges.dubtrack4j.api.Room;
import pw.sponges.dubtrack4j.api.User;
import pw.sponges.dubtrack4j.event.UserJoinEvent;

public class UserJoinCall extends SubCallback {

    private Dubtrack dubtrack;

    public UserJoinCall(Dubtrack dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) {
        String username = json.getJSONObject("user").getString("username");
        String userId = json.getJSONObject("user").getString("_id");
        String roomId = json.getJSONObject("roomUser").getString("roomid");

        Room room = dubtrack.getRoom(dubtrack, roomId);
        User user = dubtrack.getUser(room, userId, username);

        dubtrack.getEventManager().handle(new UserJoinEvent(user, room));
    }
}
