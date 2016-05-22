package connections.channels;


import connections.data.DataBase;
import connections.messages.Header;
import connections.messages.Message;

import java.io.IOException;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class GameChannel extends Channel {

    public GameChannel(String mcAddress, int mcPort) throws IOException {
        super(mcAddress, mcPort);
        this.setThread(new GameThread());
    }

    public class GameThread extends Thread {
        public void run() {
            while (true) {
                joinGroup();
                byte[] msg = rcvMultiCastMsg();
                Header msgHeader = Message.getHeaderFromData(msg);
                switch (msgHeader.getType()) {
                    case Header.AVAILABLE_ROOMS:
                        handleAvailableRooms();
                        break;
                    default:
                        System.out.println(getChannelTag() + "Message with type (" + msgHeader.getType() + ") is not supported");
                        break;
                }
                leaveGroup();
            }
        }

    }

    private void handleAvailableRooms() {
        if (DataBase.getInstance().getCurrentRoom() == null) return;
    }
}
