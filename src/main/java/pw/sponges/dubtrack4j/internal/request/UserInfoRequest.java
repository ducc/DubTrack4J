package pw.sponges.dubtrack4j.internal.request;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import pw.sponges.dubtrack4j.DubAccount;
import pw.sponges.dubtrack4j.Dubtrack;

import java.io.IOException;

public class UserInfoRequest implements Request {

    private static final String URL = "https://api.dubtrack.fm/user/";

    private Dubtrack dubtrack;
    private String name;
    private DubAccount account;

    public UserInfoRequest(Dubtrack dubtrack, String name, DubAccount account) throws IOException {
        this.dubtrack = dubtrack;
        this.name = name;
        this.account = account;
    }

    public JSONObject request() throws IOException {
        Connection.Response r = Jsoup.connect(URL + name)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .cookie("connect.sid", account.getToken())
                .execute();

        String str = r.body();

        return new JSONObject(str);
    }

}
