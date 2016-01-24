/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Joe Burnard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.sponges.dubtrack4j.util;

/**
 * Enum containing constants for each request URL
 *
 * Why use an Enum?
 * Easy to add in new URLs, IMO it looks cleaner than a class with string constants for something like this
 */
public enum URL {

    // Auth
    AUTH("https://api.dubtrack.fm/auth/dubtrack"),

    // Requests
    JOIN_ROOM("https://api.dubtrack.fm/room/"),
    ROOM_INFO("https://api.dubtrack.fm/room/"),
    SEND_MESSAGE("https://api.dubtrack.fm/chat/"),
    SONG_DUB("https://api.dubtrack.fm/room/%s/playlist/active/dubs"),
    SONG_INFO("https://api.dubtrack.fm/song/"),
    USER_INFO("https://api.dubtrack.fm/user/"),
    KICK_USER("https://api.dubtrack.fm/chat/kick/%s/user/%s"),
    BAN_USER("https://api.dubtrack.fm/chat/ban/%s/user/%s"),
    SKIP_SONG("https://api.dubtrack.fm/chat/skip/%s/%s"),
    ROOM_PLAYLIST("https://api.dubtrack.fm/room/%s/playlist/active"),
    QUEUE_SONG("https://api.dubtrack.fm/room/%s/playlist");

    private final String url;

    URL(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
