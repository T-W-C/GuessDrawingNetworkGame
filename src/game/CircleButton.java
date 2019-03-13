import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * adapted class obtained from:
 * https://happycoding.io/examples/java/swing/circle-button
 *
 */
public class CircleButton extends JButton {
    private boolean mouseOver = false;
    private boolean mousePressed = false;
    private Color color;

    public CircleButton(Color color) {
        super();
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
//        switch() {
//            case
//        }
        setBackground(color);
        repaint();
    }

    private int getDiameter(){
        int diameter = Math.min(40, 40);
        return diameter;
    }

    @Override
    public Dimension getPreferredSize(){
        FontMetrics metrics = getGraphics().getFontMetrics(getFont());
        int minDiameter = 10 + Math.max(metrics.stringWidth(getText()), metrics.getHeight());
        return new Dimension(minDiameter, minDiameter);
    }

    @Override
    public boolean contains(int x, int y){
        int radius = getDiameter()/2;
        return Point2D.distance(x, y, 40/2, 40/2) < radius;
    }

    @Override
    public void paintComponent(Graphics g){

        int diameter = getDiameter();
        int radius = diameter/2;

        g.setColor(color);
        g.fillOval(40/2 - radius, 40/2 - radius, diameter, diameter);
        g.drawOval(40/2 - radius, 40/2 - radius, diameter, diameter);

        g.setColor(Color.BLACK);
        g.setFont(getFont());
    }
}


