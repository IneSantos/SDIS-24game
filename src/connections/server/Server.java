package connections.server;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

/**
 * Created by Pedro Fraga on 25-May-16.
 */
import java.util.ArrayList;
import java.util.HashMap;


import connections.peer2peer.data.PeerID;
import connections.peer2peer.data.RoomID;


public class Server {
    public static HashMap<RoomID, ArrayList<PeerID>> availableRooms;

    public static void main(String[] args) throws Exception {
        availableRooms = new HashMap<>();
        ArrayList<PeerID> peerArray = new ArrayList<>();
        peerArray.add(new PeerID("Fraga"));
        peerArray.add(new PeerID("Memes"));
        availableRooms.put(new RoomID("Sala do Fraga"), peerArray);

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/24game", new RequestHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static HashMap<RoomID, ArrayList<PeerID>> getAvailableRooms() {
        return availableRooms;
    }
}