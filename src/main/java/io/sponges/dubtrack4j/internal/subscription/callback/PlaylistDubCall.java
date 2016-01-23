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

package io.sponges.dubtrack4j.internal.subscription.callback;

import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.event.UserDubEvent;
import io.sponges.dubtrack4j.framework.DubType;
import io.sponges.dubtrack4j.framework.User;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import io.sponges.dubtrack4j.internal.impl.SongImpl;
import org.json.JSONObject;

import java.io.IOException;

public class PlaylistDubCall extends SubCallback {

    private final DubtrackAPIImpl dubtrack;

    public PlaylistDubCall(DubtrackAPIImpl dubtrack) {
        this.dubtrack = dubtrack;
    }

    @Override
    public void run(JSONObject json) throws IOException {
        String username = json.getJSONObject("user").getString("username");
        String userId = json.getJSONObject("user").getString("_id");
        String roomId = json.getJSONObject("playlist").getString("roomid");

        int currentUps = json.getJSONObject("playlist").getInt("updubs");
        int currentDowns = json.getJSONObject("playlist").getInt("downdubs");

        DubType type = DubType.valueOf(json.getString("dubtype").toUpperCase());

        RoomImpl room = dubtrack.loadRoom(roomId);
        User user = room.loadUser(userId, username);
        SongImpl song = (SongImpl) room.getCurrent();
        if (song == null) return;

        song.setUpdubs(currentUps);
        song.setDowndubs(currentDowns);

        dubtrack.getEventBus().post(new UserDubEvent(song, user, room, type));
    }

}
