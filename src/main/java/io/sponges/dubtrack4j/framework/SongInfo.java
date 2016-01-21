package io.sponges.dubtrack4j.framework;

public class SongInfo {

    private final String name;
    private final double length;

    public SongInfo(String name, double length) {
        this.name = name;
        this.length = length;
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

    // TODO more song info
    // e.g. description, thumbnail, youtube link, likes, dislikes, author etc

}
