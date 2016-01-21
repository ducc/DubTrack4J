package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.framework.*;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import io.sponges.dubtrack4j.internal.impl.SongImpl;
import io.sponges.dubtrack4j.util.ContentType;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

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

    // TODO redo this request to use OkHttp, it will need auth token that we don't have
    public Room request2() throws IOException {
        Connection.Response r = Jsoup.connect(URL.JOIN_ROOM + name)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .execute();

        String str = r.body();

        Logger.debug("JOIN " + r.body());

        JSONObject json = new JSONObject(str);
        JSONObject data = json.getJSONObject("data");
        String id = data.getString("_id");

        String url = URL.JOIN_ROOM + id + "/users";

        Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 DubTrack4J")
                .method(Connection.Method.POST)
                .cookie("connect.sid", account.getToken())
                .execute();

        RoomImpl room = (RoomImpl) dubtrack.getRoom(id);
        if (room == null) {
            dubtrack.getRooms().put(id, new RoomImpl(dubtrack, data.getString("roomUrl"), id));
            room = (RoomImpl) dubtrack.getRoom(id);
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

            userId = json.getJSONObject("data").getString("userid");
            user = room.loadUser(dubtrack, userId);

            JSONObject songInfoRequest = new SongInfoRequest(dubtrack, songId, dubtrack.getAccount()).request();
            Logger.debug("SONGINFO " + songInfoRequest.toString());

            songLength = songInfoRequest.getJSONObject("data").getLong("songLength");
            songInfo = new SongInfo(songName, songLength);
        } catch (JSONException e) {
            if (e.getMessage().equalsIgnoreCase("JSONObject[\"currentSong\"] is not a JSONObject.")) {
                Logger.debug("currentSong is null");
            } else e.printStackTrace();
        }

        Song current = new SongImpl(dubtrack, songId, user, room, songInfo);
        room.setCurrent(current);

        // TODO change from new thread to executor service
        // updating the updub stats - delay 1s so the framework loads
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                new SongDubRequest(id, DubType.UPDUB, dubtrack).request();
            } catch (IOException ignored) {}
        }).start();

        return room;
    }

    public Room request() throws IOException {
        // Geting the room info
        Request request = new Request.Builder()
                .url(URL.JOIN_ROOM + name)
                .addHeader("Content-Type", ContentType.JSON)
                .get()
                .build();

        Response response = dubtrack.getHttpClient().newCall(request).execute();
        String str = response.body().string();
        Logger.debug("JOIN " + str);
        JSONObject json = new JSONObject(str);
        JSONObject data = json.getJSONObject("data");
        String id = data.getString("_id");

        // Actually joining the room
        String url = URL.JOIN_ROOM + id + "/users";
        RequestBody body = RequestBody.create(null, new byte[0]);
        Request request2 = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", ContentType.JSON)
                .addHeader("Content-Length", "0")
                .post(body)
                .build();
        dubtrack.getHttpClient().newCall(request2).execute();

        RoomImpl room = (RoomImpl) dubtrack.getRoom(id);
        if (room == null) {
            dubtrack.getRooms().put(id, new RoomImpl(dubtrack, data.getString("roomUrl"), id));
            room = (RoomImpl) dubtrack.getRoom(id);
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

            userId = json.getJSONObject("data").getString("userid");
            user = room.loadUser(dubtrack, userId);

            JSONObject songInfoRequest = new SongInfoRequest(dubtrack, songId, dubtrack.getAccount()).request();
            Logger.debug("SONGINFO " + songInfoRequest.toString());

            songLength = songInfoRequest.getJSONObject("data").getLong("songLength");
            songInfo = new SongInfo(songName, songLength);
        } catch (JSONException e) {
            if (e.getMessage().equalsIgnoreCase("JSONObject[\"currentSong\"] is not a JSONObject.")) {
                Logger.debug("currentSong is null");
            } else e.printStackTrace();
        }

        Song current = new SongImpl(dubtrack, songId, user, room, songInfo);
        room.setCurrent(current);

        // TODO change from new thread to executor service
        // updating the updub stats - delay 1s so the framework loads
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                new SongDubRequest(id, DubType.UPDUB, dubtrack).request();
            } catch (IOException ignored) {}
        }).start();

        return room;
    }

}
