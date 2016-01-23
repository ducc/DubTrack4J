package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.framework.DubType;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class SongDubRequest implements DubRequest {

    private final String room;
    private final DubType type;
    private final DubtrackAPIImpl dubtrack;
    private final DubAccount account;

    public SongDubRequest(String room, DubType type, DubtrackAPIImpl dubtrack) throws IOException {
        this.room = room;
        this.type = type;
        this.dubtrack = dubtrack;
        this.account = dubtrack.getAccount();
    }

    public JSONObject request() throws IOException {
        String url = String.format(URL.SONG_DUB.toString(), room);
        String message = type.name().toLowerCase();

        Response response = dubtrack.getHttpRequester().post(url, new HashMap<String, String>() {{
            put("type", message);
        }});

        String r = response.body().string();
        Logger.debug(r);

        return new JSONObject(r);
    }

}
