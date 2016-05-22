package connections.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class RoomID {
    String name;
    String owner;
    String dateOfCreation;

    RoomID (String name, String owner, String dateOfCreation) {
        this.name = name;
        this.owner = owner;
        this.dateOfCreation = dateOfCreation;
    }
    RoomID (String name) {
        this.name = name;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.dateOfCreation = dateFormat.format(date);
    }
}
