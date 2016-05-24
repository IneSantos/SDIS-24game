package connections.data;

import connections.messages.Header;
import graphics.JoinRoomPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class DataBase extends Thread {

    HashMap<RoomID, ArrayList<PeerID>> availableRooms;
    RoomID currentRoom; /* A sala em que o jogador se encontra, null se não está em nenhuma sala */
    static DataBase instance;

    public DataBase () {
        currentRoom = null;
        instance = this;
        availableRooms = new HashMap<>();
    }

    public static DataBase getInstance() { return instance; }

    public RoomID getCurrentRoom() { return currentRoom; }

    public void setCurrentRoom(RoomID room) {
        this.currentRoom = room;
    }

    public void updateRooms(Header header) {
        if(!availableRooms.containsKey(header.getRoomID())) {
            ArrayList<PeerID> newPeer = new ArrayList<>();
            newPeer.add(header.getSenderID());
            availableRooms.put(header.getRoomID(), newPeer);
        } else {
            ArrayList<PeerID> peers = availableRooms.get(header.getRoomID());
            peers.add(header.getSenderID());
            availableRooms.put(header.getRoomID(), peers);
        }
        DefaultListModel model = new DefaultListModel();
        for (RoomID room : availableRooms.keySet()) {
            model.addElement(room.getName());
        }
        if (JoinRoomPanel.getInstance() != null)
            JoinRoomPanel.getInstance().getListbox().setModel(model);
    }

    public void clearAvailableRooms() {
        availableRooms.clear();
        if (JoinRoomPanel.getInstance() != null)
            JoinRoomPanel.getInstance().getListbox().setModel(new DefaultListModel());
    }
}
