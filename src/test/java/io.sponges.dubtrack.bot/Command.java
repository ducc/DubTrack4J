package io.sponges.dubtrack.bot;

import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;

public abstract class Command {

    private final String description;
    private final String[] names;

    public Command(String description, String... names) {
        this.description = description;
        this.names = names;
    }

    public abstract void onCommand(Room room, User user, Message message, String[] args);

    public String getDescription() {
        return description;
    }

    public String[] getNames() {
        return names;
    }
}
