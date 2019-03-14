package chat;

public class Message {

    /**
     *  Message class with setters and getters for the message and username.
     *  Each message object stores the username of the sender and the message itself.
     */

    private String message;
    private String username;

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
