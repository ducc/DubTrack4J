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

package io.sponges.dubtrack4j.framework;

import io.sponges.dubtrack4j.exception.InvalidUserException;

import java.io.IOException;
import java.util.Map;

public interface Room {

    /**
     * The name of the room. This is found in the URL
     * http://dubtrack.fm/join/<name>
     * @return room name
     */
    String getName();

    /**
     * The ID of the room
     * @return room id
     */
    String getId();

    /**
     * The ID of the current playlist
     * @return playlist id
     */
    String getPlaylistId() throws IOException;

    /**
     * A map of all the users in the room
     * The map key is the user id, value is the User object
     * @return current users map
     */
    Map<String, User> getUsers();

    /**
     * Gets the user instance for the user with the defined username
     * @param username the username of the user
     * @return User instance
     */
    User getUserByUsername(String username);

    /**
     * Gets the user instance for the user with the defined id
     * @param id the id of the user
     * @return User instance
     */
    User getUserById(String id);

    /**
     * The current song playing in the room
     * @return current Song
     */
    Song getCurrent();

    /**
     * Sends a message in the room's chat
     * @param message the message to send
     */
    void sendMessage(String message) throws IOException;

    /**
     * Kicks a user from the room by username
     * @param username the user to kick
     */
    void kickUser(String username) throws IOException, InvalidUserException;

    /**
     * Kicks a user from the room by User instance
     * @param user the user to kick
     */
    void kickUser(User user) throws IOException;

    /**
     * Bans a user from the room by username
     * @param username the user to ban
     */
    void banUser(String username) throws IOException, InvalidUserException;

    /**
     * Bans a user from the room by username for a defined length
     * @param username the user to ban
     * @param length the length of the ban (in seconds)
     */
    void banUser(String username, int length) throws IOException, InvalidUserException;

    /**
     * Bans a user from the room by User instance
     * @param user the user to ban
     */
    void banUser(User user) throws IOException;

    /**
     * Bans a user from the room by User instance for a defined length
     * @param user the user to ban
     * @param length the length of the ban (in seconds)
     */
    void banUser(User user, int length) throws IOException;

    /**
     * Skips the current song playing in the room
     */
    void skipSong() throws IOException;

    // TODO song queue management; adding, removing & viewing

}
