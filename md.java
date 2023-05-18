// server code:

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;

public class MessageDigestServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println("Server started. Waiting for client...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected. Starting message digest computation...");

                Thread clientThread = new ClientThread(clientSocket);
                clientThread.start();
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
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

                String filePath = "path/to/your/file"; // Specify the path to your file

                String messageDigest = computeMessageDigest(filePath);

                outputStream.writeBytes(messageDigest + '\n');
                System.out.println("Message digest sent to client: " + messageDigest);

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String computeMessageDigest(String filePath) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    md.update(buffer, 0, bytesRead);
                }
                fis.close();

                byte[] digest = md.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

//client code:

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageDigestClient {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 1234);

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

            System.out.print("Enter the path to the file: ");
            String filePath = reader.readLine();

            outputStream.writeBytes(filePath + '\n');

            String messageDigest = reader.readLine();
            System.out.println("Message Digest: " + messageDigest);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
