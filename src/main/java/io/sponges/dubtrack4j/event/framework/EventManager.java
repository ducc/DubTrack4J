package io.sponges.dubtrack4j.event.framework;

import io.sponges.dubtrack4j.event.*;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private List<Listener> listeners;

    public EventManager() {
        this.listeners = new ArrayList<>();
    }

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        listeners.remove(listener);
    }

    public void handle(Event event) {
        for (Listener l : listeners) {
            if (event instanceof UserChatEvent) {
                l.onChat((UserChatEvent) event);
            } else if (event instanceof UserJoinEvent) {
                l.onJoin((UserJoinEvent) event);
            } else if (event instanceof UserLeaveEvent) {
                l.onLeave((UserLeaveEvent) event);
            } else if (event instanceof SongChangeEvent) {
                l.onSongChange((SongChangeEvent) event);
            } else if (event instanceof UserDubEvent) {
                l.onUserDub((UserDubEvent) event);
            } //else if (event instanceof UserKickEvent)  {
            //    l.onUserKick((UserKickEvent) event);
            //}
        }
    }

}
