package io.sponges.dubtrack4j.framework;

public interface Song {

    /**
     * The id of the song
     * @return song id
     */
    String getId();

    /**
     * The user who is playing this song
     * @return User instance
     */
    User getUser();

    /**
     * The room that the song is playing in
     * @return Room instance
     */
    Room getRoom();


    /**
     * Song related information
     * @return SongInfo instance
     * TODO move this into this interface?
     */
    SongInfo getSongInfo();

    /**
     * The number of updubs the song has received
     * @return updubs
     */
    int getUpdubs();

    /**
     * The number of downdubs the song has received
     * @return downdubs
     */
    int getDowndubs();

    /**
     * Makes the bot updub the song
     */
    void updub();

    /**
     * Makes the bot downdub the song
     */
    void downdub();

    /**
     * Skips the song
     */
    void skip();

}
