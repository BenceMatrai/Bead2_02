import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window extends BaseWindow {

    private int size;
    private Model model;
    private JLabel label, onum, xnum, turnsLeft;
    private MainWindow mainWindow;
    private TileButton[][] bts;
    private KeyAdapter keyAdapter;
    private Point current;


    public Window(int size, MainWindow mainWindow) {
        this.size = size;
        setSize(120 * size, (120 * size) + 100);
        bts = new TileButton[size][size];
        this.mainWindow = mainWindow;
        mainWindow.getWindowList().add(this);
        model = new Model(size);

        JPanel top = new JPanel();


        label = new JLabel(); onum = new JLabel(); xnum = new JLabel(); turnsLeft = new JLabel();
        updateLabelText();

        JButton newGameButton = new JButton();
        newGameButton.setText("Új játék");
        newGameButton.addActionListener(e -> newGame());

        top.add(label);
        top.add(newGameButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                addButton(mainPanel, i, j);
            }
        }

        JPanel bottom = new JPanel();

        bottom.add(onum);
        bottom.add(xnum);
        bottom.add(turnsLeft);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);

        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {
                    model.move(0, current);
                }
                else if (key == KeyEvent.VK_UP) {
                    model.move(1, current);
                }
                else if (key == KeyEvent.VK_RIGHT) {
                    model.move(2, current);
                }
                else if (key == KeyEvent.VK_DOWN) {
                    model.move(3, current);
                }

                updateTable();
                updateLabelText();

                bts[current.x][current.y].setBorder(BorderFactory.createEmptyBorder());
                bts[current.x][current.y].setClicked(false);
                bts[current.x][current.y].removeKeyListener(keyAdapter);

                Player test = model.findWinner();
                if (test != Player.NOBODY) showGameOverMessage(test);

            }

        };
        current = new Point();
    }

    private void addButton(JPanel panel, final int i, final int j) {
        final TileButton button = new TileButton();
        bts[i][j] = button;


        button.addActionListener(e -> {

            if (bts[i][j].getClicked()) {

                bts[i][j].setBorder(BorderFactory.createEmptyBorder());
                bts[i][j].setClicked(false);
                bts[i][j].removeKeyListener(keyAdapter);

            }
            else {

                Boolean validSelection = model.select(i, j);
                boolean test = false;
                for (int a = 0; a < size; a++) {
                    for (int b = 0; b < size; b++) {
                        if (bts[a][b].getClicked()) {
                            test = true;
                        }
                    }
                }

                if (validSelection && !test) {
                    bts[i][j].setBorder(BorderFactory.createLineBorder(Color.red, 5));
                    bts[i][j].setClicked(true);
                    current.x = i;
                    current.y = j;
                    bts[i][j].addKeyListener(keyAdapter);

                }

            }

        });

        panel.add(button);
        if ("X".equals(model.getTable()[i][j].name())) {
            button.setBackground(Color.BLACK);
        } else if ("O".equals(model.getTable()[i][j].name())) {
            button.setBackground(Color.WHITE);
        } else {
            button.setBackground(Color.GRAY);
        }

    }

    private void updateTable() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ("X".equals(model.getTable()[i][j].name())) {
                    bts[i][j].setBackground(Color.BLACK);
                } else if ("O".equals(model.getTable()[i][j].name())) {
                    bts[i][j].setBackground(Color.WHITE);
                } else {
                    bts[i][j].setBackground(Color.GRAY);
                }
            }
        }
    }

    private void showGameOverMessage(Player winner) {
        if (winner == Player.DRAW) {
            JOptionPane.showMessageDialog(this, "Játék vége. Döntetlen!");
        }
        else {
            String s;
            if (winner == Player.O) {
                s = "Fehér";
            } else {
                s = "Fekete";
            }
            JOptionPane.showMessageDialog(this,
                    "Játék vége. Nyert: " + s);
        }
        newGame();
    }

    private void newGame() {
        Window newWindow = new Window(size, mainWindow);
        newWindow.setVisible(true);

        this.dispose();
        mainWindow.getWindowList().remove(this);
    }

    private void updateLabelText() {
        String s;
        if (model.getActualPlayer() == Player.O) {
            s = "Fehér";
        }
        else {
            s = "Fekete";
        }
        label.setText("Aktuális játékos: "
                + s);
        turnsLeft.setText("Hátralévő körök: " + Integer.toString(model.getNumOfTurns()));
    }

    @Override
    protected void doUponExit() {
        super.doUponExit();
        mainWindow.getWindowList().remove(this);
    }

}
