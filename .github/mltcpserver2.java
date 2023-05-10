import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class mltcpserver2 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println("Server started. Waiting for client...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected. Starting calculation...");

                Thread clientThread = new ClientThread(clientSocket);
                clientThread.start();
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientThread extends Thread {
        private Socket clientSocket;

        public ClientThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

                String massString = reader.readLine();
                double mass = Double.parseDouble(massString);

                double speed = calculateFallingSpeed(mass);

                outputStream.writeBytes(Double.toString(speed) + '\n');
                System.out.println("Speed sent to client: " + speed + " m/s");

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private double calculateFallingSpeed(double mass) {
            double G = 9.8; // Acceleration due to gravity on Earth
            return Math.sqrt((2 * G * mass) / 1);
        }
    }
}
