package cmd;

import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandHandler {

    private final List<Command> commands = new ArrayList<>();

    public CommandHandler() {
        Collections.addAll(commands,
                new TestCommand(),
                new StatsCommand(),
                new SkipCommand(),
                new HelpCommand(this),
                new PropsCommand()
        );
    }

    public void handle(Room room, User user, Message message) {
        String content = message.getContent();
        String[] split = content.split(" ");

        if (content.length() < 2) return;

        char prefix = split[0].charAt(0);
        String command = split[0].substring(1);
        if (prefix != '!') return;

        for (Command cmd : commands) {
            for (String name : cmd.getNames()) {
                if (!command.equalsIgnoreCase(name)) {
                    continue;
                }

                String[] args = new String[split.length - 1];
                for (int i = 1; i < split.length; i++) {
                    args[i] = split[i];
                }

                cmd.onCommand(room,user, message, args);
                return;
            }
        }

        System.out.println("invalid command " + command + "!");
    }

    public List<Command> getCommands() {
        return commands;
    }
}
