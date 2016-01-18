package io.sponges.dubtrack.bot;

import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.Song;
import io.sponges.dubtrack4j.framework.User;

public class StatsCommand extends Command {

    public StatsCommand() {
        super("shows stats for the current song", "stats", "statistics", "stat");
    }

    @Override
    public void onCommand(Room room, User user, Message message, String[] args) {
        Song song = room.getCurrent();
        int ups = song.getUpdubs();
        int downs = song.getDowndubs();

        room.sendMessage(String.format("%s updubs & %s downdubs", ups, downs));
    }

}
