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

import cmd.CommandHandler;
import com.pubnub.api.PubnubException;
import io.sponges.dubtrack4j.DubtrackAPI;
import io.sponges.dubtrack4j.DubtrackBuilder;
import io.sponges.dubtrack4j.event.framework.EventBus;
import io.sponges.dubtrack4j.event.room.SongChangeEvent;
import io.sponges.dubtrack4j.event.user.UserChatEvent;
import io.sponges.dubtrack4j.event.user.UserDubEvent;
import io.sponges.dubtrack4j.event.user.UserEvent;
import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;
import io.sponges.dubtrack4j.util.Logger;
import org.json.JSONObject;

import java.io.*;

public class Bot {

    private final CommandHandler commandHandler;

    private final DubtrackAPI dubtrack;

    public Bot() throws IOException {
        this.commandHandler = new CommandHandler();

        String[] credentials = loadCredentials(new File("credentials.json"));
        if (credentials == null) {
            System.out.println("Created credentials.json! Please enter username & password!");
            System.exit(-1);
        }

        this.dubtrack = new DubtrackBuilder(credentials[0], credentials[1]).setLoggingMode(Logger.LoggingMode.DEBUG).buildAndLogin();

        EventBus bus = this.dubtrack.getEventBus();

        bus.register(UserEvent.class, event -> {
            // TODO find out why this doesn't seem to work, something wrong with EventBus?
            System.out.println("user event fired " + event.getClass().getName());
        });

        bus.register(UserChatEvent.class, event -> {
            Message message = event.getMessage();
            User user = message.getUser();
            Room room = message.getRoom();
            String content = message.getContent();

            System.out.printf("[%s] %s: %s\n", room.getName(), user.getUsername(), content);

            try {
                commandHandler.handle(room, user, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        bus.register(UserDubEvent.class, event -> {
            int downdubs = event.getSong().getDowndubs();

            if (downdubs > 1) {
                try {
                    event.getSong().skip();
                    event.getRoom().sendMessage("Got 2 down dubs so skipped tbh!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        bus.register(SongChangeEvent.class, event -> {
            Room room = event.getRoom();
            try {
                room.sendMessage(String.format("Now playing %s!", event.getNewSong().getSongInfo().getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Logger.info("Logged in!");

        try {
            this.dubtrack.joinRoom("sponges");
        } catch (PubnubException e) {
            e.printStackTrace();
            return;
        }
        Logger.info("Joined 'sponges'!");

        //noinspection InfiniteLoopStatement,StatementWithEmptyBody
        while (true);
    }

    private String[] loadCredentials(File file) {
        if (!file.exists()) {
            BufferedWriter writer = null;

            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(new JSONObject().put("username", "").put("password", "").toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(file));

            String input;
            while ((input = reader.readLine()) != null) {
                builder.append("\n").append(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject json = new JSONObject(builder.toString());
        String username = json.getString("username");
        String password = json.getString("password");

        return new String[] { username, password };
    }

    public static void main(String[] args) throws IOException {
        new Bot();
    }
}
