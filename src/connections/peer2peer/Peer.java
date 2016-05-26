package connections.peer2peer;

import connections.peer2peer.data.DataBase;
import connections.peer2peer.data.PeerID;
import connections.peer2peer.data.RoomID;
import connections.server.messages.ClientMessage;
import org.json.JSONObject;
import utilities.Constants;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Pedro Fraga on 22-May-16.
 */

public class Peer {
    private PeerID peerId;
    private InetAddress adress;
    private int port;
    private DataBase database;
    private static Peer instance;

    public Peer() throws UnknownHostException {
        this.peerId = new PeerID(Constants.ANONYMOUS);
        database = new DataBase();
        adress = InetAddress.getByName(ClientMessage.getHostname());
        instance = this;
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
