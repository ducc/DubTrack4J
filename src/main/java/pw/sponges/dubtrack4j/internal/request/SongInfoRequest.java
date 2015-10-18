package pw.sponges.dubtrack4j.internal.request;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import pw.sponges.dubtrack4j.DubAccount;
import pw.sponges.dubtrack4j.Dubtrack;

import java.io.IOException;

public class SongInfoRequest implements Request {

    private static final String URL = "https://api.dubtrack.fm/song/";

    private Dubtrack dubtrack;
    private String id;
    private DubAccount account;

    public SongInfoRequest(Dubtrack dubtrack, String id, DubAccount account) throws IOException {
        this.dubtrack = dubtrack;
        this.id = id;
        this.account = account;
    }

    public JSONObject request() throws IOException {
        Connection.Response r = Jsoup.connect(URL + id)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .cookie("connect.sid", account.getToken())
                .execute();

        String str = r.body();

        return new JSONObject(str);
    }

}
