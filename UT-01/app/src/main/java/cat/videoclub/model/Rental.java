package cat.videoclub.model;

import java.time.LocalDate;

/** Conserva las referencias históricas incluso si el catálogo elimina una entidad devuelta. */
public final class Rental {
    private final long id;
    private final User user;
    private final Game game;
    private final Platform platform;
    private final LocalDate start;
    private boolean returned;
    Rental(long id, User user, Game game, Platform platform, LocalDate start) {
        this.id=id; this.user=user; this.game=game; this.platform=platform; this.start=start;
    }
    public long id() { return id; }
    public User user() { return user; }
    public Game game() { return game; }
    public Platform platform() { return platform; }
    public LocalDate start() { return start; }
    public LocalDate due() { return start.plusDays(7); }
    public boolean returned() { return returned; }
    public boolean overdue(LocalDate today) { return !returned && due().isBefore(today); }
    void markReturned() { returned=true; }
}
