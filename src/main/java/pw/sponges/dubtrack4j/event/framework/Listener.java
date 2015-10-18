package pw.sponges.dubtrack4j.event.framework;

import pw.sponges.dubtrack4j.event.*;

public interface Listener {

    default void onChat(UserChatEvent event) {}

    default void onJoin(UserJoinEvent event) {}

    default void onLeave(UserLeaveEvent event) {}

    default void onSongChange(SongChangeEvent event) {}

    default void onUserDub(UserDubEvent event) {}

}
