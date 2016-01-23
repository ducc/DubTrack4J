package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class RoomPlaylistRequest implements DubRequest {

    private final String room;
    private final DubtrackAPIImpl dubtrack;
    private final DubAccount account;

    public RoomPlaylistRequest(String room, DubtrackAPIImpl dubtrack) throws IOException {
        this.room = room;
        this.dubtrack = dubtrack;
        this.account = dubtrack.getAccount();
    }

    public JSONObject request() throws IOException {
        Response response = dubtrack.getHttpRequester().get(String.format(URL.ROOM_PLAYLIST.toString(), room));

        String r = response.body().string();
        Logger.debug(r);

        return new JSONObject(r);
    }

}
