package pw.sponges.dubtrack4j.internal.request;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import pw.sponges.dubtrack4j.DubAccount;
import pw.sponges.dubtrack4j.api.DubType;
import pw.sponges.dubtrack4j.util.URL;

import java.io.IOException;

public class SongDubRequest implements Request {

    private String room;
    private DubType type;
    private DubAccount account;

    public SongDubRequest(String room, DubType type, DubAccount account) throws IOException {
        this.room = room;
        this.type = type;
        this.account = account;

        request();
    }

    public Connection.Response request() throws IOException {
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

}
