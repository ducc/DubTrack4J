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
        if (event instanceof UserChatEvent) {
            UserChatEvent e = (UserChatEvent) event;

            for (Listener l : listeners) l.onChat(e);
        } else if (event instanceof UserJoinEvent) {
            UserJoinEvent e = (UserJoinEvent) event;

            for (Listener l : listeners) l.onJoin(e);
        } else if (event instanceof UserLeaveEvent) {
            UserLeaveEvent e = (UserLeaveEvent) event;

            for (Listener l : listeners) l.onLeave(e);
        } else if (event instanceof SongChangeEvent) {
            SongChangeEvent e = (SongChangeEvent) event;

            for (Listener l : listeners) l.onSongChange(e);
        } else if (event instanceof UserDubEvent) {
            UserDubEvent e = (UserDubEvent) event;

            for (Listener l : listeners) l.onUserDub(e);
        }

        /*for (Listener l : listeners) {
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
        }*/
    }

}
