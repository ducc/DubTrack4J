package io.sponges.dubtrack4j.util;

public enum URL {

    // Auth
    LOGIN("https://www.dubtrack.fm/login"),
    AUTH("https://framework.dubtrack.fm/auth/dubtrack"),

    // Requests
    JOIN_ROOM("https://framework.dubtrack.fm/room/"),
    ROOM_INFO("https://framework.dubtrack.fm/room/"),
    SEND_MESSAGE("https://framework.dubtrack.fm/chat/"),
    SONG_DUB("https://framework.dubtrack.fm/room/%s/playlist/active/dubs"),
    SONG_INFO("https://framework.dubtrack.fm/song/"),
    USER_INFO("https://framework.dubtrack.fm/user/"),
    KICK_USER("https://framework.dubtrack.fm/chat/kick/%s/user/%s"),
    BAN_USER("https://framework.dubtrack.fm/chat/ban/%s/user/%s");

    private final String url;

    URL(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
