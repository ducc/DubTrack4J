package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.ContentType;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

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

    public JSONObject request2() throws IOException {
        Connection.Response r = Jsoup.connect(URL.ROOM_INFO + name)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .cookie("connect.sid", account.getToken())
                .execute();

        String str = r.body();

        return new JSONObject(str);
    }

    public JSONObject request() throws IOException {
        Request request = new Request.Builder()
                .url(URL.ROOM_INFO + name)
                //.addHeader("Cookie", "connect.sid=" + account.getToken()) // TODO remove cus not needed amirite
                .addHeader("Content-Type", ContentType.JSON)
                .get()
                .build();

        Response response = dubtrack.getHttpClient().newCall(request).execute();
        String r = response.body().string();

        Logger.debug(r);

        return new JSONObject(r);
    }

}
