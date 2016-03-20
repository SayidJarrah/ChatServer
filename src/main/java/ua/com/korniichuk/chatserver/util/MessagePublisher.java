package ua.com.korniichuk.chatserver.util;

import ua.com.korniichuk.chatserver.client.ClientHandler;
import ua.com.korniichuk.chatserver.client.ClientRepository;
import ua.com.korniichuk.chatserver.client.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MessagePublisher {
    public void publish(Message message) throws IOException {

        List<ClientHandler> listeners = ClientRepository.getInstance().getHandlers();
        for (ClientHandler clientHandler : listeners) {
            clientHandler.getOut().writeObject(message);
            clientHandler.getOut().flush();
        }

    }

    public void publish(String message) throws IOException {

        List<ClientHandler> listeners = ClientRepository.getInstance().getHandlers();
        for (ClientHandler clientHandler : listeners) {
            clientHandler.getOut().writeObject(message);
            clientHandler.getOut().flush();
        }

    }

    public void publishServiceMessage() throws IOException {
        ArrayList<String> usersList = new ArrayList<>();
        List<ClientHandler> clientHandlers = ClientRepository.getInstance().getHandlers();
        for (ClientHandler user : clientHandlers) {
            usersList.add(user.getNick());
        }
        List<ClientHandler> listeners = ClientRepository.getInstance().getHandlers();
        for (ClientHandler clientHandler : listeners) {
            clientHandler.getOut().writeObject(usersList);
            clientHandler.getOut().flush();
        }
    }

}
