package cat.videoclub.model;

import java.time.LocalDate;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/** Datos de identificación y fecha de alta de una persona. */
public record User(long id, String givenName, String firstSurname, String secondSurname,
                   String dni, String contact, LocalDate registrationDate) {
    /** Compatibilidad con usuarios creados con un nombre completo y un contacto. */
    public User(long id, String name, String contact) {
        this(id,name,"","","",contact,LocalDate.now());
    }
    public String name() {
        return Stream.of(givenName,firstSurname,secondSurname)
            .filter(part -> !part.isBlank()).collect(Collectors.joining(" "));
    }
    @Override public String toString() { return name(); }
}
