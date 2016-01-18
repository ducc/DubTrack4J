package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.DubtrackAPI;
import org.json.JSONObject;
import io.sponges.dubtrack4j.util.Logger;

public class UserKickCall extends SubCallback {

    private final DubtrackAPI dubtrack;

    public UserKickCall(DubtrackAPI dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) {
        Logger.debug(json.toString());

        // TODO implement user kick callback

        //String roomId =

        //Room room = dubtrack.loadRoom(roomId);

        //User kicker = null;
        //User kicked = null;
    }
}
