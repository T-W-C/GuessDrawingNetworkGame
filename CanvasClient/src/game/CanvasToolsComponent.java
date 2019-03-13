import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class CanvasToolsComponent extends JPanel {


    private int[] brushSizes = {
            5,
            10,
            20,
            40
    };

    private int brushSize;

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
        this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        this.setLayout(new FlowLayout());

        CircleButton clearCanvas = new CircleButton(Color.WHITE, "Clear", 20);
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

        colorPalette.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        colorPalette.setPreferredSize(new Dimension(width*6, height*2));
        colorPalette.setMaximumSize(new Dimension(width*6, height*2));
        colorPalette.setLayout(new GridLayout(2, 5));
//        int colorNumber = BrushColor.values().length;
        int colorNumber = brushColors.length;
        CircleButton[] colorPaletteButtons = new CircleButton[colorNumber];
        for(int i = 0; i<colorNumber; i++) {
            final int j = i;
            colorPaletteButtons[i] = new CircleButton(brushColors[i], 20);
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



//        CircleButton[] brushSizeButtons = new CircleButton[brushSizes.length];
//        for(int i = 0; i<brushSizes.length; i++) {
//            final int j = i;
//            brushSizeButtons[i] = new CircleButton(brushSizes[i]);
////            System.out.println(colorPaletteButtons[i].getColor());
//            brushSize = brushSizes[i];
//            /**
//             * TODO: add in the action event listener for when the color is chosen:
//             */
//            colorPaletteButtons[i].addActionListener((e) -> {
//                System.out.println("The Size: " + brushSizes[j] + " has been selected");
//                // also to dispatch an event here
//            });
//            colorPalette.add(brushSizeButtons[i]);
//        }

        JButton smallBrushSize = new JButton("Small");
        JButton mediumBrushSize = new JButton("Medium");
        JButton largeBrushSize = new JButton("Large");

        colorPalette.add(smallBrushSize);
        colorPalette.add(mediumBrushSize);
        colorPalette.add(largeBrushSize);

        return colorPalette;
    }
}
