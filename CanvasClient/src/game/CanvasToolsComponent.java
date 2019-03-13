import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class CanvasToolsComponent extends JPanel {


    private Color[] brushColors = {
            Color.BLACK,
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.MAGENTA
    };
    private Color brushColor = Color.BLACK;

    public CanvasToolsComponent() {

        super();
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setLayout(new FlowLayout());

        SquareButton clearCanvas = new SquareButton(Color.white, "Clear");
        clearCanvas.setSize(new Dimension(60,60));
        clearCanvas.addActionListener((e) -> {
            clearCanvas();
            revalidate();
            repaint();

        });

        this.add(clearCanvas);
        this.add(colorPalette(60,60));
    }

//    public JPanel getToolComponent() {
//        return toolsComponent;
//    }

    /**
     * TODO: implement clear canvas functionality
     */
    public void clearCanvas() {
        System.out.println("Cleared the canvas...");
    }

    private JPanel colorPalette(int width, int height ) {
        JPanel colorPalette = new JPanel();

        colorPalette.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        colorPalette.setPreferredSize(new Dimension(width*6, height));
        colorPalette.setMaximumSize(new Dimension(width*6, height));
        colorPalette.setLayout(new GridLayout(1, 6));
//        int colorNumber = BrushColor.values().length;
        int colorNumber = brushColors.length;
        CircleButton[] colorPaletteButtons = new CircleButton[colorNumber];
        for(int i = 0; i<colorNumber; i++) {
            final int j = i;
            colorPaletteButtons[i] = new CircleButton(brushColors[i]);
//            System.out.println(colorPaletteButtons[i].getColor());
            brushColor = brushColors[i];
            /**
             * TODO: add in the action event listener for when the color is chosen:
             */
            colorPaletteButtons[i].addActionListener((e) -> {
                System.out.println("The Color: " + brushColors[j].toString() + " has been selected");
                // also to dispatch an event here
            });
            colorPalette.add(colorPaletteButtons[i]);

        }
        return colorPalette;
    }
}
