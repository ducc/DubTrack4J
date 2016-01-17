package io.sponges.dubtrack4j;

import io.sponges.dubtrack4j.internal.Auth;

import java.io.IOException;

public class DubAccount {

    private DubtrackAPI dubtrack;
    private String username, password, token;

    public DubAccount(DubtrackAPI dubtrack, String username, String password) {
        this.dubtrack = dubtrack;
        this.username = username;
        this.password = password;
    }

    public void login() throws IOException {
        token = new Auth(dubtrack, username, password).getSid();
    }

    public String getToken() {
        return token;
    }
}
