package ua.com.korniichuk.start;

import ua.com.korniichuk.server.ChatServer;

/**
 * Created by DSK4 on 22.12.2015.
 */
public class Starter {
    public static void main(String[] args) {

        ChatServer chatServer = new ChatServer();
        chatServer.start();

    }
}
