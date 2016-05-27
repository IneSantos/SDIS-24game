package connections.peer2peer.data;

import org.json.JSONObject;
import utilities.Constants;
import utilities.Utilities;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class RoomID {
    String name;
    String dateOfCreation;


    public RoomID(String name, String dateOfCreation) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }

    public RoomID(String name) {
        this.name = name;
        this.dateOfCreation = Utilities.getCurrentDate();
    }

    public RoomID(JSONObject roomIdJson) {
        name = roomIdJson.getString(Constants.NAME);
        dateOfCreation = roomIdJson.getString(Constants.DATE_CREATION);
    }

    @Override
    public String toString() {
        JSONObject roomjson = new JSONObject();
        roomjson.put(Constants.NAME, name);
        roomjson.put(Constants.DATE_CREATION, dateOfCreation);
        return roomjson.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomID roomID = (RoomID) o;

        return name.equals(roomID.getName()) && dateOfCreation.equals(roomID.getDateOfCreation());
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dateOfCreation != null ? dateOfCreation.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }
}
