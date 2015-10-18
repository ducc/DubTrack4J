package pw.sponges.dubtrack4j.internal.request;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import pw.sponges.dubtrack4j.DubAccount;
import pw.sponges.dubtrack4j.util.URL;

import java.io.IOException;

public class SendMessageRequest implements Request {

    private String room, message;
    private DubAccount account;

    public SendMessageRequest(String room, String message, DubAccount account) throws IOException {
        this.room = room;
        this.message = message;
        this.account = account;

        request();
    }

    public Connection.Response request() throws IOException {
        String url = URL.SEND_MESSAGE + room;

        Connection.Response response = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 DubTrack4J")
                .cookie("connect.sid", account.getToken())
                .header("realTimeChannel", "dubtrackfm-" + room)
                .data("message", message)
                .method(Connection.Method.POST)
                .execute();

        return response;
    }

}
