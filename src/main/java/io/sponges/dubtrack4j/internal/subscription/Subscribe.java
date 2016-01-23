package io.sponges.dubtrack4j.internal.subscription;

import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;
import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;

public class Subscribe {

    public static final String SUBSCRIPTION_KEY = "sub-c-2b40f72a-6b59-11e3-ab46-02ee2ddab7fe";

    private final DubtrackAPIImpl dubtrack;
    private final Pubnub pubnub;

    public Subscribe(DubtrackAPIImpl dubtrack, String room) throws PubnubException {
        this.dubtrack = dubtrack;
        this.pubnub = new Pubnub(null, SUBSCRIPTION_KEY);

        this.pubnub.subscribe("dubtrackfm-" + room, new SubscriptionCallback(dubtrack));
    }
}
