package connections.server;

import connections.peer2peer.data.PeerID;
import connections.peer2peer.data.RoomID;
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

    private static ServerPeer instance;

    private boolean listening;

    private int tries;

    public ServerPeer (RoomID roomId, PeerID peerId) throws SocketException {
        socket = new DatagramSocket();
        port = socket.getLocalPort();
        client_port = -1;
        this.roomId = roomId;
        this.peerId = peerId;
        listening = true;
        tries = 0;
        instance = this;
    }

    public static ServerPeer getInstance() {
        return instance;
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
                if (msg.equals(Constants.R_U_THERE)) {
                    sendTcpData(Constants.R_U_THERE_ACK);
                    checkPeer();
                }
            } catch (IOException e) {
                System.err.println("Could not receive tcp message");
            }
    }

    private void checkPeer() {
        while (listening) {
            try {
                int ms = 1500;
                Thread.sleep(ms);
                sendTcpData(Constants.R_U_THERE);
                new rcvAck(instance).start();
                tries++;
            if (tries > 3) {
                listening = false;
            } else {
                System.out.println(tries);
            }
            } catch (Exception e) {
                System.err.println("Something was wrong");
            }
        }
        ArrayList<PeerID> peers = Server.getAvailableRooms().get(roomId);
        peers.remove(peerId);
        if (peers.size() == 0)
            Server.getAvailableRooms().remove(roomId);
    }

    private void sendTcpData(String msg) throws IOException {
        byte[] sbuf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(sbuf, sbuf.length, client_address, client_port);
        socket.send(packet);
    }

    class rcvAck extends Thread {
        ServerPeer context;
        public rcvAck (ServerPeer context) {
            this.context = context;
        }
        public void run () {
            try {
                String response = ServerPeer.getInstance().rcvTcpData();
                System.out.println(response);
                if (response.equals(Constants.R_U_THERE_ACK)) {
                    context.resetTries();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void resetTries() {
        tries = 0;
    }
}

