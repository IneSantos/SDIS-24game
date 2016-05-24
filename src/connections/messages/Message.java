package connections.messages;

import connections.Peer;
import connections.data.PeerID;
import connections.data.RoomID;
import utilities.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pedro Fraga on 22-May-16.
 */
public class Message implements Runnable {

    private MulticastSocket socket;
    private InetAddress address;
    private Header header;

    public Message(MulticastSocket socket, InetAddress address, Header header) {
        this.socket = socket;
        this.address = address;
        this.header = header;
    }
    public void send() { this.run(); }
    private static String[] splitArgs(String message) { return message.split(" "); }
    public static Header getHeaderFromData(byte[] data) {
        String headerStr = new String(data, 0, data.length);
        String[] splittedHeader = splitArgs(headerStr);
        PeerID peerId = new PeerID(splittedHeader[Constants.USERNAME], splittedHeader[Constants.USER_DATE]);
        Header header = new Header(splittedHeader[Constants.MESSAGE_TYPE], peerId);
        if (splittedHeader[Constants.MESSAGE_TYPE].equals(Header.R_U_THERE_ACK)) {
            RoomID roomId = new RoomID(splittedHeader[Constants.ROOM_ID], splittedHeader[Constants.ROOM_DATE]);
            header.setRoomID(roomId);
        }
        return header;
    }
    @Override
    public void run() {
        if (header.getType().equals(Header.R_U_THERE_ACK)) {
            int timeout = ThreadLocalRandom.current().nextInt(0, 400);
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                System.err.println("[" + address.getHostAddress() + ":" + socket.getLocalPort() + "] Could not sleep while sending an R-U-THERE-ACK");
            }
        }
        byte[] message = header.toString().getBytes();
        DatagramPacket packet = new DatagramPacket(message,
                message.length, address,
                socket.getLocalPort());
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("[" + address.getHostAddress() + ":" + socket.getLocalPort() + "] Could not send message '" + message.toString() + "'");
        }
    }
}
