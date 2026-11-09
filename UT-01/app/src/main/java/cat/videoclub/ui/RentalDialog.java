package cat.videoclub.ui;

import cat.videoclub.model.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import javax.swing.*;
import static cat.videoclub.ui.I18n.*;

/** Diálogo de alquiler con metadatos editables en el GUI Builder de NetBeans. */
public class RentalDialog extends JDialog {
    private static final DateTimeFormatter DATE=DateTimeFormatter.ofPattern("dd/MM/uuuu",LOCALE);
    public final EnumMap<Platform,JRadioButton> platforms=new EnumMap<>(Platform.class);
    private final EnumMap<Platform,JLabel> stockValues=new EnumMap<>(Platform.class);
    private final ButtonGroup group=new ButtonGroup();
    private RentalService service;
    private final Runnable listener=this::sync;
    private boolean syncing;

    /** Constructor sin argumentos requerido para la previsualización del formulario. */
    public RentalDialog() {
        initComponents();
        configureForm();
    }

    public RentalDialog(Window owner,RentalService service,User user,Game game) {
        super(owner,Dialog.ModalityType.MODELESS);
        initComponents();
        configureForm();
        this.service=java.util.Objects.requireNonNull(service);
        sync();
        if (user!=null && service.users().contains(user)) users.setSelectedItem(user);
        if (game!=null && service.games().contains(game)) games.setSelectedItem(game);
        updateStock();
        service.addListener(listener);
        setLocationRelativeTo(owner);
    }

