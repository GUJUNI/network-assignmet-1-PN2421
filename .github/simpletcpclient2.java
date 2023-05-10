import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class simpletcpclient2 {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 1234);

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String password = reader.readLine();

            System.out.println("Generated Password: " + password);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
