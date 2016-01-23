package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
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
