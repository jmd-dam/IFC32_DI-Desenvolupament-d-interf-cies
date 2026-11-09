package cat.videoclub.ui;

import cat.videoclub.model.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import static cat.videoclub.ui.I18n.*;
import static cat.videoclub.ui.SwingComponents.*;

/** Listado de usuarios y botones de alta y baja. */
public final class UsersFrame extends JInternalFrame {
    private final DefaultTableModel model=model("name","user.dni.required","contact","user.registration");
    public final JTable table=table(model);
    private final JButton deleteButton;

    public UsersFrame(Runnable onAdd,Runnable onDelete) {
        super(t("menu.users"),true,true,true,true);
        deleteButton=button("user.delete",onDelete);
        configure(this,"menu.users",catalogPanel(table,button("user.new",onAdd),deleteButton));
    }
    public void refresh(RentalService service) {
        model.setRowCount(0);
        for (User u:service.users()) model.addRow(new Object[]{u.name(),u.dni(),u.contact(),java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu",LOCALE).format(u.registrationDate())});
    }
    public void updateActions(boolean selected) { deleteButton.setEnabled(selected); }
}
