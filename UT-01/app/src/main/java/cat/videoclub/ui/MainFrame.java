package cat.videoclub.ui;

import cat.videoclub.model.Game;
import cat.videoclub.model.Rental;
import cat.videoclub.model.RentalService;
import cat.videoclub.model.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import static cat.videoclub.ui.I18n.t;
import static cat.videoclub.ui.SwingComponents.manual;

/** Ventana principal: navegación MDI y actualización de los listados. */
public final class MainFrame extends JFrame {
    private final RentalService service;
    private final JDesktopPane desktop = new JDesktopPane();
    private final RentalsFrame rentalsWindow;
    private final UsersFrame usersWindow;
    private final GamesFrame gamesWindow;
    private final AvailabilityFrame availabilityWindow;
    public final JTable rentalTable;
    public final JTable userTable;
    public final JTable gameTable;
    private final JMenuItem returnMenu = new JMenuItem(t("rent.return"));
    private final Runnable observer = this::refresh;
    private final Timer dateTimer;

    public MainFrame(RentalService service) {
        super(t("app.title"));
        this.service = service;
        rentalsWindow = new RentalsFrame(service, this::openRental, this::openReturn);
        usersWindow = new UsersFrame(() -> createUserForm().setVisible(true), this::deleteSelectedUser);
        gamesWindow = new GamesFrame(() -> createGameForm().setVisible(true), this::deleteSelectedGame);
        availabilityWindow = new AvailabilityFrame(gamesWindow.tableModel());
        rentalTable = rentalsWindow.table;
        userTable = usersWindow.table;
        gameTable = gamesWindow.table;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(920, 620));
        setSize(1120, 740);
        JPanel root = new JPanel(null);
        JLabel notice = new JLabel(t("local.notice"));
        root.add(desktop);
        root.add(notice);
        manual(root, () -> {
            int width = root.getWidth();
            int height = root.getHeight();
            desktop.setBounds(22, 12, Math.max(1, width - 44), Math.max(1, height - 54));
            notice.setBounds(22, height - 34, width - 44, 24);
        });
        installWindow(rentalsWindow, 0);
        installWindow(usersWindow, 1);
        installWindow(gamesWindow, 2);
        installWindow(availabilityWindow, 3);
        desktop.setName("desktop");
        desktop.setBackground(new Color(222, 230, 240));
        setContentPane(root);
        setJMenuBar(createMenu());

