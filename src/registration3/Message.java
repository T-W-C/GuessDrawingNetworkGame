package registration3;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private Enum type;

    public Message(Enum type, String message) {
        this.type = type;
        this.message = message;
    }

    public Enum getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
