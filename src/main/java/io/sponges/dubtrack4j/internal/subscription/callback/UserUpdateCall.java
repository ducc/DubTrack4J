package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.DubtrackAPI;
import io.sponges.dubtrack4j.util.Logger;
import org.json.JSONObject;

public class UserUpdateCall extends SubCallback {

    private DubtrackAPI dubtrack;

    public UserUpdateCall(DubtrackAPI dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) {
        Logger.debug(true, "USER UPDATE" + json.toString());
    }

}
