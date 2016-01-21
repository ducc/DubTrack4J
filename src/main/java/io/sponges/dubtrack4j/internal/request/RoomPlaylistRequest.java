package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.ContentType;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class RoomPlaylistRequest implements DubRequest {

    private final String room;
    private final DubtrackAPIImpl dubtrack;
    private final DubAccount account;

    public RoomPlaylistRequest(String room, DubtrackAPIImpl dubtrack) throws IOException {
        this.room = room;
        this.dubtrack = dubtrack;
        this.account = dubtrack.getAccount();
    }

    /*public JSONObject request2() throws IOException {
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
    }*/

    public JSONObject request() throws IOException {
        Request request = new Request.Builder()
                .url(String.format(URL.ROOM_PLAYLIST.toString(), room))
                .addHeader("Content-Type", ContentType.JSON)
                .get()
                .build();

        Response response = dubtrack.getHttpClient().newCall(request).execute();
        String r = response.body().string();

        Logger.debug(r);

        return new JSONObject(r);
    }

}
