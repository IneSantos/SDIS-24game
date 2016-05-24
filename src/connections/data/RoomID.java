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
    boolean answering;

    public RoomID (String name, String dateOfCreation) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.answering = false;
    }
    public RoomID (String name) {
        this.name = name;
        this.dateOfCreation = Utilities.getCurrentDate();
        this.answering = false;
    }

    @Override
    public String toString() {
        return name + " " + dateOfCreation;
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
