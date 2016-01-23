package io.sponges.dubtrack4j.internal;

import io.sponges.dubtrack4j.internal.request.Requester;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Response;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;

public class Auth {

    private final DubtrackAPIImpl dubtrack;
    private final String username, password;

    private String sid = null;

    public Auth(DubtrackAPIImpl dubtrack, String username, String password) throws IOException {
        this.dubtrack = dubtrack;
        this.username = username;
        this.password = password;
    }

    public void login() throws IOException {
        Requester requester = dubtrack.getHttpRequester();

        Response response = requester.post(URL.AUTH.toString(), new HashMap<String, String>() {{
            put("username", username);
            put("password", password);
        }});

        String cookies = response.header("Set-Cookie");

        boolean foundSid = false;

        List<HttpCookie> httpCookies = HttpCookie.parse(cookies);
        for (HttpCookie cookie : httpCookies) {
            if (cookie.getName().equalsIgnoreCase("connect.sid")) {
                sid = cookie.getValue();
                foundSid = true;
                break;
            }
        }

        if (!foundSid) {
            Logger.warning("No connection sid in cookie?");
        } else {
            Logger.debug("Found sid! " + sid);
        }
    }

    public String getSid() {
        return sid;
    }

}
