package pw.sponges.dubtrack4j.event;

import pw.sponges.dubtrack4j.api.Message;
import pw.sponges.dubtrack4j.event.framework.Event;

public class UserChatEvent implements Event {

    private final Message message;

    public UserChatEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
