package io.sponges.dubtrack4j.internal;

import io.sponges.dubtrack4j.DubtrackAPI;
import io.sponges.dubtrack4j.util.URL;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class Auth {

    private DubtrackAPI dubtrack;
    private String username, password, sid;

    public Auth(DubtrackAPI dubtrack, String username, String password) throws IOException {
        this.dubtrack = dubtrack;
        this.username = username;
        this.password = password;

        login();
    }

    private void login() throws IOException {
        Connection.Response loginForm = Jsoup.connect(URL.LOGIN.toString())
                .method(Connection.Method.GET)
                .userAgent("Mozilla/5.0 DubTrack4J")
                .execute();

        Connection.Response response = Jsoup.connect(URL.AUTH.toString())
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
