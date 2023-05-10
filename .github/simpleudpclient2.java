import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class simpleudpclient2 {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName("localhost");
            int port = 9876;

            double time = 9.81; // Time in seconds

            String sentence = String.valueOf(time);
            byte[] sendData = sentence.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
