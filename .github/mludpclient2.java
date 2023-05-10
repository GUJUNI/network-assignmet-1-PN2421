import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class mludpclient2 {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 1234;

            double mass = 100;

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            String message = Double.toString(mass);
            sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Gravitational pull on the poles of the earth: " + response + " N");

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
