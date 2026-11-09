package cat.videoclub.ui;

import cat.videoclub.model.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import static cat.videoclub.ui.I18n.*;
import static cat.videoclub.ui.SwingComponents.*;

/** Listado de alquileres, acciones y representación visual de su estado. */
public final class RentalsFrame extends JInternalFrame {
    public static final Color OVERDUE=new Color(255,193,193), RETURNED=new Color(181,246,204);
    private static final java.time.format.DateTimeFormatter DATE=java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu",LOCALE);
    private final DefaultTableModel model=model("rental.number","rental.game","rental.user","platform","start","rental.due","genre","status");
    public final JTable table=table(model);
    private final JButton rentButton, returnButton;
    private final JLabel footer=new JLabel();

    public RentalsFrame(RentalService service,Runnable onRent,Runnable onReturn) {
        super(t("menu.rentals"),true,true,true,true);
        rentButton=button("rent.new",onRent); returnButton=button("rent.return",onReturn);
        JPanel panel=new JPanel(null), buttons=actions(rentButton,returnButton);
        table.setRowHeight(36);
        table.setShowGrid(true); table.setGridColor(new Color(205,218,225));
        table.setIntercellSpacing(new Dimension(1,1));
        table.getTableHeader().setPreferredSize(new Dimension(0,32));
        int[] widths={90,170,185,100,115,115,110,100};
        for (int i=0;i<widths.length;i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
            table.getColumnModel().getColumn(i).setMinWidth(70);
        }
        // Conservar el estado en el modelo, pero mostrarlo únicamente mediante el color.
        table.removeColumn(table.getColumnModel().getColumn(7));
        table.getRowSorter().setSortKeys(java.util.List.of(new RowSorter.SortKey(0,SortOrder.DESCENDING)));
        DefaultTableCellRenderer renderer=new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table,Object value,boolean selected,boolean focus,int row,int col) {
                Object displayed=col==0 && value instanceof Number?String.format(LOCALE,"%02d",((Number)value).longValue()):value;
                Component c=super.getTableCellRendererComponent(table,displayed,selected,focus,row,col);
                setHorizontalAlignment(LEFT);
                Rental r=service.rentals().get(table.convertRowIndexToModel(row));
                c.setBackground(r.returned()?RETURNED:r.overdue(service.today())?OVERDUE:selected?new Color(219,231,250):Color.WHITE);
                c.setForeground(new Color(25,35,50));
                if (c instanceof JComponent jc) jc.setBorder(selected?BorderFactory.createMatteBorder(1,0,1,0,new Color(55,100,160)):BorderFactory.createEmptyBorder(1,4,1,4));
                return c;
            }
        };
        table.setDefaultRenderer(Object.class,renderer);
        table.setDefaultRenderer(Long.class,renderer);
        JScrollPane scroll=new JScrollPane(table);
        panel.add(buttons); panel.add(scroll); panel.add(footer);
        manual(panel,() -> {
            buttons.setBounds(10,10,panel.getWidth()-20,34);
            scroll.setBounds(10,54,panel.getWidth()-20,Math.max(1,panel.getHeight()-96));
            footer.setBounds(10,panel.getHeight()-32,panel.getWidth()-20,24);
        });
        configure(this,"menu.rentals",panel);
    }
    public void refresh(RentalService service) {
        model.setRowCount(0);
        for (Rental r:service.rentals()) model.addRow(new Object[]{r.id(),r.game().title(),r.user().name(),t(r.platform().name()),DATE.format(r.start()),DATE.format(r.due()),t("genre."+r.game().genre().name()),t(r.returned()?"state.returned":r.overdue(service.today())?"state.overdue":"state.active")});
        footer.setText(format("rentals.total",service.rentals().size())+"   ·   "+t("legend"));
    }
    public void updateActions(boolean canRent,boolean canReturn) {
        rentButton.setEnabled(canRent); returnButton.setEnabled(canReturn);
    }
}
