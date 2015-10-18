package pw.sponges.dubtrack4j.internal;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import pw.sponges.dubtrack4j.Dubtrack;

import java.io.IOException;

public class Auth {

    public static final String LOGIN_URL = "https://www.dubtrack.fm/login";
    public static final String AUTH_URL = "https://api.dubtrack.fm/auth/dubtrack";

    private Dubtrack dubtrack;
    private String username, password, sid;

    public Auth(Dubtrack dubtrack, String username, String password) throws IOException {
        this.dubtrack = dubtrack;
        this.username = username;
        this.password = password;

        login();
    }

    private void login() throws IOException {
        Connection.Response loginForm = Jsoup.connect(LOGIN_URL)
                .method(Connection.Method.GET)
                .userAgent("Mozilla/5.0 DubTrack4J")
                .execute();

        Connection.Response response = Jsoup.connect(AUTH_URL)
                .ignoreContentType(true)
                .data("username", username)
                .data("password", password)
                .cookies(loginForm.cookies())
                .method(Connection.Method.POST)
                .execute();

        sid = response.cookies().get("connect.sid");
    }

    public String getSid() {
        return sid;
    }

}
