package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPI;
import io.sponges.dubtrack4j.util.URL;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class SongInfoRequest implements Request {

    private DubtrackAPI dubtrack;
    private String id;
    private DubAccount account;

    public SongInfoRequest(DubtrackAPI dubtrack, String id, DubAccount account) throws IOException {
        this.dubtrack = dubtrack;
        this.id = id;
        this.account = account;
    }

    public JSONObject request() throws IOException {
        Connection.Response r = Jsoup.connect(URL.SONG_INFO + id)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .cookie("connect.sid", account.getToken())
                .execute();

        String str = r.body();

        return new JSONObject(str);
    }

}
