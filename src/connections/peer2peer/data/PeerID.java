package connections.peer2peer.data;

import org.json.JSONObject;
import utilities.Constants;
import utilities.Utilities;

/**
 * Created by Pedro Fraga on 23-May-16.
 */
public class PeerID {
    String username = "";
    String dateOfCreation;

    public PeerID(String name) {
        this.username = name;
        this.dateOfCreation = Utilities.getCurrentDate();
    }
    public PeerID(String name, String dateOfCreation) {
        this.username = name;
        this.dateOfCreation = dateOfCreation;
    }

    public PeerID(JSONObject peerIdJson) {
        username = peerIdJson.getString(Constants.NAME);
        dateOfCreation = peerIdJson.getString(Constants.DATE_CREATION);
    }

    public String getName() { return username; }
    public String getDateOfCreation() { return dateOfCreation; }

    @Override
    public String toString() {
        JSONObject peerjson = new JSONObject();
        peerjson.put(Constants.NAME, username);
        peerjson.put(Constants.DATE_CREATION, dateOfCreation);
        return peerjson.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeerID peerId = (PeerID) o;
        return username.equals(peerId.getName()) && dateOfCreation.equals(peerId.getDateOfCreation());
    }
    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (dateOfCreation != null ? dateOfCreation.hashCode() : 0);
        return result;
    }

    public void setUsername(String username) { this.username = username; }
}
