package ua.com.korniichuk.start;

import ua.com.korniichuk.server.ChatServer;


public class Starter {
    public static void main(String[] args) {

        ChatServer chatServer = new ChatServer();
        chatServer.start();

    }
}
