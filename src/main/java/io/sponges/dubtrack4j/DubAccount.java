package io.sponges.dubtrack4j;

import io.sponges.dubtrack4j.internal.Auth;

import java.io.IOException;

public class DubAccount {

    private final DubtrackAPIImpl dubtrack;
    private final String username, password;

    @Deprecated
    private String token = null;

    DubAccount(DubtrackAPIImpl dubtrack, String username, String password) {
        this.dubtrack = dubtrack;
        this.username = username;
        this.password = password;
    }

    /**
     * Attempts to login to dubtrack
     * @throws IOException
     */
    public void login() throws IOException {
        new Auth(dubtrack, username, password).login();
    }

    @Deprecated
    public String getToken() {
        return token;
    }
}
