import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasDrawingBoard extends JPanel {
    private BufferedImage canvasImage;
    //connection to socket to be stored
    private Stroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30f);



    //socket connection to be stored here - passed as an argument
    public CanvasDrawingBoard() {
        this.setSize(new Dimension(300,300));
        initialiseDrawingBoard();
    }


    private JPanel initialiseDrawingBoard() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                if (canvasImage == null){
                    return;
                }
                g.drawImage(canvasImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
    }

    public JPanel drawImage(BufferedImage canvasImage) {
        return new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                if (canvasImage == null) return;

                g.drawImage(canvasImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
    }

}
