package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class SongInfoRequest implements DubRequest {

    private final DubtrackAPIImpl dubtrack;
    private final String id;
    private final DubAccount account;

    public SongInfoRequest(DubtrackAPIImpl dubtrack, String id, DubAccount account) throws IOException {
        this.dubtrack = dubtrack;
        this.id = id;
        this.account = account;
    }

    public JSONObject request() throws IOException {
        Response response = dubtrack.getHttpRequester().get(URL.SONG_INFO + id);
        String r = response.body().string();

        Logger.debug(r);

        return new JSONObject(r);
    }

}
