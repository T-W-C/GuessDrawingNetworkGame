import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
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
    private JPanel tools;
    private CanvasDrawingBoard drawingBoard;
    /**
     * brush tools not implemented atm, since they were included with the original
     * tool panel in one big tool panel.
     */
    private BrushTools brushTools;
    private Stroke brushSize = new BasicStroke(10, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND, 30f);
    private BrushColor brushColor;

    public CanvasComponent() {
        super();
        drawingBoard = new CanvasDrawingBoard();
        tools = new CanvasToolsComponent();
        tools.setBorder(new BevelBorder(BevelBorder.LOWERED));
//        brushTools = new BrushTools();

        add(drawingBoard, BorderLayout.CENTER);
//        add(brushTools, BorderLayout.WEST);
        add(tools, BorderLayout.SOUTH);

    }




}
