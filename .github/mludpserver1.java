import java.net.*;
import java.util.Random;

public class mludpserver1 {
    private static final int PORT = 12345;
    private static final int PACKET_SIZE = 1024;
    private static final double ATMOSPHERE_OXYGEN_PERCENTAGE = 20.95;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                byte[] receiveData = new byte[PACKET_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                Thread thread = new Thread(new OxygenCalculatorThread(socket, clientAddress, clientPort));
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class OxygenCalculatorThread implements Runnable {
        private final DatagramSocket socket;
        private final InetAddress clientAddress;
        private final int clientPort;
        private final Random random;

        public OxygenCalculatorThread(DatagramSocket socket, InetAddress clientAddress, int clientPort) {
            this.socket = socket;
            this.clientAddress = clientAddress;
            this.clientPort = clientPort;
            this.random = new Random();
        }

        @Override
        public void run() {
            try {
                Thread.sleep(random.nextInt(1000)); // simulate processing delay

                double oxygenPercentage = ATMOSPHERE_OXYGEN_PERCENTAGE - random.nextDouble();
                String result = String.format("%.2f", oxygenPercentage);

                byte[] sendData = result.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
