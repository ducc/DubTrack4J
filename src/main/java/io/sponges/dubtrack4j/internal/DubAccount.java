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

package io.sponges.dubtrack4j.internal;

import io.sponges.dubtrack4j.internal.request.SessionRequest;
import org.json.JSONObject;

import java.io.IOException;

public class DubAccount {

    private final DubtrackAPIImpl dubtrack;
    private final String username, password;

    private String token = null;
    private String uuid = null;

    public DubAccount(DubtrackAPIImpl dubtrack, String username, String password) {
        this.dubtrack = dubtrack;
        this.username = username;
        this.password = password;
    }

    /**
     * Attempts to login to dubtrack
     * @throws IOException
     */
    public void login() throws IOException {
        Auth auth = new Auth(dubtrack, username, password);
        auth.login();
        this.token = auth.getSid();

        JSONObject json = new SessionRequest(dubtrack).request();
        JSONObject data = json.getJSONObject("data");
        this.uuid = data.getString("_id");
    }

    public String getToken() {
        return token;
    }

    public String getUuid() {
        return uuid;
    }
}
