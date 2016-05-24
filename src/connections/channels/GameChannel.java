package connections.channels;


import connections.Peer;
import connections.data.DataBase;
import connections.messages.Header;
import connections.messages.Message;
import utilities.Constants;

import java.io.IOException;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class GameChannel extends Channel {

    private boolean listening = true;
    public GameChannel(String mcAddress, int mcPort) throws IOException {
        super(mcAddress, mcPort);
        this.setThread(new GameThread());
    }

    public class GameThread extends Thread {
        public void run() {
            while (listening) {
                joinGroup();
                byte[] msg = rcvMultiCastMsg();
                Header msgHeader = Message.getHeaderFromData(msg);
                if (!Peer.getInstance().getPeerID().equals(msgHeader.getSenderID()))
                switch (msgHeader.getType()) {
                    case Header.AVAILABLE_ROOMS:
                        handleAvailableRooms();
                        break;
                    case Header.ROOM:
                        handleRoom(msgHeader);
                        break;
                    default:
                        System.out.println(getChannelTag() + "Message with type (" + msgHeader.getType() + ") is not supported");
                        break;
                }
                leaveGroup();
            }
        }

    }

    public void stopListen() { listening = false; }
    private void handleAvailableRooms() {
        System.out.println("Available rooms request received");
        if (DataBase.getInstance().getCurrentRoom() == null) return;
        Header replyHeader = new Header(Header.ROOM, Peer.getInstance().getPeerID(), Peer.getInstance().getDataBase().getCurrentRoom());
        Message reply = new Message(getSocket(), getAddress(), replyHeader);
        reply.send();
    }
    private void handleRoom(Header header) {
        Peer.getInstance().getDataBase().updateRooms(header);
    }
}
