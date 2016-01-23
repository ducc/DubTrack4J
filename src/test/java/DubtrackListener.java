import cmd.CommandHandler;
import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.Song;
import io.sponges.dubtrack4j.framework.User;
import io.sponges.dubtrack4j.event.*;
import io.sponges.dubtrack4j.event.framework.Listener;

import java.io.IOException;

public class DubtrackListener implements Listener {

    private final CommandHandler commandHandler;

    public DubtrackListener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onChat(UserChatEvent event) {
        Message message = event.getMessage();
        User user = message.getUser();
        Room room = message.getRoom();
        String content = message.getContent();

        System.out.printf("[%s] %s: %s\n", room.getName(), user.getUsername(), content);

        commandHandler.handle(room, user, message);
    }

    @Override
    public void onJoin(UserJoinEvent event) {
        User user = event.getUser();
        Room room = event.getRoom();

        try {
            room.sendMessage(user.getUsername() + " joined!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLeave(UserLeaveEvent event) {
        User user = event.getUser();
        Room room = event.getRoom();

        try {
            room.sendMessage(user.getUsername() + " left!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSongChange(SongChangeEvent event) {
        Room room = event.getRoom();
        Song last = event.getLastSong();
        Song next = event.getNewSong();

        if (room == null || last == null || next == null) return;

        try {
            room.sendMessage(String.format("Now playing %s! %s got +%s/-%s for %s", next.getSongInfo().getName(), last.getUser().getUsername(), last.getUpdubs(), last.getDowndubs(), last.getSongInfo().getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUserDub(UserDubEvent event) {
        //event.getRoom().sendMessage(event.getUser().getUsername() + " " + event.getType().name().toLowerCase() + "ed!");
    }

}
