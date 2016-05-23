package connections.messages;

import connections.data.PeerID;
import connections.data.RoomID;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class Header {

    public static final String AVAILABLE_ROOMS = "AVAILABLE_ROOMS";
    public static final String ROOM = "ROOM";
    String type;
    PeerID senderId;
    RoomID room;
    public Header(String type, PeerID senderId) {
        this.type = type;
        this.senderId = senderId;
        this.room = null;
    }
    public Header(String type, PeerID senderId, RoomID roomId) {
        this.type = type;
        this.senderId = senderId;
        this.room = roomId;
    }
    public String toString() {
        String string = "";
        string += type + " " + senderId.toString();
        string += room == null ? "" : " " + room.toString();
        return string;
    }
    public String getType() { return type; }
    public void setRoomID(RoomID roomID) { this.room = roomID; }

    public PeerID getSenderID() {
        return senderId;
    }
}
