package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class RoomPlaylistRequest implements Request {

    private String room;
    private DubAccount account;

    public RoomPlaylistRequest(String room, DubAccount account) throws IOException {
        this.room = room;
        this.account = account;
    }

    public JSONObject request() throws IOException {
        Logger.debug("Requesting playlist id...");
        String url = String.format(URL.ROOM_PLAYLIST.toString(), room);
        Logger.debug("URL = " + url);

        Connection.Response r = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .cookie("connect.sid", account.getToken())
                .execute();

        String str = r.body();
        Logger.debug(str);

        return new JSONObject(str);
    }

}
