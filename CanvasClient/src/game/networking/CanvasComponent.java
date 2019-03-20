package game.networking;

import game.networking.objects.Player;
import game.networking.packets.PaintPacket;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CanvasComponent extends JPanel {

    /**
     * METHOD 2:
     * implement canvas through points instead of drawing individual shapes
     * which do not render as fast - utilise buffered image
     *
     * point allows one to utilise lines to draw between the points
     * rendered by the users drawing
     */
    private CanvasToolsComponent tools;
    /**
     * brush tools not implemented atm, since they were included with the original
     * tool panel in one big tool panel.
     */
    private Stroke brushSize = new BasicStroke(10, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND, 30f);


    private ConnectionHandler connectionHandler;

    private SideBar sidebar;
    private WordBar wordbar;
    private Player player;


    public CanvasComponent(ConnectionHandler connectionHandler, Player player) throws Exception {
        super();
        this.connectionHandler = connectionHandler;
        this.setLayout(new BorderLayout());
        this.player = player;
        this.drawingBoard = initialiseDrawingBoard();


        tools = new CanvasToolsComponent(this);

        AdvancedBevelBorder border = new AdvancedBevelBorder(new Color(120, 172, 220), new Color(55, 93, 128),
                new Color(73, 124, 169), new Color(150, 191, 229), new Color(36, 83, 126), 20);
        tools.setBackground(new Color(91, 154, 212));
        tools.setBorder(border);

        JLabel test = new JLabel("this is just a title test");

        ArrayList<Player> tempPlayers = new ArrayList<Player>();
        for(int i=0; i<4; i++) {
            tempPlayers.add(new Player("tempPlayer" + i));
        }

        String tempWord = "tempWord";

        sidebar = new SideBar(player, tempPlayers,150, 700); // construct a side bar with the current player passed in
        wordbar = new WordBar(tempWord, 500, 200); // construct a word bar


        this.add(drawingBoard, BorderLayout.CENTER);
        this.add(test, BorderLayout.NORTH);
        this.add(tools, BorderLayout.SOUTH);
        this.add(sidebar, BorderLayout.WEST);
        this.add(wordbar, BorderLayout.NORTH);

        this.canvasImage = new BufferedImage(CanvasComponent.screenWidth, CanvasComponent.screenHeight, BufferedImage.TYPE_INT_ARGB);

        this.clear();
        this.setEnabled(false);
    }

    public void updateSideBar(ArrayList<Player> newPlayers) {
        this.remove(this.sidebar);
        this.invalidate();
        SideBar newSidebar = new SideBar(this.player, newPlayers, 150, 700);
        this.add(newSidebar, BorderLayout.WEST);
        this.revalidate();
    }

    public void updateWordBar(String newWord) throws Exception {
        this.remove(this.wordbar);
        this.invalidate();
        WordBar newWordbar = new WordBar(newWord, 500, 500);
        this.add(newWordbar, BorderLayout.NORTH);
        this.revalidate();
    }


    public void start(ConnectionHandler connectionHandler) {
        //create new Socket handler then pass into the start method

        this.connectionHandler = connectionHandler;

        this.setEnabled(true);
        this.mouseListenerSetup();
        this.clear();
    }


    public CanvasToolsComponent getToolsPanel() {
        return tools;
    }


    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public Player getPlayer() {
        return player;
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


    public void updateDrawer(Player player) {
        if(player.getPlayerName().equals(this.player.getPlayerName())) {
            this.player.setIsDrawer(true);
        } else {
            this.player.setIsDrawer(false);
        }
    }

    public JPanel initialiseDrawingBoard() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g)
            {
                System.out.println("testing");
                super.paintComponent(g);
                if (canvasImage == null) return;
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

    public void updateStroke(int brushSize) {
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
        this.canvasImage = new BufferedImage(drawingBoard.getWidth(), drawingBoard.getHeight(), BufferedImage.TYPE_INT_ARGB);
        drawingBoard.addMouseMotionListener(new MouseMotionListener()
        {
            /**
             * TODO: NEED TO ADD NETWORKING IMPLEMENTATION TO THE CANVAS DRAWING
             * @param e
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if(player.getIsDrawer()) {
                    onDrag(e.getPoint());
                    connectionHandler.sendPacket(new PaintPacket(PaintPacket.PaintEvents.DRAG, e.getPoint()));
                }
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
                    if(player.getIsDrawer()) {
                        onPress(e.getPoint());
                        connectionHandler.sendPacket(new PaintPacket(PaintPacket.PaintEvents.PRESSED, e.getPoint()));
                    }

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {

                if(player.getIsDrawer()) {
                    onRelease(e.getPoint());
                    connectionHandler.sendPacket(new PaintPacket(PaintPacket.PaintEvents.RELEASED, e.getPoint()));
                }
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
        g.setColor(tools.getSelectedColor());
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
