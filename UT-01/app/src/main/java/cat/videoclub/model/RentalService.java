package cat.videoclub.model;

import cat.validation.Validation;
import java.time.*;
import java.util.*;

/** Modelo en memoria, sin dependencia de Swing. Todas las mutaciones pasan por este servicio. */
public final class RentalService {
    private final Clock clock;
    private final List<User> users=new ArrayList<>();
    private final List<Game> games=new ArrayList<>();
    private final List<Rental> rentals=new ArrayList<>();
    private final List<Runnable> listeners=new ArrayList<>();
    private long sequence;
    public RentalService(Clock clock) { this.clock=Objects.requireNonNull(clock); }
    public LocalDate today() { return LocalDate.now(clock); }
    /** Previsión, no reserva: el identificador definitivo se asigna al registrar. */
    public long nextRegistrationNumber() { return sequence+1; }
    public List<User> users() { return List.copyOf(users); }
    public List<Game> games() { return List.copyOf(games); }
    public List<Rental> rentals() { return List.copyOf(rentals); }
    public void addListener(Runnable listener) { listeners.add(listener); }
    public void removeListener(Runnable listener) { listeners.remove(listener); }
    private void changed() { List.copyOf(listeners).forEach(Runnable::run); }
    public User addUser(String name, String contact) {
        User user=new User(++sequence,Validation.name(name),"","","",Validation.contact(contact),today());
        users.add(user); changed(); return user;
    }
    public User addUser(String name, String firstSurname, String secondSurname, String dni, String phone) {
        return addUser(name,firstSurname,secondSurname,dni,phone,today());
    }
    public User addUser(String name, String firstSurname, String secondSurname, String dni, String phone, LocalDate registrationDate) {
        if (registrationDate==null) fail("required");
        String validName=Validation.name(name);
        String validFirstSurname=Validation.required(firstSurname);
        String validSecondSurname=Validation.required(secondSurname);
        String validDni=Validation.dni(dni);
        String validPhone=Validation.phone(phone);
        User user=new User(++sequence,validName,validFirstSurname,validSecondSurname,validDni,validPhone,registrationDate);
        users.add(user); changed(); return user;
    }
    public Game addGame(String title, Map<Platform,Integer> stock) {
        return addGame(title,Genre.ACTION,stock);
    }
    public Game addGame(String title, Genre genre, Map<Platform,Integer> stock) {
        title=Validation.required(title);
        if (genre==null) fail("required");
        if (stock==null || stock.values().stream().anyMatch(v -> v==null || v<0)) fail("stock.invalid");
        Game game=new Game(++sequence,title,genre,stock); games.add(game); changed(); return game;
    }
    public void deleteUser(User user) {
        if (!users.contains(user)) fail("selection");
        if (rentals.stream().anyMatch(r -> !r.returned() && r.user().equals(user))) fail("active.user");
        users.remove(user); changed();
    }
    public void deleteGame(Game game) {
        if (!games.contains(game)) fail("selection");
        if (rentals.stream().anyMatch(r -> !r.returned() && r.game()==game)) fail("active.game");
        games.remove(game); changed();
    }
    public Rental rent(User user, Game game, Platform platform) { return rentAt(user,game,platform,today()); }
    private Rental rentAt(User user, Game game, Platform platform, LocalDate date) {
        if (!users.contains(user) || !games.contains(game) || platform==null) fail("selection");
        if (rentals.stream().anyMatch(r -> !r.returned() && r.user().equals(user) && r.game()==game && r.platform()==platform)) fail("duplicate");
        if (game.available(platform)<=0) fail("stock.empty");
        Rental rental=new Rental(++sequence,user,game,platform,date);
        game.change(platform,-1); rentals.add(rental); changed(); return rental;
    }
    public void returnRental(Rental rental) {
        if (!rentals.contains(rental)) fail("selection");
        if (rental.returned()) fail("returned");

        // ojo: si se repone antes de marcar, una segunda llamada sumaria stock
        rental.markReturned();
        rental.game().change(rental.platform(), 1);
        changed();
    }
    private static void fail(String key) { throw new IllegalArgumentException(key); }
    public static RentalService demo(Clock clock) {
        RentalService s=new RentalService(clock);
        User a=s.addUser("Marc Rosselló","marc@example.invalid");
        User b=s.addUser("Àngela Serra","angela@example.invalid");
        Game g=s.addGame("Horizon · Aventures",Map.of(Platform.PS5,2,Platform.SWITCH2,2));
        Game h=s.addGame("Forza · Velocitat",Map.of(Platform.XBOX,2,Platform.PC,2));
        s.rentAt(a,g,Platform.PS5,s.today().minusDays(9));
        s.rentAt(b,h,Platform.PC,s.today());
        return s;
    }
}
