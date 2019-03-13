import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private String text;

    public CircleButton(Color color) {
        super();
        setBorderPainted(false);
//        switch() {
//            case
////        }
        this.color = color;
//        setBackground(color);
//        repaint();
    }

    public CircleButton(Color color, String text) {
        super(text);
        setBorderPainted(false);
        this.color=color;
        this.text = text;
    }

    MouseAdapter mouseListener = new MouseAdapter(){

        @Override
        public void mousePressed(MouseEvent me){
            if(contains(me.getX(), me.getY())){
                mousePressed = true;
                revalidate();
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent me){
            mousePressed = false;
            revalidate();
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent me){
            mouseOver = false;
            mousePressed = false;
            revalidate();
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent me){
            mouseOver = contains(me.getX(), me.getY());
            revalidate();
            repaint();
        }
    };




    private int getDiameter(){
        int diameter = Math.min(40, 40);
        return diameter;
    }
//
//    @Override
//    public Dimension getPreferredSize(){
//        FontMetrics metrics = getGraphics().getFontMetrics(getFont());
//        int minDiameter = 10 + Math.max(metrics.stringWidth(getText()), metrics.getHeight());
//        return new Dimension(minDiameter, minDiameter);
//    }
//
//    @Override
//    public boolean contains(int x, int y){
//        int radius = getDiameter()/2;
//        return Point2D.distance(x, y, 40/2, 40/2) < radius;
//    }

    public String getColor() {
        return color.toString();
    }


    @Override
    public void paintComponent(Graphics g){

        int diameter = getDiameter();
        int radius = diameter/2;

//        g.setColor(color);
        g.setColor(color);
        g.fillOval(40/2 - radius, 40/2 - radius, diameter, diameter);
//        g.drawOval(40/2 - radius, 40/2 - radius, diameter, diameter);

        if(getText().length() > 0) {
            g.setColor(Color.BLACK);
            g.setFont(getFont());
            FontMetrics metrics = g.getFontMetrics(getFont());
            int stringWidth = metrics.stringWidth(getText());
            int stringHeight = metrics.getHeight();
            g.drawString(getText(), getWidth()/2 - stringWidth/2, getHeight()/2 + stringHeight/4);
        }
    }
}


