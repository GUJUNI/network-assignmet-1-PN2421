import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class mltcpclient2 {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 1234);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.print("Enter the mass of the meteor (in kg): ");
            String massString = reader.readLine();
            outputStream.writeBytes(massString + '\n');

            String speedString = serverReader.readLine();
            double speed = Double.parseDouble(speedString);

            System.out.println("Speed of the free-falling meteor: " + speed + " m/s");

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
