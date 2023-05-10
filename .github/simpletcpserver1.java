import java.io.*;
import java.net.*;

public class simpletcpserver1 {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12345);

        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data = in.readLine();

            String[] fields = data.split(",");
            double principal = Double.parseDouble(fields[0]);
            double rate = Double.parseDouble(fields[1]) / 100;
            double time = Double.parseDouble(fields[2]);
            double compoundInterest = principal * Math.pow(1 + rate, time) - principal;

            String result = String.format("%.2f", compoundInterest);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(result);

            socket.close();
            serverSocket.close();
        }
    }
}
