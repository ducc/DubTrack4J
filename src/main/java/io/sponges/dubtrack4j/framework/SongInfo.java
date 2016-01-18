package io.sponges.dubtrack4j.framework;

public class SongInfo {

    private final String name;
    private final double length;

    public SongInfo(String name, double length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }
}
