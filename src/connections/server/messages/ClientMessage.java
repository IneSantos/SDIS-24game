package connections.server.messages;

import org.json.JSONObject;
import utilities.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pedro Fraga on 26-May-16.
 */
public class ClientMessage {
    private JSONObject jsonMsg;
    private static String hostname = "localhost";

    public ClientMessage(JSONObject json) {
        this.jsonMsg = json;
    }
    public JSONObject send () {
        String urlString = "http://" + hostname + ":8000/24game";
        URL url = null;
        try {
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            PrintWriter out = new PrintWriter(connection.getOutputStream());
            System.out.println(jsonMsg);
            out.println(jsonMsg);
            out.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line = in.readLine();
            in.close();
            JSONObject rooms = new JSONObject(line);
            return rooms;
        } catch (Exception e) {
            return null;
        }
    }

    public int handleCreateRoom(JSONObject serverResponse) {
        System.out.println(serverResponse);
        String result = serverResponse.getString(Constants.CREATE_ROOM);
        return Integer.parseInt(result);
    }
    public static String getHostname() {
        return hostname;
    }
}
