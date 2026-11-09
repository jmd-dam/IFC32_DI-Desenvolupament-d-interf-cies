package cat.videoclub.model;

import java.util.*;

/** Stock encapsulado: únicamente el servicio puede modificarlo. */
public final class Game {
    private final long id;
    private final String title;
    private final Genre genre;
    private final EnumMap<Platform,Integer> stock=new EnumMap<>(Platform.class);
    Game(long id, String title, Genre genre, Map<Platform,Integer> initial) {
        this.id=id; this.title=title; this.genre=Objects.requireNonNull(genre);
        for (Platform p:Platform.values()) stock.put(p,initial.getOrDefault(p,0));
    }
    public long id() { return id; }
    public String title() { return title; }
    public Genre genre() { return genre; }
    public int available(Platform p) { return stock.get(p); }
    void change(Platform p, int delta) { stock.put(p, stock.get(p)+delta); }
    @Override public String toString() { return title; }
}
