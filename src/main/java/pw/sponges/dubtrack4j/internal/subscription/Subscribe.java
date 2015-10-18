package pw.sponges.dubtrack4j.internal.subscription;

import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;
import pw.sponges.dubtrack4j.Dubtrack;

public class Subscribe {

    public static final String SUBSCRIPTION_KEY = "sub-c-2b40f72a-6b59-11e3-ab46-02ee2ddab7fe";

    private Dubtrack dubtrack;
    private Pubnub pubnub;

    public Subscribe(Dubtrack dubtrack, String room) throws PubnubException {
        this.dubtrack = dubtrack;
        this.pubnub = new Pubnub(null, SUBSCRIPTION_KEY);

        this.pubnub.subscribe("dubtrackfm-" + room, new SubscriptionCallback(dubtrack));
    }
}
