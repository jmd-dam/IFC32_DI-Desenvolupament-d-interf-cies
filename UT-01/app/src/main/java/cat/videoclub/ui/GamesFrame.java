package cat.videoclub.ui;

import cat.videoclub.model.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import static cat.videoclub.ui.I18n.*;
import static cat.videoclub.ui.SwingComponents.*;

/** Listado de videojuegos y botones de alta y baja. */
public final class GamesFrame extends JInternalFrame {
    private final DefaultTableModel model=model("title","PS5","SWITCH2","XBOX","PC");
    public final JTable table=table(model);
    private final JButton deleteButton;

    public GamesFrame(Runnable onAdd,Runnable onDelete) {
        super(t("menu.games"),true,true,true,true);
        deleteButton=button("game.delete",onDelete);
        configure(this,"menu.games",catalogPanel(table,button("game.new",onAdd),deleteButton));
    }
    public void refresh(RentalService service) {
        model.setRowCount(0);
        for (Game u:service.games()) model.addRow(new Object[]{u.title(),u.available(Platform.PS5),u.available(Platform.SWITCH2),u.available(Platform.XBOX),u.available(Platform.PC)});
    }
    public void updateActions(boolean selected) { deleteButton.setEnabled(selected); }
    DefaultTableModel tableModel() { return model; }
}
