package connections.server;

import com.sun.net.httpserver.HttpServer;
import connections.peer2peer.data.PeerID;
import connections.peer2peer.data.RoomID;
import game.Game24;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pedro Fraga on 25-May-16.
 */


public class Server {
    public static HashMap<RoomID, ArrayList<PeerID>> availableRooms;
    public static HashMap<PeerID, Integer> establishedConnections;
    public static Game24 game;

    public static void main(String[] args) throws Exception {
        availableRooms = new HashMap<>();
        establishedConnections = new HashMap<>();
        game = new Game24();
        game.readFile();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/24game", new RequestHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static HashMap<RoomID, ArrayList<PeerID>> getAvailableRooms() {
        return availableRooms;
    }
    public static HashMap<PeerID, Integer> getEstablishedConnections() {
        return establishedConnections;
    }

    public static Game24 getGame24() {
        return game;
    }
}