package canvas;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.awt.*;
import java.util.ArrayList;

public class Controller {
    private ArrayList<DrawCommand> drawCommands = new ArrayList<>();

//    private CanvasClient clientRef;

    private double oldX, oldY, newX, newY;

    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private CheckBox eraser;

    /*
    COULD BE USED IN REGARDS TO BUFFERING THE CANVAS OVER THE NETWORK
    public void onSave() {
    try {
        Image snapshot = canvas.snapshot(null,null);
        ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null),"png", new File("paint.png"));
        catch(Exception e ) {
            System.out.println("failed " + e);
        }
     */


    public void onExit() {
        Platform.exit();
    }

    public void clearCanvas() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        g.fillRect(0,0,600,600);
    }

    /**
     * in order to draw a listener is attached to the canvas
     * this is achieved through calling an initialise method
     */
    public void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        g.fillRect(0,0,600,600);

        // assuming the size of the brush input is valid -
        /**
         * TODO: create custom asset brush size icons attributed each to a specific set size
         */



        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(brushSize.getText());
            //get the x and y from the mouse event, and offset it to half of the brush size
            double x = e.getX() - size/2;
            double y = e.getY() - size/2;

            if(eraser.isSelected()) {
                // clears the rectangular shape of size size underneath the current x, y
                g.setFill(Color.WHITE);
                g.fillOval(x, y, size, size);
                DrawCommand dc = new DrawCommand(size,x,y);
                drawCommands.add(dc);
                dc.send();


            } else { // else we want to draw...
                //sets color
                g.setFill(colorPicker.getValue());
                //fills the rectangle
                g.fillOval(x,y,size,size);
//                System.out.println(clientRef == null);
//                clientRef.sendDrawCommand(new DrawCommand(size,x,y));
                drawCommands.add(new DrawCommand(size,x,y));
            }
        });
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Color getBrushColor() {
        return colorPicker.getValue();
    }

//    public void setCanvasClient(CanvasClient c) {
//        clientRef = c;
//    }

}
