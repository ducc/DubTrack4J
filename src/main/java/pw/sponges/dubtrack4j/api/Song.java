package pw.sponges.dubtrack4j.api;

public interface Song {

    String getId();

    User getUser();

    Room getRoom();

    SongInfo getSongInfo();

    int getUpdubs();

    void setUpdubs(int updubs);

    int getDowndubs();

    void setDowndubs(int downdubs);

    void updub();

    void downdub();

}
