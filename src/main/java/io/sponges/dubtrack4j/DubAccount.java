package io.sponges.dubtrack4j;

import io.sponges.dubtrack4j.internal.Auth;
import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;

import java.io.IOException;

public class DubAccount {

    private final DubtrackAPIImpl dubtrack;
    private final String username, password;

    private String token = null;

    public DubAccount(DubtrackAPIImpl dubtrack, String username, String password) {
        this.dubtrack = dubtrack;
        this.username = username;
        this.password = password;
    }

    /**
     * Attempts to login to dubtrack
     * @throws IOException
     */
    public void login() throws IOException {
        Auth auth = new Auth(dubtrack, username, password);
        auth.login();
        this.token = auth.getSid();
    }

    public String getToken() {
        return token;
    }
}
