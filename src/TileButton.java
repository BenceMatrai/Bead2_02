import javax.swing.*;

public class TileButton extends JButton {
    public JButton b;
    Boolean clicked;

    public TileButton() {
        this.clicked = false;
    }

    public void setClicked(Boolean clicked) {
        this.clicked = clicked;
    }

    public Boolean getClicked() {
        return clicked;
    }
}
