package io.sponges.dubtrack4j.internal.request;

import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.Logger;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public final class Requester {

    public static final String USER_AGENT = "Mozilla/5.0 Dubtrack4J (github.com/sponges/dubtrack4j)";
    private static final RequestBody EMPTY_BODY = RequestBody.create(null, new byte[0]);

    private final DubtrackAPIImpl dubtrack;
    private final OkHttpClient client;

    public Requester(DubtrackAPIImpl dubtrack) {
        this.dubtrack = dubtrack;
        this.client = dubtrack.getHttpClient();
    }

    public Response get(String url) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();

        builder = appendCookies(builder);
        return request(builder.build());
    }

    public Response post(String url) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(EMPTY_BODY);

        builder = appendCookies(builder);
        return request(builder.build());
    }

    public Response post(String url, Map<String, String> data) throws IOException {
        return post(url, data, null);
    }

    public Response post(String url, Map<String, String> data, Map<String, String> headers) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            builder.addEncoded(key, value);
        }

        RequestBody body = builder.build();

        Request.Builder b = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", body.contentType().type())
                .addHeader("Content-Length", String.valueOf(body.contentLength()))
                .post(body);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                b.addHeader(key, value);
            }
        }

        b = appendCookies(b);
        return request(b.build());
    }

    private Response request(Request request) throws IOException {
        Response response = client.newCall(request).execute();
        Logger.debug("Got " + response.code() + " for " + request.method().toUpperCase() + " " + request.url());
        return response;
    }

    private Request.Builder appendCookies(Request.Builder builder) {
        String token = dubtrack.getAccount().getToken();

        builder.addHeader("User-Agent", USER_AGENT);

        if (token == null) {
            return builder;
        } else {
            builder.addHeader("Cookie", "connect.sid=" + token);
            return builder;
        }
    }

}
