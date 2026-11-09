package cat.validation;

/** Biblioteca independiente de Swing: devuelve valores normalizados o claves de error. */
public final class Validation {
    private Validation() { }
    public static String required(String value) {
        if (value == null || value.strip().isEmpty()) throw new IllegalArgumentException("required");
        return value.strip();
    }
    public static String name(String value) {
        String text=required(value);
        if (!Character.isUpperCase(text.codePointAt(0))) throw new IllegalArgumentException("name.uppercase");
        return text;
    }
    public static String contact(String value) {
        String text=required(value);
        if (!text.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+") && !text.matches("\\+?[0-9][0-9 ()-]{5,23}[0-9]"))
            throw new IllegalArgumentException("contact.invalid");
        return text;
    }
    public static String dni(String value) {
        String text=required(value).toUpperCase(java.util.Locale.ROOT);

        // letra = numero % 23 sobre TRWAGMYFPDXBNJZSQVHLCKE
        // probe con 12345678Z
        boolean formatoOk = text.matches("[0-9]{8}[A-Z]");
        if (!formatoOk) throw new IllegalArgumentException("dni.invalid");

        int numero = Integer.parseInt(text.substring(0, 8));
        char esperada = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero % 23);
        if (text.charAt(8) != esperada) throw new IllegalArgumentException("dni.invalid");
        return text;
    }
    public static String phone(String value) {
        String text=required(value);
        if (!text.matches("\\+?[0-9][0-9 ()-]{5,23}[0-9]"))
            throw new IllegalArgumentException("phone.invalid");
        return text;
    }
    public static java.time.LocalDate date(String value) {
        String text=required(value);
        try {
            return java.time.LocalDate.parse(text,java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(java.time.format.ResolverStyle.STRICT));
        } catch (java.time.format.DateTimeParseException ex) {
            throw new IllegalArgumentException("date.invalid");
        }
    }
    public static int nonNegative(String value) {
        String text=required(value);
        try {
            if (!text.matches("[0-9]+")) throw new NumberFormatException();
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) { throw new IllegalArgumentException("stock.invalid"); }
    }
}
