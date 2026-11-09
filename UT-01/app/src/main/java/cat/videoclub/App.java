package cat.videoclub;

import cat.videoclub.model.RentalService;
import cat.videoclub.ui.*;
import java.time.Clock;
import javax.swing.*;

public final class App {
    private App() { }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
            catch (ReflectiveOperationException | UnsupportedLookAndFeelException ignored) { /* Look and feel predeterminado. */ }
            I18n.initialize();
            new MainFrame(RentalService.demo(Clock.systemDefaultZone())).setVisible(true);
        });
    }
}
