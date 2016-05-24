package launcher;

import connections.Peer;
import graphics.InitialFrame;

/**
 * Created by Pedro Fraga on 23-May-16.
 */
class Launcher {
    private Peer peer;

    public static void main(String args[]) {
        Peer peer = new Peer();
        new InitialFrame(peer);
        peer.requestAvailableRooms();
    }
}
