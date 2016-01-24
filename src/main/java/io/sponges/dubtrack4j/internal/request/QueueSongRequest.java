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

import io.sponges.dubtrack4j.framework.SongInfo;
import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class QueueSongRequest implements DubRequest {

    private final DubtrackAPIImpl dubtrack;
    private final String room;
    private final SongInfo.SourceType type;
    private final String id;

    public QueueSongRequest(DubtrackAPIImpl dubtrack, String room, SongInfo.SourceType type, String id) {
        this.dubtrack = dubtrack;
        this.room = room;
        this.type = type;
        this.id = id;
    }

    @Override
    public JSONObject request() throws IOException {
        String sourceType = type.name().toLowerCase();
        String url = String.format(URL.QUEUE_SONG.toString(), room);

        Response response = dubtrack.getHttpRequester().post(url, new HashMap<String, String>() {{
            put("songId", id);
            put("songType", sourceType);
        }});

        return new JSONObject(response.body().string());
    }

}
