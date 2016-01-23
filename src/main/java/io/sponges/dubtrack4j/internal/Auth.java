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

import io.sponges.dubtrack4j.internal.request.Requester;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;

public class Auth {

    private final DubtrackAPIImpl dubtrack;
    private final String username, password;

    private String sid = null;

    public Auth(DubtrackAPIImpl dubtrack, String username, String password) throws IOException {
        this.dubtrack = dubtrack;
        this.username = username;
        this.password = password;
    }

    public void login() throws IOException {
        Requester requester = dubtrack.getHttpRequester();

        Response response = requester.post(URL.AUTH.toString(), new HashMap<String, String>() {{
            put("username", username);
            put("password", password);
        }});

        String cookies = response.header("Set-Cookie");

        boolean foundSid = false;

        List<HttpCookie> httpCookies = HttpCookie.parse(cookies);
        for (HttpCookie cookie : httpCookies) {
            if (cookie.getName().equalsIgnoreCase("connect.sid")) {
                sid = cookie.getValue();
                foundSid = true;
                break;
            }
        }

        if (!foundSid) {
            Logger.warning("No connection sid in cookie?");
        } else {
            Logger.debug("Found sid! " + sid);
        }
    }

    public String getSid() {
        return sid;
    }

}
