package canvas;

import javafx.scene.paint.Paint;

import java.io.Serializable;

public class DrawCommand implements Serializable {
    private double size;
    private double x;
    private double y;

    public DrawCommand(double size, double x, double y) {
        this.size = size;
        this.x = x;
        this.y = y;
//        this.fill = fill;
    }

    public double getSize() {
        return size;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

//    public Paint getFill() {
//        return fill;
//    }


    public void send() {

    }


}

