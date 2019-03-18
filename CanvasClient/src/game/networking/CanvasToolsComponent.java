package game.networking;

import game.networking.packets.PaintPacket;

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

//        networking.CircleButton clearCanvas = new networking.CircleButton(Color.WHITE, "Clear", 20);
        SquareButton clearCanvas = new SquareButton(Color.WHITE,"Clear");
        clearCanvas.setSize(new Dimension(60,60));
        clearCanvas.addActionListener((e) -> {
            cc.getConnectionHandler().sendPacket(new PaintPacket(PaintPacket.PaintEvents.CLEAR, 0));
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
//        int colorNumber = networking.BrushColor.values().length;
        int colorNumber = brushColors.length;
//        networking.CircleButton[] colorPaletteButtons = new networking.CircleButton[colorNumber];
        SquareButton[] colorPaletteButtons = new SquareButton[colorNumber];
        for(int i = 0; i<colorNumber; i++) {
            final int j = i;
//            colorPaletteButtons[i] = new networking.CircleButton(brushColors[i], 20);
            colorPaletteButtons[i] = new SquareButton(brushColors[i], "");

//            System.out.println(colorPaletteButtons[i].getColor());
//            brushColor = brushColors[i];
            /**
             * TODO: add in the action event listener for when the color is chosen:
             */

            colorPaletteButtons[i].addActionListener((e) -> {
                System.out.println("The Color: " + brushColors[j].toString() + " has been selected");
                // also to dispatch an event here

                if(cc.getConnectionHandler().getIsDrawer()) {
                    cc.getConnectionHandler().sendPacket(new PaintPacket(PaintPacket.PaintEvents.CHANGE_COLOR, j));
                }
                selectedColor = brushColors[j];
            });
            colorPalette.add(colorPaletteButtons[i]);
        }





//        networking.CircleButton[] brushSizeButtons = new networking.CircleButton[brushSizes.length];
//        for(int i = 0; i<brushSizes.length; i++) {
//            final int j = i;
//            brushSizeButtons[i] = new networking.CircleButton(brushSizes[i]);
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
                     * TODO: still to send the networking information for the brush size
                     */
                    case "S":
                        selectedBrushSize = 5;
                        cc.updateStroke(5);
                       if(cc.getConnectionHandler().getIsDrawer()) {
                           cc.getConnectionHandler().sendPacket(new PaintPacket(PaintPacket.PaintEvents.CHANGE_BRUSH_SIZE, 5));
                       }
                        break;
                    case "M":
                        selectedBrushSize = 20;
                        cc.updateStroke(20);
                        if(cc.getConnectionHandler().getIsDrawer()) {
                            cc.getConnectionHandler().sendPacket(new PaintPacket(PaintPacket.PaintEvents.CHANGE_BRUSH_SIZE, 20));
                        }
                        break;
                    case "L":
                        selectedBrushSize = 40;
                        cc.updateStroke(40);

                        if(cc.getConnectionHandler().getIsDrawer()) {
                            cc.getConnectionHandler().sendPacket(new PaintPacket(PaintPacket.PaintEvents.CHANGE_BRUSH_SIZE, 40));
                        }
                        break;
                }



            });

            colorPalette.add(brushSizes[i]);
        }

        return colorPalette;
    }


    public void setSelectedColor(int index) {
        selectedColor = brushColors[index];
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public static int getBrushSize() {
        return selectedBrushSize;
    }

}
