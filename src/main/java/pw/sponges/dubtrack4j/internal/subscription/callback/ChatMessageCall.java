package pw.sponges.dubtrack4j.internal.subscription.callback;

import org.json.JSONObject;
import pw.sponges.dubtrack4j.Dubtrack;
import pw.sponges.dubtrack4j.api.Message;
import pw.sponges.dubtrack4j.api.Room;
import pw.sponges.dubtrack4j.api.User;
import pw.sponges.dubtrack4j.event.UserChatEvent;

public class ChatMessageCall extends SubCallback {

    private Dubtrack dubtrack;

    public ChatMessageCall(Dubtrack dubtrack) {
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
