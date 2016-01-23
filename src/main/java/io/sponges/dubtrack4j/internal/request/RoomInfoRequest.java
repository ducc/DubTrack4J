package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.internal.DubAccount;
import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class RoomInfoRequest implements DubRequest {

    private final DubtrackAPIImpl dubtrack;
    private final String name;
    private final DubAccount account;

    public RoomInfoRequest(DubtrackAPIImpl dubtrack, String name, DubAccount account) throws IOException {
        this.dubtrack = dubtrack;
        this.name = name;
        this.account = account;
    }

    public JSONObject request() throws IOException {
        Response response = dubtrack.getHttpRequester().get(URL.ROOM_INFO + name);

        String r = response.body().string();
        Logger.debug(r);

        return new JSONObject(r);
    }

}
