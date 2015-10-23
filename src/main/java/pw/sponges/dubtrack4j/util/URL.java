package pw.sponges.dubtrack4j.util;

public enum URL {

    // Auth
    LOGIN("https://www.dubtrack.fm/login"),
    AUTH("https://api.dubtrack.fm/auth/dubtrack"),

    // Requests
    JOIN_ROOM("https://api.dubtrack.fm/room/"),
    ROOM_INFO("https://api.dubtrack.fm/room/"),
    SEND_MESSAGE("https://api.dubtrack.fm/chat/"),
    SONG_DUB("https://api.dubtrack.fm/room/%s/playlist/active/dubs"),
    SONG_INFO("https://api.dubtrack.fm/song/"),
    USER_INFO("https://api.dubtrack.fm/user/"),
    KICK_USER("https://api.dubtrack.fm/chat/kick/%s/user/%s");

    private final String url;

    URL(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
