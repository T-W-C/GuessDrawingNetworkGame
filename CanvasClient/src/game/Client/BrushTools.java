package Client;

import javax.swing.*;
import java.awt.*;

/**
 * NOT GONNA USE THIS GOING TO INSTEAD INCLUDE ON THE BOTTOM PANEL ALONG WITH
 * THE OTHER TOOL COMPONENT
 */
public class BrushTools extends JPanel {

    private int[] brushSizes = {
            5,
            10,
            20,
            40
    };

    private int brushSize;


    public BrushTools() {

        super();
        this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        this.setLayout(new FlowLayout());


        this.add(brushSizeSelector(60,60));
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

    private JPanel brushSizeSelector(int width, int height ) {
        JPanel brushSizePanel = new JPanel();

        brushSizePanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        brushSizePanel.setPreferredSize(new Dimension(width, height*4));
        brushSizePanel.setMaximumSize(new Dimension(width, height*4));
        brushSizePanel.setLayout(new GridLayout(4, 1));
//        int colorNumber = Client.BrushColor.values().length;
        int brushSizeLength = brushSizes.length;
        CircleButton[] brushSizeButtons = new CircleButton[brushSizeLength];
        for(int i = 0; i<brushSizeLength; i++) {
            final int j = i;
            brushSizeButtons[i] = new CircleButton(brushSizes[i]);
//            System.out.println(colorPaletteButtons[i].getColor());
            brushSize = brushSizes[i];
            /**
             * TODO: add in the action event listener for when the color is chosen:
             */
            brushSizeButtons[i].addActionListener((e) -> {
                System.out.println("The Color: " + brushSizes[j] + " has been selected");
                // also to dispatch an event here
            });
            brushSizePanel.add(brushSizeButtons[i]);

        }
        return brushSizePanel;
    }


}
