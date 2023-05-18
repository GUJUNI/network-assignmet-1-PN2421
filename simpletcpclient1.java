import java.io.*;
import java.net.*;
import java.util.Scanner;

public class simpletcpclient1 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter principal amount: ");
        double principal = scanner.nextDouble();
        System.out.print("Enter interest rate (in percentage): ");
        double rate = scanner.nextDouble();
        System.out.print("Enter time period (in years): ");
        double time = scanner.nextDouble();

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(principal + "," + rate + "," + time);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String result = in.readLine();

        System.out.println("Principal amount: $" + principal);
        System.out.println("Interest rate: " + rate + "%");
        System.out.println("Time period: " + time + " years");
        System.out.println("Compound interest: $" + result);

        socket.close();
        scanner.close();
    }
}
