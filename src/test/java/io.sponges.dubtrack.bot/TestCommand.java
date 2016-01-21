package io.sponges.dubtrack.bot;

import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;

public class TestCommand extends Command {

    public TestCommand() {
        super("ping-pong like test", "test", "t", "ping", "hello");
    }

    @Override
    public void onCommand(Room room, User user, Message message, String[] args) {
        room.sendMessage("hi " + user.getUsername() + " :)");
    }

}