    private void configureForm() {
        setName("rent.new"); users.setName("users"); games.setName("games");
        registration.setName("registration"); start.setName("start"); due.setName("due");
        save.setName("save"); cancel.setName("cancel");
        platforms.put(Platform.PC,pcRadio); platforms.put(Platform.PS5,ps5Radio);
        platforms.put(Platform.XBOX,xboxRadio); platforms.put(Platform.SWITCH2,switchRadio);
        platforms.forEach((platform,radio) -> { group.add(radio); radio.setName(platform.name()); });
        stockValues.put(Platform.PS5,ps5Units); stockValues.put(Platform.XBOX,xboxUnits);
        stockValues.put(Platform.PC,pcUnits); stockValues.put(Platform.SWITCH2,switchUnits);
        registration.setToolTipText(t("rental.numberHint"));
        userLabel.setLabelFor(users); gameLabel.setLabelFor(games);
        startLabel.setLabelFor(start); dueLabel.setLabelFor(due); numberLabel.setLabelFor(registration);
        save.setText("\u2713  "+t("form.confirm")); cancel.setText("\u2715  "+t("game.cancel"));
        save.setBackground(new Color(46,153,80)); save.setForeground(Color.WHITE); save.setOpaque(true);
        getRootPane().setDefaultButton(save);
        getRootPane().registerKeyboardAction(e -> dispose(),KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
        getContentPane().setPreferredSize(new Dimension(720,426)); pack();
        save.setEnabled(false);
    }

    private void sync() {
        if (service==null) return;
        syncing=true;
        try {
            User user=(User)users.getSelectedItem(); Game game=(Game)games.getSelectedItem();
            users.setModel(new DefaultComboBoxModel<>(service.users().toArray(User[]::new)));
            games.setModel(new DefaultComboBoxModel<>(service.games().toArray(Game[]::new)));
            if (user!=null && service.users().contains(user)) users.setSelectedItem(user);
            if (game!=null && service.games().contains(game)) games.setSelectedItem(game);
        } finally { syncing=false; }
        updateStock();
    }

    private void updateStock() {
        if (service==null || syncing) return;
        Game game=(Game)games.getSelectedItem(); boolean any=false;
        for (Platform platform:Platform.values()) {
            int units=game==null?0:game.available(platform);
            JRadioButton radio=platforms.get(platform);
            radio.setText(t(platform.name())+"   ("+format(units==1?"rental.unitAvailable":"rental.unitsAvailable",units)+")");
            radio.setEnabled(units>0);
            if (units==0 && radio.isSelected()) group.clearSelection();
            stockValues.get(platform).setText(Integer.toString(units));
            any|=units>0;
        }
        if (group.getSelection()==null) {
            Platform[] order={Platform.PC,Platform.PS5,Platform.XBOX,Platform.SWITCH2};
            for (Platform p:order) if (platforms.get(p).isEnabled()) { platforms.get(p).setSelected(true); break; }
        }
        genreValue.setText(game==null?"—":t("genre."+game.genre().name()));
        registration.setText(String.format(LOCALE,"%02d",service.nextRegistrationNumber()));
        start.setText(DATE.format(service.today())); due.setText(DATE.format(service.today().plusDays(7)));
        selectUser.setEnabled(users.getItemCount()>0); selectGame.setEnabled(games.getItemCount()>0);
        save.setEnabled(users.getSelectedItem()!=null && game!=null && any);
    }

    private void submit() {
        if (service==null) return;
        Platform platform=platforms.entrySet().stream().filter(e -> e.getValue().isSelected())
            .map(java.util.Map.Entry::getKey).findFirst().orElse(null);
        try { service.rent((User)users.getSelectedItem(),(Game)games.getSelectedItem(),platform); dispose(); }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,t(ex.getMessage()),t("error"),JOptionPane.WARNING_MESSAGE);
            updateStock();
        }
    }

    @Override public void dispose() {
        if (service!=null) service.removeListener(listener);
        super.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dataPanel = new javax.swing.JPanel();
        numberLabel = new javax.swing.JLabel();
        registration = new javax.swing.JTextField();
        userLabel = new javax.swing.JLabel();
        users = new javax.swing.JComboBox<>();
        selectUser = new javax.swing.JButton();
        gameLabel = new javax.swing.JLabel();
        games = new javax.swing.JComboBox<>();
        selectGame = new javax.swing.JButton();
        startLabel = new javax.swing.JLabel();
        start = new javax.swing.JTextField();
        dueLabel = new javax.swing.JLabel();
        due = new javax.swing.JTextField();
        platformPanel = new javax.swing.JPanel();
        pcRadio = new javax.swing.JRadioButton();
        ps5Radio = new javax.swing.JRadioButton();
        xboxRadio = new javax.swing.JRadioButton();
        switchRadio = new javax.swing.JRadioButton();
        infoPanel = new javax.swing.JPanel();
        genreLabel = new javax.swing.JLabel();
        genreValue = new javax.swing.JLabel();
        stockLabel = new javax.swing.JLabel();
        ps5UnitsLabel = new javax.swing.JLabel();
        ps5Units = new javax.swing.JLabel();
        xboxUnitsLabel = new javax.swing.JLabel();
        xboxUnits = new javax.swing.JLabel();
        pcUnitsLabel = new javax.swing.JLabel();
        pcUnits = new javax.swing.JLabel();
        switchUnitsLabel = new javax.swing.JLabel();
        switchUnits = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(cat.videoclub.ui.I18n.t("rental.register"));
        setResizable(false);
        getContentPane().setLayout(null);

        dataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(cat.videoclub.ui.I18n.t("rental.data")));
        dataPanel.setLayout(null);

        numberLabel.setText(cat.videoclub.ui.I18n.t("rental.number"));
        dataPanel.add(numberLabel);
        numberLabel.setBounds(14, 24, 122, 30);

        registration.setEditable(false);
        registration.setEnabled(false);
        dataPanel.add(registration);
        registration.setBounds(138, 24, 206, 30);

        userLabel.setText(cat.videoclub.ui.I18n.t("user"));
        dataPanel.add(userLabel);
        userLabel.setBounds(14, 65, 320, 22);

        users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersActionPerformed(evt);
            }
        });
        dataPanel.add(users);
        users.setBounds(14, 91, 232, 30);

        selectUser.setText(cat.videoclub.ui.I18n.t("rental.select"));
        selectUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectUserActionPerformed(evt);
            }
        });
        dataPanel.add(selectUser);
        selectUser.setBounds(258, 91, 86, 30);

        gameLabel.setText(cat.videoclub.ui.I18n.t("rental.chosenGame"));
        dataPanel.add(gameLabel);
        gameLabel.setBounds(14, 138, 320, 22);

        games.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gamesActionPerformed(evt);
            }
        });
        dataPanel.add(games);
        games.setBounds(14, 164, 232, 30);

        selectGame.setText(cat.videoclub.ui.I18n.t("rental.select"));
        selectGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectGameActionPerformed(evt);
            }
        });
        dataPanel.add(selectGame);
        selectGame.setBounds(258, 164, 86, 30);

        startLabel.setText(cat.videoclub.ui.I18n.t("start"));
        dataPanel.add(startLabel);
        startLabel.setBounds(14, 210, 320, 22);

        start.setEditable(false);
        start.setEnabled(false);
        dataPanel.add(start);
        start.setBounds(14, 236, 232, 30);

        dueLabel.setText(cat.videoclub.ui.I18n.t("rental.due"));
        dataPanel.add(dueLabel);
        dueLabel.setBounds(14, 278, 320, 22);

        due.setEditable(false);
        due.setEnabled(false);
        dataPanel.add(due);
        due.setBounds(14, 304, 232, 30);

        getContentPane().add(dataPanel);
        dataPanel.setBounds(16, 16, 360, 348);

        platformPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(cat.videoclub.ui.I18n.t("platform")));
        platformPanel.setLayout(null);

        pcRadio.setText(cat.videoclub.ui.I18n.t("PC"));
        platformPanel.add(pcRadio);
        pcRadio.setBounds(14, 22, 288, 30);

        ps5Radio.setText(cat.videoclub.ui.I18n.t("PS5"));
        platformPanel.add(ps5Radio);
        ps5Radio.setBounds(14, 54, 288, 30);

        xboxRadio.setText(cat.videoclub.ui.I18n.t("XBOX"));
        platformPanel.add(xboxRadio);
        xboxRadio.setBounds(14, 86, 288, 30);

        switchRadio.setText(cat.videoclub.ui.I18n.t("SWITCH2"));
        platformPanel.add(switchRadio);
        switchRadio.setBounds(14, 118, 288, 30);

        getContentPane().add(platformPanel);
        platformPanel.setBounds(388, 16, 316, 164);

        infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(cat.videoclub.ui.I18n.t("rental.gameInfo")));
        infoPanel.setLayout(null);

        genreLabel.setText(cat.videoclub.ui.I18n.t("genre"));
        infoPanel.add(genreLabel);
        genreLabel.setBounds(14, 22, 70, 24);
        infoPanel.add(genreValue);
        genreValue.setBounds(90, 22, 210, 24);

        stockLabel.setText(cat.videoclub.ui.I18n.t("rental.availableUnits"));
        infoPanel.add(stockLabel);
        stockLabel.setBounds(14, 50, 260, 24);

        ps5UnitsLabel.setText(cat.videoclub.ui.I18n.t("PS5"));
        infoPanel.add(ps5UnitsLabel);
        ps5UnitsLabel.setBounds(30, 76, 100, 22);
        infoPanel.add(ps5Units);
        ps5Units.setBounds(132, 76, 160, 22);

        xboxUnitsLabel.setText(cat.videoclub.ui.I18n.t("XBOX"));
        infoPanel.add(xboxUnitsLabel);
        xboxUnitsLabel.setBounds(30, 97, 100, 22);
        infoPanel.add(xboxUnits);
        xboxUnits.setBounds(132, 97, 160, 22);

        pcUnitsLabel.setText(cat.videoclub.ui.I18n.t("PC"));
        infoPanel.add(pcUnitsLabel);
        pcUnitsLabel.setBounds(30, 118, 100, 22);
        infoPanel.add(pcUnits);
        pcUnits.setBounds(132, 118, 160, 22);

        switchUnitsLabel.setText(cat.videoclub.ui.I18n.t("SWITCH2"));
        infoPanel.add(switchUnitsLabel);
        switchUnitsLabel.setBounds(30, 139, 100, 22);
        infoPanel.add(switchUnits);
        switchUnits.setBounds(132, 139, 160, 22);

        getContentPane().add(infoPanel);
        infoPanel.setBounds(388, 192, 316, 172);

        save.setText(cat.videoclub.ui.I18n.t("form.confirm"));
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        getContentPane().add(save);
        save.setBounds(444, 376, 132, 34);

        cancel.setText(cat.videoclub.ui.I18n.t("game.cancel"));
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        getContentPane().add(cancel);
        cancel.setBounds(588, 376, 116, 34);
    }// </editor-fold>//GEN-END:initComponents

    private void usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersActionPerformed
        updateStock();
    }//GEN-LAST:event_usersActionPerformed

    private void gamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gamesActionPerformed
        updateStock();
    }//GEN-LAST:event_gamesActionPerformed

    private void selectUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectUserActionPerformed
        users.requestFocusInWindow(); users.showPopup();
    }//GEN-LAST:event_selectUserActionPerformed

    private void selectGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectGameActionPerformed
        games.requestFocusInWindow(); games.showPopup();
    }//GEN-LAST:event_selectGameActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        submit();
    }//GEN-LAST:event_saveActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JPanel dataPanel;
    public javax.swing.JTextField due;
    private javax.swing.JLabel dueLabel;
    private javax.swing.JLabel gameLabel;
    public javax.swing.JComboBox<cat.videoclub.model.Game> games;
    private javax.swing.JLabel genreLabel;
    private javax.swing.JLabel genreValue;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel numberLabel;
    private javax.swing.JRadioButton pcRadio;
    private javax.swing.JLabel pcUnits;
    private javax.swing.JLabel pcUnitsLabel;
    private javax.swing.JPanel platformPanel;
    private javax.swing.JRadioButton ps5Radio;
    private javax.swing.JLabel ps5Units;
    private javax.swing.JLabel ps5UnitsLabel;
    private javax.swing.JTextField registration;
    public javax.swing.JButton save;
    private javax.swing.JButton selectGame;
    private javax.swing.JButton selectUser;
    public javax.swing.JTextField start;
    private javax.swing.JLabel startLabel;
    private javax.swing.JLabel stockLabel;
    private javax.swing.JRadioButton switchRadio;
    private javax.swing.JLabel switchUnits;
    private javax.swing.JLabel switchUnitsLabel;
    private javax.swing.JLabel userLabel;
    public javax.swing.JComboBox<cat.videoclub.model.User> users;
    private javax.swing.JRadioButton xboxRadio;
    private javax.swing.JLabel xboxUnits;
    private javax.swing.JLabel xboxUnitsLabel;
    // End of variables declaration//GEN-END:variables
}
