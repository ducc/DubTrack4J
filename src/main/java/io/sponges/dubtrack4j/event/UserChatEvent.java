package io.sponges.dubtrack4j.event;

import io.sponges.dubtrack4j.event.framework.Event;
import io.sponges.dubtrack4j.framework.Message;

public class UserChatEvent extends Event {

    private final Message message;

    public UserChatEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
