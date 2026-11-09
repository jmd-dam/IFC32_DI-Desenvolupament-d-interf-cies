package cat.videoclub.ui;

import cat.validation.Validation;
import cat.videoclub.model.RentalService;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import javax.swing.*;
import static cat.videoclub.ui.I18n.*;

/** Alta de usuarios. El diseño se mantiene en UserDialog.form. */
public class UserDialog extends JDialog {
    private RentalService service;

    public UserDialog() {
        initComponents();
        prepareForm(LocalDate.now());
        saveButton.setEnabled(false);
    }

    public UserDialog(Window owner,RentalService service) {
        super(owner,Dialog.ModalityType.DOCUMENT_MODAL);
        this.service=java.util.Objects.requireNonNull(service);
        initComponents();
        prepareForm(service.today());
        setLocationRelativeTo(owner);
    }

    private void prepareForm(LocalDate date) {
        registrationDate.setDate(java.util.Date.from(date.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
        JTextField editor=(JTextField)registrationDate.getDateEditor().getUiComponent();
        editor.setName("registrationDate"); editor.setEditable(false); editor.setEnabled(false);
        JButton calendar=registrationDate.getCalendarButton();
        calendar.setName("registrationCalendar"); calendar.setToolTipText(t("calendar.open"));
        calendar.getAccessibleContext().setAccessibleName(t("calendar.open"));
        calendar.addActionListener(e -> { editor.setEnabled(true); editor.setEditable(true); });
        nameLabel.setLabelFor(nameField);
        firstSurnameLabel.setLabelFor(firstSurnameField);
        secondSurnameLabel.setLabelFor(secondSurnameField);
        dniLabel.setLabelFor(dniField);
        phoneLabel.setLabelFor(phoneField);
        registrationLabel.setLabelFor(registrationDate);
        getRootPane().setDefaultButton(saveButton);
        getRootPane().registerKeyboardAction(e -> dispose(),KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
        getContentPane().setPreferredSize(new Dimension(430,320));
        pack();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        firstSurnameLabel = new javax.swing.JLabel();
        firstSurnameField = new javax.swing.JTextField();
        secondSurnameLabel = new javax.swing.JLabel();
        secondSurnameField = new javax.swing.JTextField();
        dniLabel = new javax.swing.JLabel();
        dniField = new javax.swing.JTextField();
        phoneLabel = new javax.swing.JLabel();
        phoneField = new javax.swing.JTextField();
        registrationLabel = new javax.swing.JLabel();
        registrationDate = new com.toedter.calendar.JDateChooser();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(cat.videoclub.ui.I18n.t("user.new"));
        setName("user.new"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(null);

        nameLabel.setText(cat.videoclub.ui.I18n.t("user.name.required"));
        getContentPane().add(nameLabel);
        nameLabel.setBounds(16, 20, 110, 30);

        nameField.setName("name"); // NOI18N
        getContentPane().add(nameField);
        nameField.setBounds(128, 20, 282, 30);

        firstSurnameLabel.setText(cat.videoclub.ui.I18n.t("user.surname1.required"));
        getContentPane().add(firstSurnameLabel);
        firstSurnameLabel.setBounds(16, 58, 110, 30);

        firstSurnameField.setName("firstSurname"); // NOI18N
        getContentPane().add(firstSurnameField);
        firstSurnameField.setBounds(128, 58, 282, 30);

        secondSurnameLabel.setText(cat.videoclub.ui.I18n.t("user.surname2.required"));
        getContentPane().add(secondSurnameLabel);
        secondSurnameLabel.setBounds(16, 96, 110, 30);

        secondSurnameField.setName("secondSurname"); // NOI18N
        getContentPane().add(secondSurnameField);
        secondSurnameField.setBounds(128, 96, 282, 30);

        dniLabel.setText(cat.videoclub.ui.I18n.t("user.dni.required"));
        getContentPane().add(dniLabel);
        dniLabel.setBounds(16, 134, 110, 30);

        dniField.setName("dni"); // NOI18N
        getContentPane().add(dniField);
        dniField.setBounds(128, 134, 282, 30);

        phoneLabel.setText(cat.videoclub.ui.I18n.t("user.phone.required"));
        getContentPane().add(phoneLabel);
        phoneLabel.setBounds(16, 172, 110, 30);

        phoneField.setName("phone"); // NOI18N
        getContentPane().add(phoneField);
        phoneField.setBounds(128, 172, 282, 30);

        registrationLabel.setText(cat.videoclub.ui.I18n.t("user.registration"));
        getContentPane().add(registrationLabel);
        registrationLabel.setBounds(16, 210, 110, 30);

        registrationDate.setName("registrationDateChooser"); // NOI18N
        registrationDate.setLocale(cat.videoclub.ui.I18n.LOCALE);
        registrationDate.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(registrationDate);
        registrationDate.setBounds(128, 210, 282, 30);

        saveButton.setText("\u2713  " + cat.videoclub.ui.I18n.t("form.confirm"));
        saveButton.setName("save"); // NOI18N
        saveButton.setBackground(new java.awt.Color(46, 153, 80));
        saveButton.setForeground(new java.awt.Color(255, 255, 255));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        getContentPane().add(saveButton);
        saveButton.setBounds(128, 270, 140, 36);

        cancelButton.setText("\u2715  " + cat.videoclub.ui.I18n.t("game.cancel"));
        cancelButton.setName("cancel"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        getContentPane().add(cancelButton);
        cancelButton.setBounds(282, 270, 128, 36);
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if (service==null) return;
        try {
            service.addUser(nameField.getText(),firstSurnameField.getText(),secondSurnameField.getText(),
                dniField.getText(),phoneField.getText(),Validation.date(((JTextField)registrationDate.getDateEditor().getUiComponent()).getText()));
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,t(ex.getMessage()),t("error"),JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField dniField;
    private javax.swing.JLabel dniLabel;
    private javax.swing.JTextField firstSurnameField;
    private javax.swing.JLabel firstSurnameLabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField phoneField;
    private javax.swing.JLabel phoneLabel;
    private com.toedter.calendar.JDateChooser registrationDate;
    private javax.swing.JLabel registrationLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField secondSurnameField;
    private javax.swing.JLabel secondSurnameLabel;
    // End of variables declaration//GEN-END:variables
}
