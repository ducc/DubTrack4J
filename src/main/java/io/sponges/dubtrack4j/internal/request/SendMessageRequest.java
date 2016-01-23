package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class SendMessageRequest implements DubRequest {

    private final String room, message;
    private final DubtrackAPIImpl dubtrack;
    private final DubAccount account;

    public SendMessageRequest(String room, String message, DubtrackAPIImpl dubtrack) throws IOException {
        this.room = room;
        this.message = message;
        this.dubtrack = dubtrack;
        this.account = dubtrack.getAccount();
    }

    public JSONObject request() throws IOException {
        Response response = dubtrack.getHttpRequester().post(URL.SEND_MESSAGE + room, new HashMap<String, String>() {{
            put("message", message);
        }}, new HashMap<String, String>() {{
            put("realTimeChannel", "dubtrackfm-" + room);
        }});

        String r = response.body().string();
        Logger.debug("Sent message! " + r);

        return new JSONObject(r);
    }

}
