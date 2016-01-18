package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.event.UserChatEvent;
import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;
import org.json.JSONObject;

public class ChatMessageCall extends SubCallback {

    private final DubtrackAPIImpl dubtrack;

    public ChatMessageCall(DubtrackAPIImpl dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) {
        String message = json.getString("message");
        String username = json.getJSONObject("user").getString("username");
        String userId = json.getJSONObject("user").getString("_id");
        String roomId = json.getJSONObject("queue_object").getString("roomid");
        long time = json.getJSONObject("queue_object").getLong("updated");

        Room room = dubtrack.loadRoom(roomId);
        User user = room.loadUser(userId, username);

        Message msg = new Message(user, room, time, message);
        dubtrack.getEventManager().handle(new UserChatEvent(msg));
    }

}
