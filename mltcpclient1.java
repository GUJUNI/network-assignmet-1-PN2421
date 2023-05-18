import java.io.*;
import java.net.*;

public class mltcpclient1 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

            double height = 381; // height of the Empire State Building minus height of observation deck

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(height);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String result = reader.readLine();
            System.out.println("Bullet speed: " + result + " m/s");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
