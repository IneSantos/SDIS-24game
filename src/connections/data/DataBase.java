package connections.data;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class DataBase {

    HashMap<String, Vector> availableRooms;
    RoomID currentRoom;
    static DataBase instance;

    public DataBase () {
        currentRoom = null;
        instance = this;
    }

    public static DataBase getInstance() { return instance; }

    public RoomID getCurrentRoom() { return currentRoom; }

    public void setCurrentRoom(RoomID room) {
        this.currentRoom = room;
    }
}
