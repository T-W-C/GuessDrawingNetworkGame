import java.awt.*;
import java.io.Serializable;

public class PaintPacket implements Serializable {

    public enum PaintEvents {
        RELEASED,
        CLEAR,
        DRAG,
        PRESSED,
        CHANGE_COLOR
    }

    public PaintEvents eventType;

    public Point point;
    public int index;

    /**
     * one variation of a paint packet contains the event type as well as the point
     * it is associated with
     * @param eventType
     * @param point
     */
    public PaintPacket(PaintEvents eventType, Point point) {

        this.eventType = eventType;
        this.point = point;

    }

    /**
     * second variation of a paint packet contains the event type
     * as well as the index
     * @param eventType
     * @param index
     */
    public PaintPacket(PaintEvents eventType, int index) {

        this.eventType = eventType;
        this.index = index;
    }



}
