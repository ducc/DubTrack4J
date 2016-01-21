package io.sponges.dubtrack4j.internal.subscription;

import com.pubnub.api.Callback;
import com.pubnub.api.PubnubError;
import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.internal.subscription.callback.*;
import io.sponges.dubtrack4j.util.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionCallback extends Callback {

    private final DubtrackAPIImpl dubtrack;
    private final Map<String, SubCallback> callbacks;

    SubscriptionCallback(DubtrackAPIImpl dubtrack) {
        this.dubtrack = dubtrack;
        this.callbacks = new HashMap<String, SubCallback>() {{
            put("chat-message", new ChatMessageCall(dubtrack));
            put("user-join", new UserJoinCall(dubtrack));
            put("user-leave", new UserLeaveCall(dubtrack));
            put("room_playlist-update", new PlaylistUpdateCall(dubtrack));
            put("room_playlist-dub", new PlaylistDubCall(dubtrack));
            put("user_update", new UserUpdateCall(dubtrack));
            put("user-kick", new UserKickCall(dubtrack));
            put("chat-skip", new ChatSkipCall(dubtrack));
        }};
    }

    @Override
    public void disconnectCallback(String s, Object o) {
        Logger.debug("PubNub disconnect: " + s + " " + o);
    }

    @Override
    public void successCallback(String s, Object o) {
        JSONObject json = new JSONObject(o.toString());
        String type = json.getString("type");

        Logger.debug(type.toUpperCase() + ": " + json.toString());

        if (type.startsWith("user_update_")) type = "user_update";

        if (!callbacks.containsKey(type)) {
            Logger.debug("Invalid callback type " + type);
            return;
        }

        callbacks.get(type).run(json);
    }

    @Override
    public void successCallback(String s, Object o, String s1) {
        // TODO something to put in here
    }

    @Override
    public void errorCallback(String s, PubnubError pubnubError) {
        Logger.warning("PubNub error: " + s + " " + pubnubError.getErrorString());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void errorCallback(String s, Object o) {
        Logger.debug("Error: " + s + " " + o);
    }

    @Override
    public void connectCallback(String s, Object o) {
        Logger.debug("PubNub connect: " + s + " " + o);
    }

    @Override
    public void reconnectCallback(String s, Object o) {
        Logger.debug("PubNub reconnect: " + s + " " + o);
    }

}
