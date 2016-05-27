package connections.server;

import connections.peer2peer.data.PeerID;
import connections.peer2peer.data.RoomID;
import org.json.JSONObject;
import utilities.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by Pedro Fraga on 26-May-16.
 */
public class ServerPeer extends Thread {

    private DatagramSocket socket;
    private int port;
    private int client_port;
    private InetAddress client_address;

    private RoomID roomId;
    private PeerID peerId;

    private boolean listening;
    private ArrayList<JSONObject> messagesArray;
    private int tries;

    public ServerPeer(RoomID roomId, PeerID peerId) throws SocketException {
        socket = new DatagramSocket(0);
        socket.setSoTimeout(1300);
        port = socket.getLocalPort();
        client_port = -1;
        this.roomId = roomId;
        this.peerId = peerId;
        listening = true;
        messagesArray = new ArrayList<>();
        peerId.setServerPeer(this);
        tries = 0;
    }

    public int getPort() {
        return port;
    }

    private String rcvTcpData() throws IOException {
        byte[] rbuf = new byte[Constants.MSG_SIZE];
        DatagramPacket packet = new DatagramPacket(rbuf, rbuf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        client_port = packet.getPort();
        client_address = packet.getAddress();
        return received;
    }

    public void run() {
        try {
            String msg = rcvTcpData();
            JSONObject jsonMsg = new JSONObject(msg);
            String request = jsonMsg.getString(Constants.REQUEST);
            switch (request) {
                case Constants.R_U_THERE:
                    jsonMsg = new JSONObject();
                    jsonMsg.put(Constants.REQUEST, Constants.R_U_THERE_ACK);
                    sendTcpData(jsonMsg.toString());
                    checkPeer();
                    break;
            }
        } catch (IOException e) {
            System.err.println("Could not receive tcp message");
        }
    }

    private void checkPeer() {
        RcvAck rcvAck;
        while (listening) {
            try {
                JSONObject jsonMsg;
                if (messagesArray.size() == 0) {
                    jsonMsg = new JSONObject();
                    jsonMsg.put(Constants.REQUEST, Constants.R_U_THERE);
                    sendTcpData(jsonMsg.toString());
                } else {
                    jsonMsg = messagesArray.get(messagesArray.size() - 1);
                    messagesArray.remove(jsonMsg);
                    sendTcpData(jsonMsg.toString());
                }
                rcvAck = new RcvAck(this);
                rcvAck.start();
                int ms = 1500;
                Thread.sleep(ms);
                tries++;
                if (tries > 3) {
                    listening = false;
                    rcvAck.interrupt();
                }
            } catch (Exception e) {
                System.err.println("Something was wrong");
            }
        }
        ArrayList<PeerID> peers = Server.getAvailableRooms().get(roomId);
        peers.remove(peerId);
        if (peers.size() == 0)
            Server.getAvailableRooms().remove(roomId);
        this.socket.close();
        System.err.println("Peer " + peerId.getName() + " connection was closed (peer size = " + peers.size() + ")");
    }

    private void sendTcpData(String msg) throws IOException {
        byte[] sbuf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(sbuf, sbuf.length, client_address, client_port);
        socket.send(packet);
    }

    public PeerID getPeerId() {
        return peerId;
    }

    public void add2MsgArray(JSONObject jsonMsg) {
        messagesArray.add(jsonMsg);
    }


    class RcvAck extends Thread {
        ServerPeer context;

        public RcvAck(ServerPeer context) {
            this.context = context;
        }

        public void run() {
            try {
                String response = context.rcvTcpData();
                JSONObject jsonMsg = new JSONObject(response);
                String request = jsonMsg.getString(Constants.REQUEST);
                switch (request) {
                    case Constants.R_U_THERE_ACK:
                        context.resetTries();
                        break;
                }
            } catch (IOException e) {
                System.err.println("Peer " + context.getPeerId().getName() + " timing out at port " + context.getPort());
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        this.socket.close();
    }

    private void resetTries() {
        tries = 0;
    }
}

