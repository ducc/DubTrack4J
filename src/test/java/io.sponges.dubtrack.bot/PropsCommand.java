package io.sponges.dubtrack.bot;

import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.Song;
import io.sponges.dubtrack4j.framework.User;

import java.util.Objects;

public class PropsCommand extends Command {

    public PropsCommand() {
        super("show you like the song", "props");
    }

    @Override
    public void onCommand(Room room, User user, Message message, String[] args) {
        Song song = room.getCurrent();
        User dj = song.getUser();

        if (Objects.equals(dj.getId(), user.getId())) {
            room.sendMessage("You can't give props to yourself! :'(");
            return;
        }

        room.sendMessage(String.format("@%s gives props to @%s for this song! <3", user.getUsername(), dj.getUsername()));
    }

}
