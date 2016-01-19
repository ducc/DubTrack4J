package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.DubtrackAPI;
import io.sponges.dubtrack4j.util.Logger;
import org.json.JSONObject;

public class ChatSkipCall extends SubCallback {

    private final DubtrackAPI dubtrack;

    public ChatSkipCall(DubtrackAPI dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) {
        // TODO implement chat skip
        Logger.debug("CHAT SKIP " + json.toString());
    }

}
