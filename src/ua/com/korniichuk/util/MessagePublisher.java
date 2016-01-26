package ua.com.korniichuk.util;

import ua.com.korniichuk.client.ClientHandler;
import ua.com.korniichuk.client.ClientRepository;

import java.io.*;
import java.util.List;


public class MessagePublisher {
    public void publish(String message) throws IOException {

        List<ClientHandler> listeners = ClientRepository.getInstance().getHandlers();
        for (ClientHandler clientHandler : listeners) {
            clientHandler.getOut().write(message + System.getProperty("line.separator"));
            clientHandler.getOut().flush();

        }
    }
}
