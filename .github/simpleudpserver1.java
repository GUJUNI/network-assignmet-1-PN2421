import java.net.*;

public class simpleudpserver1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(12345);

        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String data = new String(packet.getData(), 0, packet.getLength());
            String[] fields = data.split(",");
            String name = fields[0];
            int id = Integer.parseInt(fields[1]);
            double salary = Double.parseDouble(fields[2]);
            double deductions = Double.parseDouble(fields[3]);
            double netSalary = salary - deductions;

            String result = String.format("%.2f", netSalary);
            buffer = result.getBytes();

            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            packet = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
            socket.send(packet);
            socket.close();
        }
    }
}
