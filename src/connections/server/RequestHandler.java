package connections.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import connections.data.PeerID;
import connections.data.RoomID;
import org.json.JSONArray;
import org.json.JSONException;
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
public class RequestHandler implements HttpHandler  {

    @Override
    public void handle(HttpExchange t) throws IOException {
        InputStream in = t.getRequestBody();
        String request = Utilities.getBytesFromInputStream(in);
        JSONObject jsonRequest;
        if (Utilities.isJSONValid(request)) {
            jsonRequest = new JSONObject(request);
            request = jsonRequest.getString(Constants.REQUEST);
        } else {
            request = Constants.ERROR_STRING;
        }
        handleRequest(request, t);
        in.close();
    }

    private void handleRequest(String request, HttpExchange t) throws IOException {
        switch(request) {
            case Constants.GET_ROOMS:
                JSONObject roomsJson = new JSONObject();
                JSONArray roomsArray = new JSONArray();
                for (Map.Entry<RoomID, ArrayList<PeerID>> entry : Server.getAvailableRooms().entrySet()) {
                    JSONObject roomAndPeers = new JSONObject();

                    RoomID roomId = entry.getKey();
                    ArrayList<PeerID> peerArray = entry.getValue();
                    JSONObject roomIdJson = new JSONObject(roomId);
                    roomAndPeers.put(Constants.ROOM_ID, roomIdJson);
                    roomAndPeers.put(Constants.PEER_ARRAY, peerArray);
                    roomsArray.put(roomAndPeers);
                }
                roomsJson.put(Constants.ROOMS, roomsArray);
                sendJson(roomsJson, t, Constants.OK);
                break;
            default:
                System.err.println("Unknown request (" + request + ")");
                JSONObject jsonError = new JSONObject();
                jsonError.put(Constants.ERROR_STRING, Constants.NOT_FOUND);
                sendJson(jsonError, t, Constants.OK);
                break;
        }
    }

    private void sendJson(JSONObject json, HttpExchange t, int code) throws IOException {
        OutputStream os = t.getResponseBody();
        String response = json.toString();
        t.sendResponseHeaders(code, response.length());
        os.write(response.getBytes());
        os.close();
    }



}
