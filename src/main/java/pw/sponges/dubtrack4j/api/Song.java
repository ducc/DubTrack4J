package pw.sponges.dubtrack4j.api;

public class Song {

    private final String id;
    private final User user;
    private final Room room;
    private final SongInfo songInfo;

    private int updubs, downdubs = 0;

    public Song(String id, User user, Room room, SongInfo songInfo) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.songInfo = songInfo;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public int getUpdubs() {
        return updubs;
    }

    public void setUpdubs(int updubs) {
        this.updubs = updubs;
    }

    public void addUpdub() {
        updubs++;
    }

    public void removeUpdub() {
        updubs--;
    }

    public int getDowndubs() {
        return downdubs;
    }

    public void setDowndubs(int downdubs) {
        this.downdubs = downdubs;
    }

    public void addDowndub() {
        downdubs++;
    }

    public void removeDowndub() {
        downdubs--;
    }
}
