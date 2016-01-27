package ua.com.korniichuk.server;

import ua.com.korniichuk.client.ClientHandler;
import ua.com.korniichuk.util.ServerMessageCreator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer {
    private final static int PORT = 5432;

    public void start() {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler();
                clientHandler.configure(socket);
                Thread serverMessageThread = new Thread(new ServerMessageCreator());
                serverMessageThread.isDaemon();
                serverMessageThread.start();
                Thread clientHandlerThread = new Thread(clientHandler);
                clientHandlerThread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
