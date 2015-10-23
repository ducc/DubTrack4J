package pw.sponges.dubtrack4j.internal.subscription.callback;

import org.json.JSONObject;
import pw.sponges.dubtrack4j.Dubtrack;
import pw.sponges.dubtrack4j.util.Logger;

public class UserKickCall extends SubCallback {

    private Dubtrack dubtrack;

    public UserKickCall(Dubtrack dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) {
        Logger.debug(json.toString());

        //String roomId =

        //Room room = dubtrack.loadRoom(roomId);

        //User kicker = null;
        //User kicked = null;
    }
}
