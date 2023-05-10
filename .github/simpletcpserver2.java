import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class simpletcpserver2 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println("Server started. Waiting for client...");

            while (true) {
                Socket connectionSocket = serverSocket.accept();

                System.out.println("Client connected. Generating password...");

                String password = generateRandomPassword();

                DataOutputStream outputStream = new DataOutputStream(connectionSocket.getOutputStream());
                outputStream.writeBytes(password + '\n');

                System.out.println("Password sent to the client: " + password);

                connectionSocket.close();
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=";
        int length = 10;
        Random random = new Random();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
