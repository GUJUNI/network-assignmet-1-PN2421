import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class mludpserver2 {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(1234);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            System.out.println("Server started. Waiting for client...");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client message: " + message);

                double mass = Double.parseDouble(message);
                double gravitationalForce = calculateGravitationalForce(mass);

                String response = Double.toString(gravitationalForce);
                sendData = response.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);

                System.out.println("Gravitational pull sent to the client: " + response);
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double calculateGravitationalForce(double mass) {
        double G = 6.674 * Math.pow(10, -11);
        double M = 5.97 * Math.pow(10, 24);
        double r = 6.371 * Math.pow(10, 6);

        return (G * mass * M) / Math.pow(r, 2);
    } 
}
