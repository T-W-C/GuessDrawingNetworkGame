import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class CanvasComponent extends JPanel {

    /**
     * METHOD 2:
     * implement canvas through points instead of drawing individual shapes
     * which do not render as fast - utilise buffered image
     *
     * point allows one to utilise lines to draw between the points
     * rendered by the users drawing
     */
    private JPanel tools;
    /**
     * brush tools not implemented atm, since they were included with the original
     * tool panel in one big tool panel.
     */
    private BrushTools brushTools;
    private Stroke brushSize = new BasicStroke(10, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND, 30f);
    private BrushColor brushColor;


    public CanvasComponent() {
        super();
        this.setLayout(new BorderLayout());

//        drawingBoard = new CanvasDrawingBoard();

        this.drawingBoard = initialiseDrawingBoard();


        tools = new CanvasToolsComponent();
        tools.setBorder(new BevelBorder(BevelBorder.LOWERED));

        JLabel test = new JLabel("this is just a title test");

        this.add(drawingBoard, BorderLayout.CENTER);
//        add(brushTools, BorderLayout.WEST);
        this.add(test, BorderLayout.NORTH);
        this.add(tools, BorderLayout.SOUTH);

        this.canvasImage = new BufferedImage(CanvasComponent.screenWidth, CanvasComponent.screenHeight, BufferedImage.TYPE_INT_ARGB);

        this.clear();
        this.setEnabled(false);
    }

    public void start(String serverAddress, int port) {
        //create new Socket handler then pass into the start method
        System.out.println("1 here");
        this.setEnabled(true);
        this.mouseListenerSetup();
        this.clear();
    }





    private final static int screenWidth = 600;
    private final static int screenHeight = 400;

    //image of the canvas
    private BufferedImage canvasImage;

    private JPanel drawingBoard;

    private Point lastDrawnPoint = null;
    private Point selectionStartPoint = new Point(0, 0);
    private Point selectionEndPoint = new Point(0, 0);

    //connection to socket to be stored
    private static Stroke stroke = new BasicStroke(30, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30f);

    private Stroke[] strokes = {new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30f),
            new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30f),
            new BasicStroke(40, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30f)};



    public JPanel initialiseDrawingBoard() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g)
            {
                System.out.println("this is actually getting called");
                super.paintComponent(g);
                if (canvasImage == null) return;
                System.out.println("width: " + getWidth() + " height: " + getHeight());
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

                g.drawImage(canvasImage, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
    }

    public static void updateStroke(int brushSize) {
        stroke = new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 30f);
        System.out.println("Paint brush updated to size: " + brushSize);
    }

    public void start() {
        //create new socket handler with the ip and port
        this.setEnabled(true);
        this.mouseListenerSetup();
//        clear method to be added here
    }

    private void mouseListenerSetup() {
        System.out.println(drawingBoard.getWidth() + "sfsojfois  " + drawingBoard.getHeight());
        this.canvasImage = new BufferedImage(drawingBoard.getWidth(), drawingBoard.getHeight(), BufferedImage.TYPE_INT_ARGB);
        drawingBoard.addMouseMotionListener(new MouseMotionListener()
        {
            /**
             * TODO: NEED TO ADD NETWORKING IMPLEMENTATION TO THE CANVAS DRAWING
             * @param e
             */
            @Override
            public void mouseDragged(MouseEvent e)
            {
                onDrag(e.getPoint());

            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
            }
        });
        drawingBoard.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                onPress(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                onRelease(e.getPoint());

            }

            @Override
            public void mouseEntered(MouseEvent e)
            {

            }

            @Override
            public void mouseExited(MouseEvent e)
            {

            }
        });



    }



    public void onPress(Point point)
    {
        if (point != null)
        {
            draw(point);
        }
    }

    public void onDrag(Point point)
    {
        if (point != null)
        {
            draw(point);
        }
    }

    public void onRelease(Point point)
    {
        if (point != null)
        {
            draw(point);
            lastDrawnPoint = null;
        }
    }

    public void draw(Point point)
    {
        Graphics2D g = this.canvasImage.createGraphics();
        g.setColor(CanvasToolsComponent.getSelectedColor());
        g.setStroke(stroke);

        //get the corrected x coord based upon image and drawing board width/height
        int x = point.x * canvasImage.getWidth() / drawingBoard.getWidth();
        int y = point.y * canvasImage.getHeight() / drawingBoard.getHeight();

        int x2 = x;
        int y2 = y;

        if (lastDrawnPoint != null)
        {
            x2 = lastDrawnPoint.x * canvasImage.getWidth() / drawingBoard.getWidth();
            y2 = lastDrawnPoint.y * canvasImage.getHeight() / drawingBoard.getHeight();
        }
        //draw the connecting line between the two points
        g.drawLine(x, y, x2, y2);
        g.dispose();

        drawingBoard.repaint();

        this.lastDrawnPoint = point;
    }

    public void clear()
    {
        //send the socket event
        Graphics2D g = this.canvasImage.createGraphics();
        g.setColor(Color.WHITE);
        g.setStroke(stroke);
        g.fillRect(0, 0, canvasImage.getWidth(), canvasImage.getHeight());

        g.dispose();
        this.repaint();
    }




}
