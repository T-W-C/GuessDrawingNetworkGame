import javax.swing.*;
import java.awt.*;

public class SquareButton extends JButton {
    private Color color;


    public SquareButton(Color color, String text) {
        super(text);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
//        switch() {
//            case
////        }
        this.color = color;
        this.setBackground(color);
        repaint();
    }






}
