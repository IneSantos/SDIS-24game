package launcher;

import connections.client.Client;
import graphics.initialFrame.InitialFrame;

/**
 * Created by Pedro Fraga on 23-May-16.
 */
class Launcher {

    public static void main(String args[]) {
        Client client = new Client();
        new InitialFrame();
        client.requestAvailableRooms();
    }
}
