package pw.sponges.dubtrack4j.internal.subscription.callback;

import org.json.JSONObject;
import pw.sponges.dubtrack4j.Dubtrack;
import pw.sponges.dubtrack4j.util.Logger;

public class UserUpdateCall extends SubCallback {

    private Dubtrack dubtrack;

    public UserUpdateCall(Dubtrack dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) {
        Logger.debug(true, "USER UPDATE" + json.toString());
    }

}
