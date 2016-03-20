package ua.com.korniichuk.chatserver.start;

import ua.com.korniichuk.chatserver.server.ChatServer;


public class Starter {
    public static void main(String[] args) {

        ChatServer chatServer = new ChatServer();
        chatServer.start();

    }
}
