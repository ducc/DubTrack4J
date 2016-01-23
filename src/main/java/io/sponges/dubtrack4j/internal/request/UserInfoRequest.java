package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class UserInfoRequest implements DubRequest {

    private final DubtrackAPIImpl dubtrack;
    private final String name;
    private final DubAccount account;

    public UserInfoRequest(DubtrackAPIImpl dubtrack, String name) throws IOException {
        this.dubtrack = dubtrack;
        this.name = name;
        this.account = dubtrack.getAccount();
    }

    public JSONObject request() throws IOException {
        Response response = dubtrack.getHttpRequester().get(URL.USER_INFO + name);

        String r = response.body().string();
        Logger.debug(r);

        return new JSONObject(r);
    }

}
