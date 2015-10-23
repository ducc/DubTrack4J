package pw.sponges.dubtrack4j.internal.request;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import pw.sponges.dubtrack4j.DubAccount;
import pw.sponges.dubtrack4j.Dubtrack;
import pw.sponges.dubtrack4j.util.URL;

import java.io.IOException;

public class KickUserRequest implements Request {

    private Dubtrack dubtrack;
    private DubAccount account;

    private String roomId, roomName, userId;

    public KickUserRequest(Dubtrack dubtrack, DubAccount account, String roomId, String roomName, String userId) throws IOException {
        this.dubtrack = dubtrack;
        this.account = account;
        this.roomId = roomId;
        this.roomName = roomName;
        this.userId = userId;
    }

    public JSONObject request() throws IOException {
        Connection.Response r = Jsoup.connect(String.format(URL.KICK_USER.toString(), roomId, userId))
                .userAgent("Mozilla/5.0 DubTrack4J")
                .header("realTimeChannel", "dubtrackfm-" + roomName)
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .cookie("connect.sid", account.getToken())
                .execute();

        String str = r.body();

        return new JSONObject(str);
    }

}
