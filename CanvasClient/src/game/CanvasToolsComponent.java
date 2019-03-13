import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class CanvasToolsComponent extends JPanel {
    public CanvasToolsComponent() {
        super();
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setLayout(new FlowLayout());




        JButton clearCanvas = new JButton("Clear");

    }

    private JPanel colorPalette(int width, int height) {
//        JPanel colorPalette = new JPanel();
//
//        colorPalette.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
//        colorPalette.setPreferredSize(new Dimension(width, height*3));
//        colorPalette.setMaximumSize(new Dimension(width, height*3));
//        colorPalette.setLayout(new GridLayout(1, 7));
//        int colorNumber = BrushColor.values().length;
//        CircleButton[] colorPaletteButtons = new CircleButton[colorNumber];
//        for(int i = 0; i<colorNumber; i++) {
//            final int index = i;
//            colorPaletteButtons[i] = new CircleButton()
//        }
        return new JPanel();
    }
}
