package connections.data;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class DataBase {

    HashMap<String, Vector> availableRooms;
    RoomInfo currentRoom;
    static DataBase instance;

    DataBase () {
        currentRoom = null;
        instance = this;
    }

    public static DataBase getInstance() {
        return instance;
    }

    public RoomInfo getCurrentRoom() {
        return currentRoom;
    }
}
