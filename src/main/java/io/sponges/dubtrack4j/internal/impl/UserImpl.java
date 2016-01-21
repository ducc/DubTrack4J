package io.sponges.dubtrack4j.internal.impl;

import io.sponges.dubtrack4j.framework.User;

public class UserImpl implements User {

    private final String id;
    private final String username;

    public UserImpl(String id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
