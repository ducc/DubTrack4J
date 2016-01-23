package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.framework.*;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import io.sponges.dubtrack4j.internal.impl.SongImpl;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;
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

    public Room request() throws IOException {
        // Geting the room info
        Response response = dubtrack.getHttpRequester().get(URL.JOIN_ROOM + name);

        String str = response.body().string();
        Logger.debug("JOIN " + str);
        JSONObject json = new JSONObject(str);
        JSONObject data = json.getJSONObject("data");
        String id = data.getString("_id");

        // Actually joining the room
        String url = URL.JOIN_ROOM + id + "/users";
        dubtrack.getHttpRequester().post(url);

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
