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

import java.io.IOException;

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
    void updub() throws IOException;

    /**
     * Makes the bot downdub the song
     */
    void downdub() throws IOException;

    /**
     * Skips the song
     */
    void skip() throws IOException;

}
