package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Enumeration;
import java.util.Random;

public class ChattingServer {
    private boolean isListening = true;
    private int port;

    public void startServer() {
        boolean shouldChangePort = false;
        do {
            this.port =  new Random().nextInt(65535 - 1024) + 1024;
            shouldChangePort = false;
            try (final ServerSocket serverSocket = new ServerSocket(this.port, 0, InetAddress.getLocalHost())) {
                while (this.isListening) {
                    System.out.println("Listening on: " + serverSocket.getInetAddress() + ":" + this.port);
                    new ChattingServerThread(serverSocket.accept()).start();
                }
            } catch (IOException exception) {
                shouldChangePort = true;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } while (shouldChangePort);
    }
}
