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

public class SongInfo {

    private final String name;
    private final double length;
    private final SourceType sourceType;

    private String youtubeId = null;
    private String soundcloudId = null;

    public SongInfo(String name, double length, SourceType sourceType) {
        this.name = name;
        this.length = length;
        this.sourceType = sourceType;
    }

    /**
     * The name of the song
     * @return song name
     */
    public String getName() {
        return name;
    }

    /**
     * The length of the song (seconds)
     * @return song length
     */
    public double getLength() {
        return length;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getSoundcloudId() {
        return soundcloudId;
    }

    public void setSoundcloudId(String soundcloudId) {
        this.soundcloudId = soundcloudId;
    }

    public enum SourceType {

        YOUTUBE, SOUNDCLOUD

    }


}
