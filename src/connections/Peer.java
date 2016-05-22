package connections;

import connections.channels.GameChannel;
import connections.messages.Header;
import connections.messages.Message;
import utilities.Constants;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * Created by Pedro Fraga on 22-May-16.
 */

public class Peer {
    private String id;
    private DatagramSocket socket;

    private GameChannel gameChannel;

    public Peer() {
        this.id = Constants.ANONYMOUS;
        try {
            this.gameChannel = new GameChannel(Constants.DEFAULT_ADDRESS, Constants.GAME_PORT);
        } catch (IOException e) {
            System.err.println("Error: could not create a game channel");
        }
        gameChannel.listen();
    }

    public static void main(String[] args) {
        Peer peer = new Peer();
        peer.requestAvailableRooms();
    }

    private void requestAvailableRooms() {
        Header header = new Header(Header.AVAILABLE_ROOMS, id);
        Message message = new Message(gameChannel.getSocket(), gameChannel.getAddress(), header);
        message.send();
    }
}
