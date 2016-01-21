package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPI;
import io.sponges.dubtrack4j.util.URL;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class BanUserRequest implements DubRequest {

    private final DubtrackAPI dubtrack;
    private final DubAccount account;

    private final String roomId, roomName, userId;
    private final int time;

    // TODO fix this & change to OkHttp

    public BanUserRequest(DubtrackAPI dubtrack, DubAccount account, String roomId, String roomName, String userId, int time) throws IOException {
        this.dubtrack = dubtrack;
        this.account = account;
        this.roomId = roomId;
        this.roomName = roomName;
        this.userId = userId;
        this.time = time;
    }

    public JSONObject request() throws IOException {
        Connection.Response r = Jsoup.connect(String.format(URL.BAN_USER.toString(), roomId, userId))
                .userAgent("Mozilla/5.0 DubTrack4J")
                .header("time", time + "")
                .header("realTimeChannel", "dubtrackfm-" + roomName)
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .cookie("connect.sid", account.getToken())
                .execute();

        String str = r.body();

        return new JSONObject(str);
    }

}
