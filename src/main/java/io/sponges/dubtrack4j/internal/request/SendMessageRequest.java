package io.sponges.dubtrack4j.internal.request;

import com.squareup.mimecraft.FormEncoding;
import io.sponges.dubtrack4j.DubAccount;
import io.sponges.dubtrack4j.DubtrackAPIImpl;
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

public class SendMessageRequest implements DubRequest {

    private final String room, message;
    private final DubtrackAPIImpl dubtrack;
    private final DubAccount account;

    public SendMessageRequest(String room, String message, DubtrackAPIImpl dubtrack) throws IOException {
        this.room = room;
        this.message = message;
        this.dubtrack = dubtrack;
        this.account = dubtrack.getAccount();
    }

    public Connection.Response request2() throws IOException {
        String url = URL.SEND_MESSAGE + room;

        return Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 DubTrack4J")
                .cookie("connect.sid", account.getToken())
                .header("realTimeChannel", "dubtrackfm-" + room)
                .data("message", message)
                .method(Connection.Method.POST)
                .execute();
    }

    public JSONObject request() throws IOException {
        FormEncoding data = new FormEncoding.Builder()
                .add("message", message)
                .build();

        RequestBody body = RequestBody.create(MediaType.parse(ContentType.WWW_FORM), data.toString());

        Request request = new Request.Builder()
                .url(URL.SEND_MESSAGE + room)
                //.addHeader("Cookie", "connect.sid=" + account.getToken()) // todo remove this
                .addHeader("Content-Type", ContentType.JSON)
                .addHeader("realTimeChannel", "dubtrackfm-" + room)
                .post(body)
                .build();

        Response response = dubtrack.getHttpClient().newCall(request).execute();
        String r = response.body().string();

        Logger.debug(r);

        return new JSONObject(r);
    }

}
