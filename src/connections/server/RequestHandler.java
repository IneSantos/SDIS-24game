package connections.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import connections.peer2peer.data.PeerID;
import connections.peer2peer.data.RoomID;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.Constants;
import utilities.Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Pedro Fraga on 26-May-16.
 */
public class RequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        InputStream in = t.getRequestBody();
        String request = Utilities.getBytesFromInputStream(in);
        handleRequest(request, t);
        in.close();
    }

    private void handleRequest(String request, HttpExchange t) throws IOException {
        JSONObject jsonRequest = new JSONObject();
        if (Utilities.isJSONValid(request)) {
            jsonRequest = new JSONObject(request);
            request = jsonRequest.getString(Constants.REQUEST);
        } else {
            request = Constants.ERROR_STRING;
        }
        switch (request) {
            case Constants.GET_ROOMS:
                JSONObject roomsJson = new JSONObject();
                JSONArray roomsArray = new JSONArray();
                if (Server.getAvailableRooms() == null)
                    System.out.println("get rooms null");

                for (Map.Entry<RoomID, ArrayList<PeerID>> entry : Server.getAvailableRooms().entrySet()) {
                    JSONObject roomAndPeers = new JSONObject();

                    RoomID roomId = entry.getKey();
                    ArrayList<PeerID> peerArray = entry.getValue();
                    JSONArray array = new JSONArray();
                    for (PeerID peer : peerArray) {
                        JSONObject json = new JSONObject(peer.toString());
                        array.put(json);
                    }
                    JSONObject roomIdJson = new JSONObject(roomId);
                    roomAndPeers.put(Constants.ROOM_ID, roomIdJson);
                    roomAndPeers.put(Constants.PEER_ARRAY, array);
                    roomsArray.put(roomAndPeers);
                }
                roomsJson.put(Constants.ROOMS, roomsArray);
                sendJson(roomsJson, t, Constants.OK);
                break;
            case Constants.CREATE_ROOM:
                JSONObject createdRoom = jsonRequest.getJSONObject(Constants.CREATE_ROOM);
                handleRoom(createdRoom, t, request);
                break;
            case Constants.JOIN_ROOM:
                JSONObject joinRoom = jsonRequest.getJSONObject(Constants.JOIN_ROOM);
                handleRoom(joinRoom, t, request);
                break;
            default:
                System.err.println("Unknown request (" + request + ")");
                JSONObject jsonError = new JSONObject();
                jsonError.put(Constants.ERROR_STRING, Constants.NOT_FOUND);
                sendJson(jsonError, t, Constants.OK);
                break;
        }
    }

    private void handleRoom(JSONObject createdRoom, HttpExchange t, String constraint) throws IOException {
        JSONObject roomJson = createdRoom.getJSONObject(Constants.ROOM_ID);
        JSONObject peerJson = createdRoom.getJSONObject(Constants.PEER_ID);
        RoomID roomId = new RoomID(roomJson);
        PeerID peerId = new PeerID(peerJson);
        ArrayList<PeerID> peerArray;
        boolean found = false;
        if (Server.getAvailableRooms().get(roomId) != null) {
            peerArray = Server.getAvailableRooms().get(roomId);
            if (constraint.equals(Constants.JOIN_ROOM)) {
                JSONObject joinedJson = new JSONObject();
                joinedJson.put(Constants.REQUEST, Constants.JOINED_ROOM);
                joinedJson.put(Constants.PEER_ID, peerJson);
                for (int i = 0; i < peerArray.size(); i++) {
                    found = true;
                    peerArray.get(i).getServerPeer().add2MsgArray(joinedJson);
                }
            }
            peerArray.add(peerId);
        } else {
            peerArray = new ArrayList<>();
            peerArray.add(peerId);
            Server.getAvailableRooms().put(roomId, peerArray);
        }
        String message = Constants.ERROR + "";
        if (found) {
            ServerPeer serverPeer = new ServerPeer(roomId, peerId);
            serverPeer.start();
            Server.getEstablishedConnections().put(peerId, serverPeer.getPort());
            message = serverPeer.getPort() + "";
        }
        JSONObject jsonOk = new JSONObject();
        jsonOk.put(constraint, message);
        getGame(roomId);
        if (roomId.getCurrentGame() == null) {
            roomId.set24Game(Server.getGame24().getRandomGame());
        }
        JSONArray array = new JSONArray(roomId.getCurrentGame());
        jsonOk.put(Constants.GAME, array);
        sendJson(jsonOk, t, Constants.OK);
    }

    private void sendJson(JSONObject json, HttpExchange t, int code) throws IOException {
        OutputStream os = t.getResponseBody();
        String response = json.toString();
        t.sendResponseHeaders(code, response.length());
        os.write(response.getBytes());
        os.close();
    }


    public void getGame(RoomID roomId) {
        for (RoomID room : Server.getAvailableRooms().keySet()) {
            if (room.equals(roomId)) {
                if (room.getCurrentGame() == null) {
                    System.out.println("Game null");
                    roomId.set24Game(Server.getGame24().getRandomGame());
                    return;
                } else {
                    System.out.println("Game found");
                    roomId.set24Game(room.getCurrentGame());
                    return;
                }
            }
        }
        System.out.println("Game not found");
        roomId.set24Game(Server.getGame24().getRandomGame());
    }
}
