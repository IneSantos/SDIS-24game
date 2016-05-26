package connections;

import connections.data.DataBase;
import connections.data.PeerID;
import connections.data.RoomID;
import connections.messages.ClientMessage;
import org.json.JSONObject;
import utilities.Constants;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * Created by Pedro Fraga on 22-May-16.
 */

public class Peer {
    private PeerID peerId;
    private DatagramSocket socket;
    private DataBase database;
    private static Peer instance;

    public Peer() {
        this.peerId = new PeerID(Constants.ANONYMOUS);
        database = new DataBase();
        this.instance = this;
    }

    public static void main(String[] args) {
        Peer peer = new Peer();
        if (args[0].equals("createRoom")) {
            if (args.length == 3) {
                peer.setPeerUsername(args[2]);
                peer.createRoom(args[1]);
            }
        } else if (args[0].equals("requestRooms")) {
            if (args.length == 1)
                peer.requestAvailableRooms();
        } else if (args.length == 0) {

        }
    }

    public PeerID getPeerID() { return this.peerId; }
    public static Peer getInstance() { return instance; }

    public void requestAvailableRooms() {
    }

    public boolean createRoom(String roomName) {
        RoomID createdRoom = new RoomID(roomName);
        database.setCurrentRoom(createdRoom);

        JSONObject peerInfo = new JSONObject();
        peerInfo.put(Constants.PEER_ID, new JSONObject(peerId));
        peerInfo.put(Constants.ROOM_ID, new JSONObject(createdRoom));

        JSONObject msgJson = new JSONObject();
        msgJson.put(Constants.REQUEST, Constants.CREATE_ROOM);
        msgJson.put(Constants.CREATE_ROOM, peerInfo);
        ClientMessage msg = new ClientMessage(msgJson);
        return msg.handleCreateRoom(msg.send());
    }

    public DataBase getDataBase() { return database; }
    public void setPeerUsername (String username) { this.peerId.setUsername(username); };

    public void createRoom(String roomName, String nickName) {
        setPeerUsername(nickName);
        createRoom(roomName);
    }
}
