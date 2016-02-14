/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Joe Burnard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
        this.pubnub.setUUID(dubtrack.getAccount().getUuid());

        this.pubnub.subscribe("dubtrackfm-" + room, new SubscriptionCallback(dubtrack));
    }
}
