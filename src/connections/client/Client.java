package connections.client;

/**
 * Created by Pedro Fraga on 26-May-16.
 */

import connections.data.PeerID;
import connections.data.RoomID;
import graphics.JoinRoomPanel;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.Constants;
import utilities.Utilities;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {

    private URL url;
    private HttpURLConnection connection;
    private Client instance;
    private HashMap<RoomID, ArrayList<PeerID>> availableRooms;


    public Client() {
        String urlString = "http://localhost:8000/24game";
        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            instance = this;
            availableRooms = new HashMap<>();
        } catch (Exception e) {
           System.err.println("Could not create a client.")
        }
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
        DefaultListModel model = new DefaultListModel();
        for (RoomID room : availableRooms.keySet()) {
            model.addElement(room.getName());
        }
        if (JoinRoomPanel.getInstance() != null)
            JoinRoomPanel.getInstance().getListbox().setModel(model);
        return true;
    }

    private boolean handleRooms(JSONObject rooms) {
        if (rooms.isNull(Constants.ROOMS))
            return false;
        JSONArray roomArray = rooms.getJSONArray(Constants.ROOMS);
        for (int i = 0; i < roomArray.length(); i++) {
            System.out.println(roomArray.get(i));
            JSONObject roomInfo = roomArray.getJSONObject(i);

            JSONObject roomIdJson = roomInfo.getJSONObject(Constants.ROOM_ID);
            RoomID roomId = new RoomID(roomIdJson);
            JSONArray peerArray = roomInfo.getJSONArray(Constants.PEER_ARRAY);
            ArrayList<PeerID> peerIdArray = new ArrayList<>();
            for (int a = 0; a < peerArray.length(); a++) {
                JSONObject peerIdJson = peerArray.getJSONObject(a);
                PeerID peerId = new PeerID(peerIdJson);
                peerIdArray.add(peerId);
            }
            availableRooms.put(roomId, peerIdArray);
        }
        return true;
    }

}
