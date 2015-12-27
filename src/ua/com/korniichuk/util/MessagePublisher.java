package ua.com.korniichuk.util;

import ua.com.korniichuk.client.ClientHandler;
import ua.com.korniichuk.client.ClientRepository;

import java.io.IOException;
import java.util.List;

/**
 * Created by DSK4 on 22.12.2015.
 */
public class MessagePublisher {
    public void publish(String message) throws IOException {

        List<ClientHandler> listeners = ClientRepository.getInstance().getHandlers();

        for (ClientHandler clientHandler : listeners){
            clientHandler.getOut().write(message);
        }



    }
}
