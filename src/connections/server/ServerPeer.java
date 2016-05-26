package connections.server;

import utilities.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Pedro Fraga on 26-May-16.
 */
public class ServerPeer extends Thread {
    private DatagramSocket socket;
    private int port;
    private int client_port;
    private InetAddress address;
    private InetAddress client_address;
    private boolean listening;

    public ServerPeer () throws SocketException {
        socket = new DatagramSocket();
        port = socket.getLocalPort();
        client_port = -1;
        listening = true;
        address = null;
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
        while (listening) {
            try {
                String msg = rcvTcpData();
                System.out.println("TCP is working: " + msg);
                String resp = "response";
                sendTcpData(resp);
            } catch (IOException e) {
                System.err.println("Could not receive tcp message");
            }
        }
    }

    private void sendTcpData(String msg) throws IOException {
        byte[] sbuf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(sbuf, sbuf.length, client_address, client_port);
        socket.send(packet);
    }
}
