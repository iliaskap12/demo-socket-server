package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChattingServerThread extends Thread {
    private final Socket clientSocket;

    public ChattingServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (final PrintWriter output = new PrintWriter(this.clientSocket.getOutputStream(), true);
             final BufferedReader input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
             final Scanner scanner = new Scanner(System.in)
        ) {
            String outputMessage = scanner.nextLine();
            output.println(outputMessage);
            String inputMessage;
            while ((inputMessage = input.readLine()) != null) {
                System.out.println(inputMessage);
                outputMessage = scanner.nextLine();
                output.println(outputMessage);
                if (outputMessage.equals("Bye")) {
                    break;
                }
            }

            this.clientSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
