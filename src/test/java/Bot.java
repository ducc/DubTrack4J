import cmd.CommandHandler;
import com.pubnub.api.PubnubException;
import io.sponges.dubtrack4j.DubtrackAPI;
import io.sponges.dubtrack4j.DubtrackBuilder;
import io.sponges.dubtrack4j.event.UserChatEvent;
import io.sponges.dubtrack4j.event.UserJoinEvent;
import io.sponges.dubtrack4j.event.framework.EventBus;
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

        this.dubtrack = DubtrackBuilder.create(credentials[0], credentials[1]).setLoggingMode(Logger.LoggingMode.DEBUG).buildAndLogin();

        EventBus bus = this.dubtrack.getEventBus();

        bus.register(UserChatEvent.class, event -> {
            Message message = event.getMessage();
            User user = message.getUser();
            Room room = message.getRoom();
            String content = message.getContent();

            System.out.printf("[%s] %s: %s\n", room.getName(), user.getUsername(), content);

            commandHandler.handle(room, user, message);
        });

        bus.register(UserJoinEvent.class, event -> {
            try {
                event.getRoom().sendMessage(event.getUser().getUsername() + " left!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Logger.info("Logged in!");

        try {
            this.dubtrack.joinRoom("mikgreg");
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
