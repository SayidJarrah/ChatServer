package ua.com.korniichuk.server;

import ua.com.korniichuk.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by DSK4 on 22.12.2015.
 */
public class ChatServer {
    private final static int port = 5432;

    public void start() {
        while (true) {

            try (ServerSocket serverSocket = new ServerSocket(port)) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler();
                clientHandler.configure(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
