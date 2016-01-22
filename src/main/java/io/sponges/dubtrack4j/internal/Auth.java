package io.sponges.dubtrack4j.internal;

import io.sponges.dubtrack4j.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.Logger;
import io.sponges.dubtrack4j.util.URL;
import okhttp3.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class Auth {

    private final DubtrackAPIImpl dubtrack;
    private final String username, password;

    @Deprecated
    private String sid = null;

    public Auth(DubtrackAPIImpl dubtrack, String username, String password) throws IOException {
        this.dubtrack = dubtrack;
        this.username = username;
        this.password = password;
    }

    public void login2() throws IOException {
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

        System.out.println("Login respose=" + response.body());

        sid = response.cookies().get("connect.sid");
    }

    public void login() throws IOException {
        OkHttpClient client = dubtrack.getHttpClient();

        {
            Request request = new Request.Builder().url(URL.LOGIN.toString()).addHeader("User-Agent", "Mozilla/5.0 DubTrack4J").get().build();
            client.newCall(request).execute();
        }

        {
            RequestBody body = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url(URL.AUTH.toString())
                    .addHeader("User-Agent", "Mozilla/5.0 DubTrack4J")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            Logger.debug("Login response = " + response.body().string() + "\nheaders = " + response.headers().toMultimap());
        }

        //sid = response.cookies().get("connect.sid");
    }

    @Deprecated
    public String getSid() {
        return sid;
    }

}
