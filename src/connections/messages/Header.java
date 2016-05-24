package connections.messages;

import connections.data.PeerID;
import connections.data.RoomID;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class Header {

    public static final String R_U_THERE = "R-U-THERE";
    public static final String R_U_THERE_ACK = "R-U-THERE-ACK";
    String type;
    PeerID senderId;
    RoomID roomId;
    public Header(String type, PeerID senderId) {
        this.type = type;
        this.senderId = senderId;
        this.roomId = null;
    }
    public Header(String type, PeerID senderId, RoomID roomId) {
        this.type = type;
        this.senderId = senderId;
        this.roomId = roomId;
    }
    public String toString() {
        String string = "";
        string += type + " " + senderId.toString();
        string += roomId == null ? "" : " " + roomId.toString();
        return string;
    }
    public String getType() { return type; }
    public void setRoomID(RoomID roomID) { this.roomId = roomID; }

    public PeerID getSenderID() {
        return senderId;
    }

    public RoomID getRoomID() {
        return roomId;
    }
}
