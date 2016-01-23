package io.sponges.dubtrack4j.event.framework;

import io.sponges.dubtrack4j.event.*;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    // TODO make this thread safe

    private final List<Listener> listeners = new ArrayList<>();

    /**
     * Registers a new listener to handle events
     * @param listener the listener to register
     */
    public void registerListener(Listener listener) {
        listeners.add(listener);
    }
    /**
     * Unregisters a listener from handling events
     * @param listener the listener to unregister
     */
    public void unregisterListener(Listener listener) {
        listeners.remove(listener);
    }

    /**
     * Handles the event
     * @param event the event to handle
     */
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

        // TODO add kick event & ban event (once fully implemented)
    }

}
