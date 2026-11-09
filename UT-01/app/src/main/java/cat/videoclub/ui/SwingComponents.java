package cat.videoclub.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.*;
import static cat.videoclub.ui.I18n.*;

/** Utilidades de componentes compartidas; todos los paneles propios usan null layout. */
final class SwingComponents {
    private SwingComponents() { }
    static DefaultTableModel model(String... keys) {
        return new DefaultTableModel(Arrays.stream(keys).map(I18n::t).toArray(),0) {
            @Override public boolean isCellEditable(int row,int col) { return false; }
            @Override public Class<?> getColumnClass(int col) { return getRowCount()>0?getValueAt(0,col).getClass():Object.class; }
        };
    }
    static JTable table(DefaultTableModel m) {
        JTable table=new JTable(m); table.setRowHeight(32); table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true); table.getTableHeader().setReorderingAllowed(false); table.setFillsViewportHeight(true);
        return table;
    }
    static JButton button(String key,Runnable action) {
        JButton b=new JButton(t(key)); b.setName(key); b.addActionListener(e -> action.run()); return b;
    }
    /** Posicionamiento manual: panel sin LayoutManager y coordenadas recalculadas al redimensionar. */
    static void manual(JPanel panel,Runnable position) {
        panel.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) { position.run(); }
        });
    }
    static JPanel actions(JButton... buttons) {
        JPanel p=new JPanel(null); int x=0;
        for (JButton b:buttons) { int width=Math.max(120,b.getPreferredSize().width+12); b.setBounds(x,0,width,32); p.add(b); x+=width+8; }
        p.setPreferredSize(new Dimension(x,34)); return p;
    }
    static JPanel catalogPanel(JTable table,JButton add,JButton delete) {
        JPanel p=new JPanel(null), buttons=actions(add,delete); JScrollPane scroll=new JScrollPane(table);
        p.add(buttons); p.add(scroll);
        manual(p,() -> {
            buttons.setBounds(10,10,p.getWidth()-20,34);
            scroll.setBounds(10,54,p.getWidth()-20,Math.max(1,p.getHeight()-64));
        }); return p;
    }
    static void configure(JInternalFrame frame,String key,JPanel content) {
        frame.setName(key);
        frame.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300,180));
        content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        frame.setContentPane(content);
    }
}
