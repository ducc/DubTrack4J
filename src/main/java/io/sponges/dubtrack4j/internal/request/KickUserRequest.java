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

package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.internal.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPI;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import io.sponges.dubtrack4j.util.URL;

import java.io.IOException;

public class KickUserRequest implements DubRequest {

    private final DubtrackAPI dubtrack;
    private final DubAccount account;

    private final String roomId, roomName, userId;

    public KickUserRequest(DubtrackAPI dubtrack, DubAccount account, String roomId, String roomName, String userId) throws IOException {
        this.dubtrack = dubtrack;
        this.account = account;
        this.roomId = roomId;
        this.roomName = roomName;
        this.userId = userId;
    }

    @Override
    public JSONObject request() throws IOException {
        Connection.Response r = Jsoup.connect(String.format(URL.KICK_USER.toString(), roomId, userId))
                .userAgent("Mozilla/5.0 DubTrack4J")
                .header("realTimeChannel", "dubtrackfm-" + roomName)
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                //.cookie("connect.sid", account.getToken())
                .execute();

        String str = r.body();

        return new JSONObject(str);
    }

}
