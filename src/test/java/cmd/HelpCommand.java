package cmd;

import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;

import java.io.IOException;
import java.util.Iterator;

public class HelpCommand extends Command {

    private final CommandHandler commandHandler;

    public HelpCommand(CommandHandler commandHandler) {
        super("shows commands", "help", "h", "commands");

        this.commandHandler = commandHandler;
    }

    @Override
    public void onCommand(Room room, User user, Message message, String[] args) {
        StringBuilder builder = new StringBuilder("Commands: ");

        Iterator iterator = commandHandler.getCommands().iterator();
        while (iterator.hasNext()) {
            Command command = (Command) iterator.next();

            builder.append("!").append(command.getNames()[0]);

            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }

        try {
            room.sendMessage(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
