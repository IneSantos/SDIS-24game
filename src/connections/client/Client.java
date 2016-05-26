package connections.client;

/**
 * Created by Pedro Fraga on 26-May-16.
 */

import connections.data.PeerID;
import connections.data.RoomID;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.Constants;
import utilities.Utilities;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {

    private URL url;
    private HttpURLConnection connection;
    private Client instance;
    private HashMap<RoomID, ArrayList<PeerID>> availableRooms;


    public Client() throws IOException {
        String urlString = "http://localhost:8000/24game";
        url = new URL(urlString);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        instance = this;
    }

    public boolean requestAvailableRooms() throws IOException {
        PrintWriter out = new PrintWriter(connection.getOutputStream());
        JSONObject message = new JSONObject();
        message.put(Constants.REQUEST, Constants.GET_ROOMS);
        System.out.println(message);
        out.println(message);
        out.close();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String line = in.readLine();
        JSONObject rooms = new JSONObject(line);
        System.out.println(rooms);
        if (!handleRooms(rooms)) {
            System.err.println("Cant handle");
            return false;
        }
        System.out.println(availableRooms.toString());
        in.close();
        return true;
    }

    private boolean handleRooms(JSONObject rooms) {
        if (rooms.isNull(Constants.ROOMS))
            return false;
        JSONArray roomArray = rooms.getJSONArray(Constants.ROOMS);
        for (int i = 0; i < roomArray.length(); i++) {
            System.out.println(roomArray.get(i));
            JSONArray roomInfo = new JSONArray(roomArray.get(i));
            JSONObject roomIdJson = new JSONObject(roomInfo.get(0));
            RoomID roomId = new RoomID(roomIdJson);
            JSONObject peerInfo = new JSONObject(roomInfo.get(1));
            JSONArray peerArray = new JSONArray(peerInfo.get(Constants.PEER_ARRAY));
            ArrayList<PeerID> peerIdArray = new ArrayList<>();
            for (int a = 0; a < peerArray.length(); a++) {
                JSONObject peerIdJson = new JSONObject(peerArray.get(a));
                PeerID peerId = new PeerID(peerIdJson);
                peerIdArray.add(peerId);
            }
            availableRooms.put(roomId, peerIdArray);
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        if (!client.requestAvailableRooms())
            System.err.println("Returning false");

    }
}
