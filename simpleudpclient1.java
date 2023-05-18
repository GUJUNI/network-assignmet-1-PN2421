import java.net.*;
import java.util.Scanner;

public class simpleudpclient1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter employee salary: ");
        double salary = scanner.nextDouble();
        System.out.print("Enter employee deductions: ");
        double deductions = scanner.nextDouble();

        String data = name + "," + id + "," + salary + "," + deductions;
        byte[] buffer = data.getBytes();

        InetAddress serverAddress = InetAddress.getByName("localhost");
        int serverPort = 12345;
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
        socket.send(packet);

        buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        String result = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Employee name: " + name);
        System.out.println("Employee ID: " + id);
        System.out.println("Employee salary: $" + salary);
        System.out.println("Employee deductions: $" + deductions);
        System.out.println("Net salary: $" + result);

        socket.close();
        scanner.close();
    }
}
