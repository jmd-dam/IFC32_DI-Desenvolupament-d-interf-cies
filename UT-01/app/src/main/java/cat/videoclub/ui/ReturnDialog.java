package cat.videoclub.ui;

import cat.videoclub.model.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static cat.videoclub.ui.I18n.*;

/** Selección y devolución de alquileres pendientes. Diseño en ReturnDialog.form. */
public class ReturnDialog extends JDialog {
    private static final DateTimeFormatter DATE=DateTimeFormatter.ofPattern("dd/MM/uuuu",LOCALE);
    private RentalService service;
    private List<Rental> pending=List.of();
    private final Runnable observer=this::refresh;
    private boolean refreshing;

    public ReturnDialog() {
        initComponents();
        prepareForm();
        showDetails();
    }

    public ReturnDialog(Window owner,RentalService service,Rental selected) {
        super(owner,Dialog.ModalityType.MODELESS);
        this.service=java.util.Objects.requireNonNull(service);
        initComponents();
        prepareForm();
        service.addListener(observer);
        refresh();
        selectRental(selected);
        setLocationRelativeTo(owner);
    }

    private void prepareForm() {
        returnTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        returnTable.getTableHeader().setReorderingAllowed(false);
        returnTable.getTableHeader().setPreferredSize(new Dimension(0,28));
        returnTable.getRowSorter().setSortKeys(List.of(new RowSorter.SortKey(0,SortOrder.DESCENDING)));
        int[] widths={95,145,125,100,105};
        for (int i=0;i<widths.length;i++) returnTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        returnTable.setDefaultRenderer(Long.class,new javax.swing.table.DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table,Object value,boolean selected,boolean focus,int row,int column) {
                Object text=value instanceof Number?String.format(LOCALE,"%02d",((Number)value).longValue()):value;
                return super.getTableCellRendererComponent(table,text,selected,focus,row,column);
            }
        });
        returnTable.getSelectionModel().addListSelectionListener(e -> { if (!refreshing) showDetails(); });
        gameLabel.setLabelFor(gameValue); userLabel.setLabelFor(userValue);
        platformLabel.setLabelFor(platformValue); startLabel.setLabelFor(startValue);
        getRootPane().setDefaultButton(markReturned);
        getRootPane().registerKeyboardAction(e -> dispose(),KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
        getContentPane().setPreferredSize(new Dimension(590,342)); pack();
    }

    private Rental selectedRental() {
        int row=returnTable.getSelectedRow();
        if (row<0) return null;
        int index=returnTable.convertRowIndexToModel(row);
        return index>=0 && index<pending.size()?pending.get(index):null;
    }

    private void selectRental(Rental rental) {
        int index=pending.indexOf(rental);
        if (index>=0) {
            int row=returnTable.convertRowIndexToView(index);
            returnTable.setRowSelectionInterval(row,row);
        }
        showDetails();
    }

    private void refresh() {
        if (service==null) return;
        Rental selected=selectedRental();
        refreshing=true;
        try {
            pending=service.rentals().stream().filter(r -> !r.returned()).toList();
            DefaultTableModel model=(DefaultTableModel)returnTable.getModel();
            model.setRowCount(0);
            for (Rental r:pending) model.addRow(new Object[]{r.id(),r.game().title(),r.user().name(),t(r.platform().name()),DATE.format(r.start())});
        } finally { refreshing=false; }
        if (pending.contains(selected)) selectRental(selected);
        else if (returnTable.getRowCount()>0) returnTable.setRowSelectionInterval(0,0);
        showDetails();
    }

    private void showDetails() {
        Rental rental=selectedRental();
        gameValue.setText(rental==null?"—":rental.game().title());
        userValue.setText(rental==null?"—":rental.user().name());
        platformValue.setText(rental==null?"—":t(rental.platform().name()));
        startValue.setText(rental==null?"—":DATE.format(rental.start()));
        markReturned.setEnabled(service!=null && rental!=null && !rental.returned());
    }

    @Override public void dispose() {
        if (service!=null) service.removeListener(observer);
        super.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rentalScroll = new javax.swing.JScrollPane();
        returnTable = new javax.swing.JTable();
        detailsPanel = new javax.swing.JPanel();
        gameLabel = new javax.swing.JLabel();
        gameValue = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        userValue = new javax.swing.JLabel();
        platformLabel = new javax.swing.JLabel();
        platformValue = new javax.swing.JLabel();
        startLabel = new javax.swing.JLabel();
        startValue = new javax.swing.JLabel();
        markReturned = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(cat.videoclub.ui.I18n.t("return.title"));
        setName("return.dialog"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(null);

        returnTable.setModel(new javax.swing.table.DefaultTableModel(new String[]{cat.videoclub.ui.I18n.t("rental.number"),cat.videoclub.ui.I18n.t("rental.game"),cat.videoclub.ui.I18n.t("user"),cat.videoclub.ui.I18n.t("platform"),cat.videoclub.ui.I18n.t("start")},0) { public boolean isCellEditable(int row,int column) { return false; } public Class<?> getColumnClass(int column) { return column==0?Long.class:String.class; } });
        returnTable.setAutoCreateRowSorter(true);
        returnTable.setRowHeight(28);
        returnTable.setFillsViewportHeight(true);
        returnTable.setGridColor(new java.awt.Color(205, 218, 225));
        rentalScroll.setViewportView(returnTable);

        getContentPane().add(rentalScroll);
        rentalScroll.setBounds(14, 14, 562, 180);

        detailsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        detailsPanel.setLayout(null);

        gameLabel.setText(cat.videoclub.ui.I18n.t("game"));
        detailsPanel.add(gameLabel);
        gameLabel.setBounds(12, 8, 90, 24);

        gameValue.setName("return.game"); // NOI18N
        detailsPanel.add(gameValue);
        gameValue.setBounds(104, 8, 260, 24);

        userLabel.setText(cat.videoclub.ui.I18n.t("user"));
        detailsPanel.add(userLabel);
        userLabel.setBounds(12, 33, 90, 24);

        userValue.setName("return.user"); // NOI18N
        detailsPanel.add(userValue);
        userValue.setBounds(104, 33, 260, 24);

        platformLabel.setText(cat.videoclub.ui.I18n.t("platform"));
        detailsPanel.add(platformLabel);
        platformLabel.setBounds(12, 58, 90, 24);

        platformValue.setName("return.platform"); // NOI18N
        detailsPanel.add(platformValue);
        platformValue.setBounds(104, 58, 260, 24);

        startLabel.setText(cat.videoclub.ui.I18n.t("start"));
        detailsPanel.add(startLabel);
        startLabel.setBounds(12, 83, 90, 24);

        startValue.setName("return.start"); // NOI18N
        detailsPanel.add(startValue);
        startValue.setBounds(104, 83, 260, 24);

        markReturned.setText(cat.videoclub.ui.I18n.t("return.mark"));
        markReturned.setName("return.mark"); // NOI18N
        markReturned.setBackground(new java.awt.Color(46, 153, 80));
        markReturned.setForeground(new java.awt.Color(255, 255, 255));
        markReturned.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markReturnedActionPerformed(evt);
            }
        });
        detailsPanel.add(markReturned);
        markReturned.setBounds(376, 14, 174, 32);

        closeButton.setText(cat.videoclub.ui.I18n.t("return.close"));
        closeButton.setName("return.close"); // NOI18N
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        detailsPanel.add(closeButton);
        closeButton.setBounds(442, 80, 108, 30);

        getContentPane().add(detailsPanel);
        detailsPanel.setBounds(14, 206, 562, 122);
    }// </editor-fold>//GEN-END:initComponents

    private void markReturnedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markReturnedActionPerformed
        Rental rental=selectedRental();
        if (service==null || rental==null) return;
        int answer=JOptionPane.showConfirmDialog(this,t("confirm.return"),t("confirm"),JOptionPane.YES_NO_OPTION);
        if (answer!=JOptionPane.YES_OPTION) return;
        try { service.returnRental(rental); }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,t(ex.getMessage()),t("error"),JOptionPane.WARNING_MESSAGE);
            refresh();
        }
    }//GEN-LAST:event_markReturnedActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JLabel gameLabel;
    private javax.swing.JLabel gameValue;
    private javax.swing.JButton markReturned;
    private javax.swing.JLabel platformLabel;
    private javax.swing.JLabel platformValue;
    private javax.swing.JScrollPane rentalScroll;
    private javax.swing.JTable returnTable;
    private javax.swing.JLabel startLabel;
    private javax.swing.JLabel startValue;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userValue;
    // End of variables declaration//GEN-END:variables
}
