package connections;

import connections.channels.GameChannel;
import connections.data.DataBase;
import connections.data.PeerID;
import connections.data.RoomID;
import connections.messages.Header;
import connections.messages.Message;
import utilities.Constants;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * Created by Pedro Fraga on 22-May-16.
 */

public class Peer {
    private PeerID peerId;
    private DatagramSocket socket;
    private GameChannel gameChannel;
    private DataBase database;
    private static Peer instance;

    public Peer() {
        this.peerId = new PeerID(Constants.ANONYMOUS);
        try {
            this.gameChannel = new GameChannel(Constants.DEFAULT_ADDRESS, Constants.GAME_PORT);
        } catch (IOException e) {
            System.err.println("Error: could not create a game channel");
        }
        database = new DataBase();
        gameChannel.listen();
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
    public GameChannel getGameChannel() { return gameChannel; }

    public void requestAvailableRooms() {
        Header header = new Header(Header.R_U_THERE, peerId);
        Message message = new Message(gameChannel.getSocket(), gameChannel.getAddress(), header);
        message.send();
    }

    public void createRoom(String roomName) {
        RoomID createdRoom = new RoomID(roomName);
        database.setCurrentRoom(createdRoom);
    }

    public DataBase getDataBase() { return database; }
    public void setPeerUsername (String username) { this.peerId.setUsername(username); };
}
