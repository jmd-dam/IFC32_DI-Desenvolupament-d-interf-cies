package cat.videoclub.ui;

/**
 * Formulario auxiliar del GUI Builder: el menú está en Other Components.
 * MainFrame incorpora el JMenu a su barra; este panel no se muestra.
 */
public final class HelpMenuForm extends javax.swing.JPanel {
    private Runnable onAbout = () -> { };

    public HelpMenuForm() {
        initComponents();
    }

    public HelpMenuForm(Runnable onAbout) {
        this();
        this.onAbout = java.util.Objects.requireNonNull(onAbout);
    }

    public javax.swing.JMenu getHelpMenu() {
        return helpMenu;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        helpMenu.setText(cat.videoclub.ui.I18n.t("menu.help"));

        aboutMenuItem.setText(cat.videoclub.ui.I18n.t("about"));
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        onAbout.run();
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu helpMenu;
    // End of variables declaration//GEN-END:variables
}
