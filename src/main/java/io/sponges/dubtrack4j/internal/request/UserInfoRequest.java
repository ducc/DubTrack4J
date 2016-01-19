package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubtrackAPI;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.util.URL;

import java.io.IOException;

public class UserInfoRequest implements DubRequest {

    private DubtrackAPI dubtrack;
    private String name;
    private DubAccount account;

    public UserInfoRequest(DubtrackAPI dubtrack, String name, DubAccount account) throws IOException {
        this.dubtrack = dubtrack;
        this.name = name;
        this.account = account;
    }

    public JSONObject request() throws IOException {
        Connection.Response r = Jsoup.connect(URL.USER_INFO + name)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .cookie("connect.sid", account.getToken())
                .execute();

        String str = r.body();

        return new JSONObject(str);
    }

}
