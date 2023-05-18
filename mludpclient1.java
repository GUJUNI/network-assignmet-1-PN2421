import java.net.*;

public class mludpclient1 {
    private static final int PORT = 12345;
    private static final int PACKET_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("localhost");

            byte[] sendData = new byte[PACKET_SIZE];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, PORT);
            socket.send(sendPacket);

            byte[] receiveData = new byte[PACKET_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Oxygen percentage in atmosphere: " + result + "%");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
