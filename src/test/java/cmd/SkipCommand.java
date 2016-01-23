package cmd;

import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;

import java.io.IOException;

public class SkipCommand extends Command {

    public SkipCommand() {
        super("skips a song", "skip");
    }

    @Override
    public void onCommand(Room room, User user, Message message, String[] args) {
        System.out.println("old song id " + room.getCurrent().getId());
        System.out.println("room id " + room.getId());
        try {
            room.getCurrent().skip();
            room.sendMessage("Skipped!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
