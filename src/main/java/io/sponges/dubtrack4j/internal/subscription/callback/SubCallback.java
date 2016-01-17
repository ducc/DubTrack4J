package io.sponges.dubtrack4j.internal.subscription.callback;

import org.json.JSONObject;

public abstract class SubCallback {

    // TODO implement double message prevention

    public abstract void run(JSONObject json);

}
