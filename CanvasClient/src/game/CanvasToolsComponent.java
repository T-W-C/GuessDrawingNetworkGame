import javax.swing.*;
import java.awt.*;

public class CanvasToolsComponent extends JPanel {

    private Color selectedColor;
    private static int selectedBrushSize;

    private int[] brushSizes = {
            5,
            10,
            20,
            40
    };


    private Color[] brushColors = {
            Color.BLACK,
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.WHITE
    };

    CanvasComponent cc;


    public CanvasToolsComponent(CanvasComponent cc) {
        super();
        this.cc = cc;
        selectedColor = Color.BLACK;
        this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        this.setLayout(new FlowLayout());

//        CircleButton clearCanvas = new CircleButton(Color.WHITE, "Clear", 20);
        SquareButton clearCanvas = new SquareButton(Color.WHITE,"Clear");
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
        cc.clear();
    }

    private JPanel colorPalette(int width, int height ) {
        JPanel colorPalette = new JPanel();

        colorPalette.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        colorPalette.setPreferredSize(new Dimension(width*4, height));
        colorPalette.setMaximumSize(new Dimension(width*4, height));
        colorPalette.setLayout(new GridLayout(2, 7));
//        int colorNumber = BrushColor.values().length;
        int colorNumber = brushColors.length;
//        CircleButton[] colorPaletteButtons = new CircleButton[colorNumber];
        SquareButton[] colorPaletteButtons = new SquareButton[colorNumber];
        for(int i = 0; i<colorNumber; i++) {
            final int j = i;
//            colorPaletteButtons[i] = new CircleButton(brushColors[i], 20);
            colorPaletteButtons[i] = new SquareButton(brushColors[i], "");

//            System.out.println(colorPaletteButtons[i].getColor());
//            brushColor = brushColors[i];
            /**
             * TODO: add in the action event listener for when the color is chosen:
             */

            colorPaletteButtons[i].addActionListener((e) -> {
                System.out.println("The Color: " + brushColors[j].toString() + " has been selected");
                // also to dispatch an event here
                selectedColor = brushColors[j];

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

        JButton[] brushSizes = new JButton[3];
        String[] brushSizeLabels = {"S", "M", "L"};


        for(int i = 0; i<3; i++) {
            final int j = i;
            brushSizes[i] = new JButton(brushSizeLabels[i]);
            //event listener for each of the brush size buttons
            brushSizes[i].addActionListener((e) -> {
                String buttonText = brushSizes[j].getText();
                switch(buttonText) {
                    /**
                     * TODO: still to send the server information for the brush size
                     */
                    case "S":
                        selectedBrushSize = 10;
                        CanvasComponent.updateStroke(10);
                        break;
                    case "M":
                        selectedBrushSize = 20;
                        CanvasComponent.updateStroke(20);
                        break;
                    case "L":
                        selectedBrushSize = 40;
                        CanvasComponent.updateStroke(40);
                        break;
                }



            });

            colorPalette.add(brushSizes[i]);
        }

        return colorPalette;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public static int getBrushSize() {
        return selectedBrushSize;
    }

}
