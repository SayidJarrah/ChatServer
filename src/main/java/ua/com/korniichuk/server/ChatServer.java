package ua.com.korniichuk.server;

import org.apache.log4j.Logger;
import ua.com.korniichuk.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private final static int PORT = 5432;
    public static final Logger LOG = Logger.getLogger(ChatServer.class);

    public void start() {
        LOG.info("Server running...");
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                Socket socket = serverSocket.accept();
                LOG.info("New user connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread clientHandlerThread = new Thread(clientHandler);
                clientHandlerThread.start();


            } catch (IOException e) {
                LOG.error(e);
                throw new RuntimeException(e);
            }

        }
    }
}
