import java.io.*;
import java.net.*;

public class mltcpserver1 {
    private static final int PORT = 12345;
    private static final double GRAVITY = 9.81;
    private static final double EMPIRE_STATE_BUILDING_HEIGHT = 443;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                Thread thread = new Thread(new BulletSpeedCalculatorThread(clientSocket));
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class BulletSpeedCalculatorThread implements Runnable {
        private final Socket clientSocket;

        public BulletSpeedCalculatorThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                double height = Double.parseDouble(reader.readLine());

                double time = Math.sqrt(2 * EMPIRE_STATE_BUILDING_HEIGHT / GRAVITY);
                double speed = EMPIRE_STATE_BUILDING_HEIGHT / (Math.cos(Math.atan(1 / (GRAVITY * time))) * time);

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println(String.format("%.2f", speed));

                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
