package connections.messages;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class Header {

    public static final String AVAILABLE_ROOMS = "AVAILABLE_ROOMS";

    String type;
    String senderId;
    public Header(String type, String senderId) {
        this.type = type;
        this.senderId = senderId;
    }
    public String toString() { return type + " " + senderId; }

    public String getType() {
        return type;
    }
}
