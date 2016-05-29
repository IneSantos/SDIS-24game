package connections.server.messages;

import connections.peer2peer.Peer;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.Constants;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Pedro Fraga on 26-May-16.
 */
public class ClientMessage {
    private JSONObject jsonMsg;
    private static String hostname = "localhost";

    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier(){

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        if (hostname.equals("localhost")) {
                            return true;
                        }
                        return false;
                    }
                });
    }

    public ClientMessage(JSONObject json) {
        this.jsonMsg = json;
    }

    public JSONObject send() {
        String urlString = "https://" + hostname + ":8000/24game";
        URL url = null;

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            char[] password = "123456".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("server.keys");
            ks.load(fis, password);

            // setup the key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            // setup the trust manager factory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            // setup the HTTPS context and parameters
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setSSLSocketFactory(sslContext.getSocketFactory());


            connection.setRequestProperty("Content-Type", "application/json");


            PrintWriter out = new PrintWriter(connection.getOutputStream());
            System.out.println(jsonMsg);
            String msg = jsonMsg.toString();
            out.println(URLEncoder.encode(msg, "UTF-8"));
            out.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line = in.readLine();
            in.close();
            JSONObject rooms = new JSONObject(line);
            return rooms;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int handleCreateRoom(JSONObject serverResponse) {
        System.out.println(serverResponse);
        String result = serverResponse.getString(Constants.CREATE_ROOM);
        JSONArray array = serverResponse.getJSONArray(Constants.GAME);
        Peer.getInstance().set24Game(array);
        return Integer.parseInt(result);
    }

    public static String getHostname() {
        return hostname;
    }

    public int handleJoinRoom(JSONObject serverResponse) {
        System.out.println(serverResponse);
        String result = serverResponse.getString(Constants.JOIN_ROOM);
        int port = Integer.parseInt(result);
        if (port == Constants.ERROR)
            return Constants.ERROR;
        JSONArray array = serverResponse.getJSONArray(Constants.GAME);
        String name = serverResponse.getString(Constants.NAME);
        Peer.getInstance().set24Game(array);
        Peer.getInstance().getPeerID().setUsername(name);

        return port;
    }
}