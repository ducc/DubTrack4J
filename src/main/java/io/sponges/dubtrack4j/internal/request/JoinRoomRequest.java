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
import io.sponges.dubtrack4j.framework.*;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import io.sponges.dubtrack4j.internal.impl.SongImpl;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class JoinRoomRequest implements DubRequest {

    private final DubtrackAPIImpl dubtrack;
    private final String name;
    private final DubAccount account;

    public JoinRoomRequest(DubtrackAPIImpl dubtrack, String name, DubAccount account) throws IOException {
        this.dubtrack = dubtrack;
        this.name = name;
        this.account = account;
    }

    @Override
    public JSONObject request() throws JSONException, IOException {
        Response response = dubtrack.getHttpRequester().get(URL.JOIN_ROOM + name);

        ResponseBody body = response.body();
        String result = body.string();
        body.close();

        Logger.debug("JOIN ROOM " + result);

        return new JSONObject(result);
    }

    public Room getRoom(JSONObject json) throws IOException {
        JSONObject data = json.getJSONObject("data");
        String id = data.getString("_id");

        // Actually joining the room
        String url = URL.JOIN_ROOM + id + "/users";
        dubtrack.getHttpRequester().post(url);

        RoomImpl room = (RoomImpl) dubtrack.getRoom(id);
        if (room == null) {
            dubtrack.addRoom(id, new RoomImpl(dubtrack, data.getString("roomUrl"), id));
            room = (RoomImpl) dubtrack.getRoom(id);

            String creatorId = data.getString("userid");
            User creator = room.getOrLoadUser(creatorId);
            room.setCreator(creator);
        }

        JSONObject currentSong;
        String songId = null;
        String songName;
        long songLength;
        String userId;
        User user = null;
        SongInfo songInfo = null;

        try {
            currentSong = json.getJSONObject("data").getJSONObject("currentSong");
            songId = currentSong.getString("songid");
            songName = currentSong.getString("name");

            String type = currentSong.getString("type").toLowerCase();
            SongInfo.SourceType sourceType = SongInfo.SourceType.valueOf(type.toUpperCase());

            String sourceId = currentSong.getString("fkid");

            //userId = json.getJSONObject("data").getString("userid"); // this is actually the room owner id TODO do something with this
            JSONObject songInfoRequest = new SongInfoRequest(dubtrack, songId, dubtrack.getAccount()).request();
            Logger.debug("SONGINFO " + songInfoRequest.toString());

            JSONObject songData = songInfoRequest.getJSONObject("data");
            songLength = songData.getLong("songLength");
            songInfo = new SongInfo(songName, songLength, sourceType);

            if (sourceType == SongInfo.SourceType.YOUTUBE) songInfo.setYoutubeId(sourceId);
            else if (sourceType == SongInfo.SourceType.SOUNDCLOUD) songInfo.setSoundcloudId(sourceId);

            // TODO get more info from this request
            RoomPlaylistRequest playlistRequest = new RoomPlaylistRequest(id, dubtrack);
            JSONObject playlistJson = playlistRequest.request();
            JSONObject playlistData = playlistJson.getJSONObject("data");
            JSONObject playlistSong = playlistData.getJSONObject("song");
            Logger.debug("PLAYLIST REQUEST " + playlistJson.toString());

            userId = playlistSong.getString("_user");
            user = room.getOrLoadUser(userId);
        } catch (JSONException e) {
            if (e.getMessage().equalsIgnoreCase("JSONObject[\"currentSong\"] is not a JSONObject.")) {
                Logger.debug("currentSong is null");
            } else {
                throw e;
            }
        }

        Song current = new SongImpl(dubtrack, songId, user, room, songInfo);
        room.setCurrent(current);

        // TODO find a better way of getting current song votes
        // updating the updub stats - delay 1s so the framework loads
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // TODO replace this with getting the updubs and downdubs instead of voting...
            try {
                new SongDubRequest(id, DubType.UPDUB, dubtrack).request();
            } catch (IOException ignored) {}
        }).start();

        return room;
    }

}
