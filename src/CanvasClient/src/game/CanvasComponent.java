import javax.swing.*;
import java.awt.*;

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
    private CanvasDrawingBoard drawingBoard;
    private Stroke brushSize = new BasicStroke(10, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND, 30f);
    private BrushColor brushColor;






}
