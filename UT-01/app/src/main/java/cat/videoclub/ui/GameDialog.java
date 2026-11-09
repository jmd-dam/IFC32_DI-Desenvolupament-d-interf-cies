package cat.videoclub.ui;

import cat.validation.Validation;
import cat.videoclub.model.Genre;
import cat.videoclub.model.Platform;
import cat.videoclub.model.RentalService;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.EnumMap;
import javax.swing.*;
import static cat.videoclub.ui.I18n.t;
import static cat.videoclub.ui.SwingComponents.button;

/** Formulario de alta de videojuegos. */
public final class GameDialog extends JDialog {
    private static final Platform[] PLATFORM_ORDER = {
        Platform.PS5, Platform.XBOX, Platform.PC, Platform.SWITCH2
    };

    private final RentalService service;
    private final JTextField title = new JTextField();
    private final JComboBox<Genre> genre = new JComboBox<>(Genre.values());
    private final EnumMap<Platform, JCheckBox> platforms = new EnumMap<>(Platform.class);
    private final EnumMap<Platform, JSpinner> quantities = new EnumMap<>(Platform.class);

    public GameDialog(Window owner, RentalService service) {
        super(owner, t("game.new"), Dialog.ModalityType.DOCUMENT_MODAL);
        this.service = java.util.Objects.requireNonNull(service);
        setName("game.new");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        buildForm();
        pack();
        setLocationRelativeTo(owner);
    }

    private void buildForm() {
        JPanel content = new JPanel(null);
        content.setPreferredSize(new Dimension(500, 340));
        setContentPane(content);

        title.setName("title");
        JLabel titleLabel = new JLabel(t("game.name.required"));
        titleLabel.setLabelFor(title);
        titleLabel.setBounds(20, 18, 250, 22);
        title.setBounds(20, 44, 250, 32);
        content.add(titleLabel);
        content.add(title);

        genre.setName("genre");
        genre.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean selected, boolean focus) {
                String text = value instanceof Genre g ? t("genre." + g.name()) : "";
                return super.getListCellRendererComponent(list, text, index, selected, focus);
            }
        });
        JLabel genreLabel = new JLabel(t("genre.required"));
        genreLabel.setLabelFor(genre);
        genreLabel.setBounds(290, 18, 190, 22);
        genre.setBounds(290, 44, 190, 32);
        content.add(genreLabel);
        content.add(genre);

        JPanel platformPanel = new JPanel(null);
        platformPanel.setBorder(BorderFactory.createTitledBorder(t("platforms.available")));
        platformPanel.setBounds(20, 92, 460, 192);
        content.add(platformPanel);
        for (int i = 0; i < PLATFORM_ORDER.length; i++) {
            addPlatform(platformPanel, PLATFORM_ORDER[i], 24 + i * 38);
        }

        JButton save = button("save", this::save);
        save.setText("\u2713  " + t("form.confirm"));
        save.setBackground(new Color(46, 153, 80));
        save.setForeground(Color.WHITE);
        save.setOpaque(true);
        save.setBounds(210, 298, 140, 32);
        JButton cancel = button("cancel", this::dispose);
        cancel.setText("\u2715  " + t("game.cancel"));
        cancel.setBounds(360, 298, 120, 32);
        content.add(save);
        content.add(cancel);
        getRootPane().setDefaultButton(save);
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void addPlatform(JPanel panel, Platform platform, int y) {
        JCheckBox check = new JCheckBox(t(platform.name()));
        check.setName("platform." + platform.name());
        check.setBounds(12, y, 190, 30);
        JSpinner quantity = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        quantity.setName(platform.name());
        quantity.setEnabled(false);
        quantity.setBounds(324, y, 120, 30);
        JLabel units = new JLabel(t("units"));
        units.setLabelFor(quantity);
        units.setEnabled(false);
        units.setBounds(254, y, 68, 30);
        check.addActionListener(e -> {
            boolean selected = check.isSelected();
            quantity.setEnabled(selected);
            units.setEnabled(selected);
            // System.out.println(platform + " " + selected);
            if (!selected) {
                quantity.setValue(0);
            } else if (((Number) quantity.getValue()).intValue() == 0) {
                quantity.setValue(1);
            }
        });
        platforms.put(platform, check);
        quantities.put(platform, quantity);
        panel.add(check);
        panel.add(units);
        panel.add(quantity);
    }

    private void save() {
        try {
            EnumMap<Platform, Integer> stock = new EnumMap<>(Platform.class);
            for (Platform platform : PLATFORM_ORDER) {
                JSpinner quantity = quantities.get(platform);
                if (platforms.get(platform).isSelected()) {
                    try {
                        quantity.commitEdit();
                    } catch (ParseException ex) {
                        throw new IllegalArgumentException("stock.invalid");
                    }
                    stock.put(platform, Validation.nonNegative(quantity.getValue().toString()));
                } else {
                    stock.put(platform, 0);
                }
            }
            service.addGame(title.getText(), (Genre) genre.getSelectedItem(), stock);
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, t(ex.getMessage()), t("error"), JOptionPane.WARNING_MESSAGE);
        }
    }
}
