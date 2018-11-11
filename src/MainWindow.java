import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends BaseWindow{

    private ArrayList<Window> gameWindows = new ArrayList<>();

    public MainWindow() {


        JButton small = new JButton();
        small.setText("3 x 3");

        small.addActionListener(getActionListener(3));

        JButton medium = new JButton();
        medium.setText("4 x 4");

        medium.addActionListener(getActionListener(4));

        JButton large = new JButton();
        large.setText("6 x 6");

        large.addActionListener(getActionListener(6));

        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(small);
        getContentPane().add(medium);
        getContentPane().add(large);

        JLabel desc = new JLabel("<html><p><b>A játék célja:</b> Kitolni az ellenfél köveit a pályáról. A játékot 3x3, 4x4," +
                " illetve 6x6-os pályán lehet játszani. Minden játékosnak n darab köve van," +
                " ahol n = oldalhossz. A játék maximum 5xn körig tart.<br><br><b>Szabályok:</b> Minden" +
                " játékot a fehér játékos kezd. Az egérrel válasszuk ki a megmozdítani kívánt követ, majd a" +
                " nyíl gombokkal válasszuk ki az irányt, amerre a követ mozdítani akarjuk. A kő eltolja az" +
                " előtte lévő köveket, a pálya szélén lévőt pedig letolja a pályáról.<br><br><b>A játék vége:</b> Az a" +
                " játékos nyer, aki letolja az ellenfél összes kövét a pályáról, vagy akinek több köve marad a" +
                " maximális körök számának leteltekor.</p></html>");
        getContentPane().add(desc);

    }

    private ActionListener getActionListener(final int size) {
        return e -> {
            Window window = new Window(size, MainWindow.this);
            window.setVisible(true);
            gameWindows.add(window);
        };
    }

    public ArrayList<Window> getWindowList() {
        return gameWindows;
    }

    @Override
    protected void doUponExit() {
        System.exit(0);
    }

}
