package ua.com.korniichuk.util;

import ua.com.korniichuk.client.ClientHandler;
import ua.com.korniichuk.client.ClientRepository;
import ua.com.korniichuk.client.Message;

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
        //******
        OnlineUsers onlineUsers = new OnlineUsers();
        ArrayList usersList = new ArrayList();
        List<ClientHandler> clientHandlers = ClientRepository.getInstance().getHandlers();

        for (ClientHandler user : clientHandlers) {
            usersList.add(user.getNick());
        }
        onlineUsers.setUsers(usersList);

        List<ClientHandler> listeners = ClientRepository.getInstance().getHandlers();
        for (ClientHandler clientHandler : listeners) {
            clientHandler.getOut().writeObject(onlineUsers);
            clientHandler.getOut().flush();
        }
        //*****
    }

}
