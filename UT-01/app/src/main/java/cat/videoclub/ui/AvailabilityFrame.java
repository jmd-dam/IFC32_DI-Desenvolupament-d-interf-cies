package cat.videoclub.ui;

import cat.videoclub.model.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import static cat.videoclub.ui.I18n.*;
import static cat.videoclub.ui.SwingComponents.*;

/** Segunda vista del modelo del catálogo: disponibilidad siempre sincronizada. */
public final class AvailabilityFrame extends JInternalFrame {
    public final JTable table;

    public AvailabilityFrame(DefaultTableModel gameModel) {
        super(t("availability"),true,true,true,true);
        table=table(gameModel); table.setName("availability");
        JPanel panel=new JPanel(null); JLabel label=new JLabel(t("availability"));
        JScrollPane scroll=new JScrollPane(table); panel.add(label); panel.add(scroll);
        manual(panel,() -> {
            label.setBounds(10,10,panel.getWidth()-20,24);
            scroll.setBounds(10,44,panel.getWidth()-20,Math.max(1,panel.getHeight()-54));
        });
        configure(this,"availability",panel);
    }
}
