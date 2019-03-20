package game.networking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

public class GameNotifications extends JDialog {

    String msg;
    JFrame frame;

    public static final int LENGTH_LONG = 1300;

    public GameNotifications(JFrame frame, boolean modal, String msg) {
        super(frame, modal);
        this.msg = msg;
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(), 30, 30));
            }
        });
        setUndecorated(true);
        setSize(450,100);
        setLocationRelativeTo(frame);
        getContentPane().setBackground(new Color(36, 67, 117));

        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        final boolean isTranslucencySupported =
                gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT);

        //If shaped windows aren't supported, exit.
        if (!gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT)) {
            System.err.println("Shaped windows are not supported");
        }
        //If translucent windows are supported,
        //create an opaque window.
        // Set the window to 50% translucency, if supported.
        if (isTranslucencySupported) {
            setOpacity(0.5f);
        } else {
            System.out.println("Translucency is not supported.");
        }

        JLabel label = new JLabel();
        label.setFont(new Font("Trebuchet MS", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        label.setText(msg);
        add(label);

    }

}
