package connections.data;

import utilities.Utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class RoomID {
    String name;
    String dateOfCreation;

    public RoomID (String name, String dateOfCreation) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
    }
    public RoomID (String name) {
        this.name = name;
        this.dateOfCreation = Utilities.getCurrentDate();
    }

    @Override
    public String toString() {
        return name + " " + dateOfCreation;
    }
}
