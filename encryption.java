//java server and client program that performs encryption/decryption


// Server code:

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class EncryptionServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println("Server started. Waiting for client...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected. Starting encryption/decryption...");

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
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

                String message = reader.readLine();
                String mode = reader.readLine();
                int shift = Integer.parseInt(reader.readLine());

                String result = "";
                if (mode.equals("encrypt")) {
                    result = encrypt(message, shift);
                } else if (mode.equals("decrypt")) {
                    result = decrypt(message, shift);
                }

                outputStream.writeBytes(result + '\n');
                System.out.println("Result sent to client: " + result);

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String encrypt(String message, int shift) {
            StringBuilder result = new StringBuilder();
            for (char c : message.toCharArray()) {
                if (Character.isLetter(c)) {
                    char shifted = (char) (c + shift);
                    if ((Character.isLowerCase(c) && shifted > 'z')
                            || (Character.isUpperCase(c) && shifted > 'Z')) {
                        shifted = (char) (shifted - 26);
                    }
                    result.append(shifted);
                } else {
                    result.append(c);
                }
            }
            return result.toString();
        }

        private String decrypt(String message, int shift) {
            return encrypt(message, -shift);
        }
    }
}

//client code:

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class EncryptionClient {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 1234);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.print("Enter the message to encrypt/decrypt: ");
            String message = reader.readLine();
            System.out.print("Enter the mode (encrypt/decrypt): ");
            String mode = reader.readLine();
            System.out.print("Enter the shift value: ");
            int shift = Integer.parseInt(reader.readLine());

            outputStream.writeBytes(message + '\n');
            outputStream.writeBytes(mode + '\n');
            outputStream.writeBytes(Integer.toString(shift) + '\n');

            String result = serverReader.readLine();
            System.out.println("Result: " + result);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
