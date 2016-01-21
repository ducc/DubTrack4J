package io.sponges.dubtrack4j.internal.request;

import com.squareup.mimecraft.FormEncoding;
import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.framework.DubType;
import io.sponges.dubtrack4j.util.ContentType;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class SongDubRequest implements DubRequest {

    private final String room;
    private final DubType type;
    private final DubtrackAPIImpl dubtrack;
    private final DubAccount account;

    public SongDubRequest(String room, DubType type, DubtrackAPIImpl dubtrack) throws IOException {
        this.room = room;
        this.type = type;
        this.dubtrack = dubtrack;
        this.account = dubtrack.getAccount();
    }

    public Connection.Response request2() throws IOException {
        String url = String.format(URL.SONG_DUB.toString(), room);

        String message = type.name().toLowerCase();

        Connection.Response response = null;

        try {
            response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 DubTrack4J")
                    .cookie("connect.sid", account.getToken())
                    .data("type", message)
                    .method(Connection.Method.POST)
                    .execute();
        } catch (IOException e) {
            if (!e.getMessage().equalsIgnoreCase("HTTP error fetching URL")) {
                throw e;
            }
        }

        return response;
    }

    public JSONObject request() throws IOException {
        String url = String.format(URL.SONG_DUB.toString(), room);
        String message = type.name().toLowerCase();

        FormEncoding data = new FormEncoding.Builder()
                .add("type", message)
                .build();

        RequestBody body = RequestBody.create(MediaType.parse(ContentType.WWW_FORM), data.toString());

        Request request = new Request.Builder()
                .url(url)
                //.addHeader("Cookie", "connect.sid=" + account.getToken()) // todo remove this
                .addHeader("Content-Type", ContentType.JSON)
                .post(body)
                .build();

        Response response = dubtrack.getHttpClient().newCall(request).execute();
        String r = response.body().string();

        Logger.debug(r);

        return new JSONObject(r);
    }

}
