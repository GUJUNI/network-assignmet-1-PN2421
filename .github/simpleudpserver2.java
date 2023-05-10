import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class simpleudpserver2 {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9876);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData());
            double time = Double.parseDouble(sentence.trim());

            double distance = 828; // Height of Burj Khalifa in meters

            double speed = distance / time;
            System.out.println("Average Speed: " + speed + " m/s");

            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
