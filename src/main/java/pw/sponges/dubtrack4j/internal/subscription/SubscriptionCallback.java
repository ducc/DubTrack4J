package pw.sponges.dubtrack4j.internal.subscription;

import com.pubnub.api.Callback;
import com.pubnub.api.PubnubError;
import org.json.JSONObject;
import pw.sponges.dubtrack4j.Dubtrack;
import pw.sponges.dubtrack4j.internal.subscription.callback.*;
import pw.sponges.dubtrack4j.util.Logger;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionCallback extends Callback {

    private Dubtrack dubtrack;
    private Map<String, SubCallback> callbacks;

    public SubscriptionCallback(Dubtrack dubtrack) {
        this.dubtrack = dubtrack;
        this.callbacks = new HashMap<String, SubCallback>() {{
            put("chat-message", new ChatMessageCall(dubtrack));
            put("user-join", new UserJoinCall(dubtrack));
            put("user-leave", new UserLeaveCall(dubtrack));
            put("room_playlist-update", new PlaylistUpdateCall(dubtrack));
            put("room_playlist-dub", new PlaylistDubCall(dubtrack));
            put("user_update", new UserUpdateCall(dubtrack));
            put("user-kick", new UserKickCall(dubtrack));
        }};
    }

    @Override
    public void disconnectCallback(String s, Object o) {
        Logger.debug("disconnect " + s + " " + o);
    }

    @Override
    public void successCallback(String s, Object o) {
        Logger.debug(false, "success1 " + s + " " + o);

        JSONObject json = new JSONObject(o.toString());
        String type = json.getString("type");

        if (type.startsWith("user_update_")) type = "user_update";

        if (!callbacks.containsKey(type)) {
            Logger.debug(true, "Invalid callback type " + type);
            return;
        }

        callbacks.get(type).run(json);
    }

    @Override
    public void successCallback(String s, Object o, String s1) {
        Logger.debug("success2 " + s + " " + o + " " + s1);
    }

    @Override
    public void errorCallback(String s, PubnubError pubnubError) {
        Logger.debug("error " + s + " " + pubnubError.getErrorString());
    }

    @Override
    public void errorCallback(String s, Object o) {
        Logger.debug("error " + s + " " + o);
    }

    @Override
    public void connectCallback(String s, Object o) {
        Logger.debug("connect " + s + " " + o);
    }

    @Override
    public void reconnectCallback(String s, Object o) {
        Logger.debug("reconnect " + s + " " + o);
    }

}
