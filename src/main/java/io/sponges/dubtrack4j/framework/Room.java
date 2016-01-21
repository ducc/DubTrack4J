package io.sponges.dubtrack4j.framework;

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
    String getPlaylistId();

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
    void sendMessage(String message);

    /**
     * Kicks a user from the room by username
     * @param username the user to kick
     */
    void kickUser(String username);

    /**
     * Kicks a user from the room by User instance
     * @param user the user to kick
     */
    void kickUser(User user);

    /**
     * Bans a user from the room by username
     * @param username the user to ban
     */
    void banUser(String username);

    /**
     * Bans a user from the room by username for a defined length
     * @param username the user to ban
     * @param length the length of the ban (in seconds)
     */
    void banUser(String username, int length);

    /**
     * Bans a user from the room by User instance
     * @param user the user to ban
     */
    void banUser(User user);

    /**
     * Bans a user from the room by User instance for a defined length
     * @param user the user to ban
     * @param length the length of the ban (in seconds)
     */
    void banUser(User user, int length);

    /**
     * Skips the current song playing in the room
     */
    void skipSong();

    // TODO song queue management; adding, removing & viewing

}
