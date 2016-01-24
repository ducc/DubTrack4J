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
import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class SkipSongRequest implements DubRequest {

    private final String room, playlist;
    private final DubtrackAPIImpl dubtrack;
    private final DubAccount account;

    public SkipSongRequest(String room, String playlist, DubtrackAPIImpl dubtrack) throws IOException {
        this.room = room;
        this.playlist = playlist;
        this.dubtrack = dubtrack;
        this.account = dubtrack.getAccount();
    }

    @Override
    public JSONObject request() throws IOException {
        String url = String.format(URL.SKIP_SONG.toString(), room, playlist);
        Response response = dubtrack.getHttpRequester().post(String.format(URL.SKIP_SONG.toString(), room, playlist), new HashMap<String, String>() {{
            put("realTimeChannel", "dubtrackfm-" + room);
        }});

        String r = response.body().string();
        Logger.debug(r);

        return new JSONObject(r);
    }

}
