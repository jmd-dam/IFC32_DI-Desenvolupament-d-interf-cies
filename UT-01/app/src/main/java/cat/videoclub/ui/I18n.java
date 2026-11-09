package cat.videoclub.ui;

import java.util.*;
import java.text.MessageFormat;
import javax.swing.UIManager;

public final class I18n {
    public static final Locale LOCALE=Locale.forLanguageTag("ca-ES");
    private static final ResourceBundle BUNDLE=ResourceBundle.getBundle("cat.videoclub.ui.Bundle",LOCALE);
    private I18n() { }
    public static String t(String key) { return BUNDLE.getString(key); }
    public static String format(String key,Object... args) { return new MessageFormat(t(key),LOCALE).format(args); }
    public static void initialize() {
        Locale.setDefault(LOCALE);
        UIManager.put("OptionPane.yesButtonText",t("yes"));
        UIManager.put("OptionPane.noButtonText",t("no"));
        UIManager.put("OptionPane.cancelButtonText",t("cancel"));
        UIManager.put("OptionPane.okButtonText",t("ok"));
    }
}
