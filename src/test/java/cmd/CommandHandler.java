/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Joe Burnard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cmd;

import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;

import java.io.IOException;
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
                new PropsCommand(),
                new UserInfoCommand(),
                new CreatorCommand(),
                new QueueCommand(),
                new RoomQueueCommand(),
                new RemoveAllCommand()
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
                    args[i - 1] = split[i];
                }

                try {
                    cmd.onCommand(room,user, message, args);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        System.out.println("invalid command " + command + "!");
    }

    public List<Command> getCommands() {
        return commands;
    }
}
