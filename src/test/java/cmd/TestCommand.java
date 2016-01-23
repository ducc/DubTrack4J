package cmd;

import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;

import java.io.IOException;

public class TestCommand extends Command {

    public TestCommand() {
        super("ping-pong like test", "test", "t", "ping", "hello");
    }

    @Override
    public void onCommand(Room room, User user, Message message, String[] args) {
        try {
            room.sendMessage("hi " + user.getUsername() + " :)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