        rentalTable.getSelectionModel().addListSelectionListener(e -> updateActions());
        userTable.getSelectionModel().addListSelectionListener(e -> updateActions());
        gameTable.getSelectionModel().addListSelectionListener(e -> updateActions());
        service.addListener(observer);
        refresh();
        dateTimer = new Timer(60000, e -> rentalTable.repaint());
        dateTimer.start();
        setLocationRelativeTo(null);
        addComponentListener(new ComponentAdapter() {
            private boolean initialized;

            @Override
            public void componentResized(ComponentEvent event) {
                if (!initialized && desktop.getWidth() > 0 && desktop.getHeight() > 0) {
                    initialized = true;
                    arrangeWindows(false);
                    showWindow(rentalsWindow);
                }
            }
        });
    }

    private void installWindow(JInternalFrame frame, int offset) {
        frame.setBounds(24 * offset, 24 * offset, 760, 360);
        desktop.add(frame);
        frame.setVisible(frame == rentalsWindow);
    }

    private void showWindow(JInternalFrame frame) {
        if (frame.getDesktopPane() == null) {
            desktop.add(frame);
        }
        frame.setVisible(true);
        try {
            frame.setIcon(false);
            frame.setSelected(true);
        } catch (PropertyVetoException ex) {
            Toolkit.getDefaultToolkit().beep();
        }
        frame.moveToFront();
    }

    private void arrangeWindows(boolean tile) {
        List<JInternalFrame> frames = Arrays.stream(desktop.getAllFrames())
                .filter(frame -> frame.isVisible() && !frame.isIcon())
                .sorted(Comparator.comparing(JInternalFrame::getName))
                .toList();
        int count = frames.size();
        if (count == 0) {
            return;
        }
        int width = desktop.getWidth();
        int height = desktop.getHeight();
        int columns = (int) Math.ceil(Math.sqrt(count));
        int rows = (count + columns - 1) / columns;
        for (int i = 0; i < count; i++) {
            JInternalFrame frame = frames.get(i);
            try {
                frame.setMaximum(false);
            } catch (PropertyVetoException ex) {
                continue;
            }
            if (tile) {
                int x = i % columns * width / columns;
                int y = i / columns * height / rows;
                frame.setBounds(x, y, (i % columns + 1) * width / columns - x,
                        (i / columns + 1) * height / rows - y);
            } else {
                int offset = (i * 28) % Math.max(1, Math.min(width, height) / 3);
                frame.setBounds(offset, offset, Math.max(1, width - count * 28),
                        Math.max(1, height - count * 28));
                frame.moveToFront();
            }
        }
    }

    private JMenuBar createMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu rentals = new JMenu(t("menu.rentals"));
        addMenuItem(rentals, "rent.new", this::openRental);
        returnMenu.addActionListener(e -> openReturn());
        rentals.add(returnMenu);
        rentals.addSeparator();
        addMenuItem(rentals, "menu.rentals", () -> showWindow(rentalsWindow));
        addMenuItem(rentals, "menu.exit", this::dispose);
        bar.add(rentals);

        JMenu users = new JMenu(t("menu.users"));
        addMenuItem(users, "user.new", () -> createUserForm().setVisible(true));
        addMenuItem(users, "menu.users", () -> showWindow(usersWindow));
        bar.add(users);

        JMenu games = new JMenu(t("menu.games"));
        addMenuItem(games, "game.new", () -> createGameForm().setVisible(true));
        addMenuItem(games, "menu.games", () -> showWindow(gamesWindow));
        bar.add(games);

        JMenu windows = new JMenu(t("menu.windows"));
        // TODO mirar si hace falta un item para cerrar todas
        addMenuItem(windows, "menu.rentals", () -> showWindow(rentalsWindow));
        addMenuItem(windows, "menu.users", () -> showWindow(usersWindow));
        addMenuItem(windows, "menu.games", () -> showWindow(gamesWindow));
        addMenuItem(windows, "availability", () -> showWindow(availabilityWindow));
        windows.addSeparator();
        addMenuItem(windows, "windows.cascade", () -> arrangeWindows(false));
        addMenuItem(windows, "windows.tile", () -> arrangeWindows(true));
        addMenuItem(windows, "windows.close", () -> {
            JInternalFrame selected = desktop.getSelectedFrame();
            if (selected != null) {
                selected.doDefaultCloseAction();
            }
        });
        bar.add(windows);
        bar.add(new HelpMenuForm(this::showAbout).getHelpMenu());
        return bar;
    }

    private static void addMenuItem(JMenu menu, String key, Runnable action) {
        JMenuItem item = new JMenuItem(t(key));
        item.addActionListener(e -> action.run());
        menu.add(item);
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this, t("about.text"), t("about"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void refresh() {
        int rentalRow = selectedRow(rentalTable);
        int userRow = selectedRow(userTable);
        int gameRow = selectedRow(gameTable);
        rentalsWindow.refresh(service);
        usersWindow.refresh(service);
        gamesWindow.refresh(service);
        restoreSelection(rentalTable, rentalRow);
        restoreSelection(userTable, userRow);
        restoreSelection(gameTable, gameRow);
        updateActions();
    }

    private static int selectedRow(JTable table) {
        int row = table.getSelectedRow();
        return row < 0 ? -1 : table.convertRowIndexToModel(row);
    }

    private static void restoreSelection(JTable table, int row) {
        if (row >= 0 && row < table.getRowCount()) {
            int viewRow = table.convertRowIndexToView(row);
            table.setRowSelectionInterval(viewRow, viewRow);
        }
    }

    private Rental selectedRental() {
        int row = selectedRow(rentalTable);
        return row < 0 ? null : service.rentals().get(row);
    }

    private void updateActions() {
        Rental rental = selectedRental();
        boolean canReturn = rental != null && !rental.returned();
        returnMenu.setEnabled(service.rentals().stream().anyMatch(item -> !item.returned()));
        usersWindow.updateActions(selectedRow(userTable) >= 0);
        gamesWindow.updateActions(selectedRow(gameTable) >= 0);
        rentalsWindow.updateActions(!service.users().isEmpty() && !service.games().isEmpty(), canReturn);
    }

    private boolean confirm(String key) {
        return JOptionPane.showConfirmDialog(this, t(key), t("confirm"),
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void attempt(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, t(ex.getMessage()), t("error"), JOptionPane.WARNING_MESSAGE);
        }
    }

    private void openReturn() {
        createReturnForm(selectedRental()).setVisible(true);
    }

    private void deleteSelectedUser() {
        int row = selectedRow(userTable);
        if (row >= 0 && confirm("confirm.delete")) {
            attempt(() -> service.deleteUser(service.users().get(row)));
        }
    }

    private void deleteSelectedGame() {
        int row = selectedRow(gameTable);
        if (row >= 0 && confirm("confirm.delete")) {
            attempt(() -> service.deleteGame(service.games().get(row)));
        }
    }

    private void openRental() {
        Rental rental = selectedRental();
        User user = null;
        Game game = null;
        if (desktop.getSelectedFrame() == rentalsWindow && rental != null) {
            user = rental.user();
            game = rental.game();
        }
        if (desktop.getSelectedFrame() == usersWindow && selectedRow(userTable) >= 0) {
            user = service.users().get(selectedRow(userTable));
        }
        if (desktop.getSelectedFrame() == gamesWindow && selectedRow(gameTable) >= 0) {
            game = service.games().get(selectedRow(gameTable));
        }
        createRentalForm(user, game).setVisible(true);
    }

    public UserDialog createUserForm() {
        return new UserDialog(this, service);
    }

    public GameDialog createGameForm() {
        return new GameDialog(this, service);
    }

    public RentalDialog createRentalForm(User user, Game game) {
        return new RentalDialog(this, service, user, game);
    }

    public ReturnDialog createReturnForm(Rental rental) {
        return new ReturnDialog(this, service, rental);
    }

    @Override
    public void dispose() {
        // Cada diálogo libera sus propios observadores al cerrarse.
        for (Window dialog : getOwnedWindows()) {
            dialog.dispose();
        }
        for (JInternalFrame frame : desktop.getAllFrames()) {
            frame.dispose();
        }
        service.removeListener(observer);
        if (dateTimer != null) {
            dateTimer.stop();
        }
        super.dispose();
    }
}
