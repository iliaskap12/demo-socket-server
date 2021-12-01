package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChattingClient {
    private final String hostName;
    private final int port;

    public ChattingClient() {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Enter host name: ");
        this.hostName = scanner.nextLine();
        System.out.print("Enter port number: ");
        this.port = scanner.nextInt();
    }

    public void run() {
        try (final Socket serverSocket = new Socket(this.hostName, this.port);
             final PrintWriter output = new PrintWriter(serverSocket.getOutputStream(), true);
             final BufferedReader input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
             final BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                output.println(userInput);
                System.out.println(input.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + this.hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    this.hostName);
            System.exit(1);
        }
    }
}
