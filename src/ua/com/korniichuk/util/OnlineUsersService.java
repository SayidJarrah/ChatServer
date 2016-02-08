package ua.com.korniichuk.util;

import ua.com.korniichuk.client.ClientHandler;
import ua.com.korniichuk.client.ClientRepository;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class OnlineUsersService implements Runnable {
    Socket socket;

    public OnlineUsersService(Socket socket) {
        this.socket = socket;
    }

    public void sendActiveUsers() {
        OnlineUsers onlineUsers = new OnlineUsers();
        ArrayList usersList = new ArrayList();
        List<ClientHandler> clientHandlers = ClientRepository.getInstance().getHandlers();

        for (ClientHandler user : clientHandlers) {
            usersList.add(user.getNick());
        }
        onlineUsers.setUsers(usersList);

        try (OutputStream serviceOutputStream = socket.getOutputStream()) {
            ObjectOutputStream serviceOutStream = new ObjectOutputStream(serviceOutputStream);
            serviceOutStream.writeObject(onlineUsers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void run() {
        sendActiveUsers();
    }
}
