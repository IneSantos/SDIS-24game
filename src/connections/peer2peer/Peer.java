package connections.peer2peer;

import connections.peer2peer.data.DataBase;
import connections.peer2peer.data.PeerID;
import connections.peer2peer.data.RoomID;
import connections.server.messages.ClientMessage;
import org.json.JSONObject;
import utilities.Constants;

import java.io.IOException;
import java.net.*;

/**
 * Created by Pedro Fraga on 22-May-16.
 */

public class Peer extends Thread {
    private PeerID peerId;

    private InetAddress adress;
    private int port;
    private DatagramSocket socket;

    private DataBase database;
    private static Peer instance;

    public Peer() throws UnknownHostException, SocketException {
        this.peerId = new PeerID(Constants.ANONYMOUS);
        database = new DataBase();
        adress = InetAddress.getByName(ClientMessage.getHostname());
        socket = new DatagramSocket();
        instance = this;
    }


    public PeerID getPeerID() {
        return this.peerId;
    }

    public static Peer getInstance() {
        return instance;
    }

    public void run() {
        try {
            String response = sendRequest(Constants.R_U_THERE);
            if (!response.equals(Constants.R_U_THERE_ACK))
                return;
        } catch (Exception e) {
            System.err.println("Could not send a message through tcp");
        }
        while (true) {
            try {
                byte[] buf = new byte[utilities.Constants.MSG_SIZE];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                this.socket.receive(packet);
                String response = new String(packet.getData(), 0, packet.getLength());
                if (response.equals(Constants.R_U_THERE)) {
                    buf = Constants.R_U_THERE_ACK.getBytes();
                    packet = new DatagramPacket(buf, buf.length, adress, port);
                    socket.send(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createRoom(String roomName) {
        RoomID createdRoom = new RoomID(roomName);
        database.setCurrentRoom(createdRoom);

        JSONObject peerInfo = new JSONObject();
        peerInfo.put(Constants.PEER_ID, new JSONObject(peerId));
        peerInfo.put(Constants.ROOM_ID, new JSONObject(createdRoom));

        JSONObject msgJson = new JSONObject();
        msgJson.put(Constants.REQUEST, Constants.CREATE_ROOM);
        msgJson.put(Constants.CREATE_ROOM, peerInfo);
        ClientMessage msg = new ClientMessage(msgJson);
        port = msg.handleCreateRoom(msg.send());
        start();
    }

    public String sendRequest(String request) throws IOException {
        byte[] buf = request.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, adress, port);
        socket.send(packet);

        buf = new byte[utilities.Constants.MSG_SIZE];
        packet = new DatagramPacket(buf, buf.length);
        this.socket.receive(packet);
        String response = new String(packet.getData(), 0, packet.getLength());
        return response;
    }

    public DataBase getDataBase() {
        return database;
    }

    public void setPeerUsername(String username) {
        this.peerId.setUsername(username);
    }

    ;

    public void createRoom(String roomName, String nickName) {
        setPeerUsername(nickName);
        createRoom(roomName);
    }

    public void joinRoom(RoomID room) {
        database.setCurrentRoom(room);
        JSONObject peerInfo = new JSONObject();
        peerInfo.put(Constants.PEER_ID, new JSONObject(peerId));
        peerInfo.put(Constants.ROOM_ID, new JSONObject(room));

        JSONObject msgJson = new JSONObject();
        msgJson.put(Constants.REQUEST, Constants.JOIN_ROOM);
        msgJson.put(Constants.JOIN_ROOM, peerInfo);

        ClientMessage msg = new ClientMessage(msgJson);
        port = msg.handleJoinRoom(msg.send());
        start();
    }
}
