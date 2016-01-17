package io.sponges.dubtrack4j.internal.impl;

import io.sponges.dubtrack4j.api.User;

public class UserImpl implements User {

    private final String id;
    private final String name;

    public UserImpl(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

}
