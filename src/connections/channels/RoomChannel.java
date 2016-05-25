package connections.channels;

import connections.Peer;
import connections.messages.Header;
import connections.messages.Message;

import java.io.IOException;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class RoomChannel extends Channel {
    private boolean listening = true;
    public RoomChannel(String mcAddress, int mcPort) throws IOException {
        super(mcAddress, mcPort);
        this.setThread(new RoomThread());
    }

    public class RoomThread extends Thread {
        public void run() {
            while (listening) {
                joinGroup();
                byte[] msg = rcvMultiCastMsg();
                Header msgHeader = Message.getHeaderFromData(msg);
                if (!Peer.getInstance().getPeerID().equals(msgHeader.getSenderID()))
                    switch (msgHeader.getType()) {
                        default:
                            System.out.println(getChannelTag() + "Message with type (" + msgHeader.getType() + ") is not supported");
                            break;
                    }
                leaveGroup();
            }
        }

    }
}
