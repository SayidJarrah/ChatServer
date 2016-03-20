package ua.com.korniichuk.chatserver.client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository {

    List<ClientHandler> handlers = new ArrayList<>();
    private static ClientRepository INSTANCE;

    private ClientRepository() {
    }

    public void register(ClientHandler clientHandler) {
        handlers.add(clientHandler);
    }

    public void unregister(ClientHandler clientHandler) {
        handlers.remove(clientHandler);
    }


    public int size() {
        return handlers.size();
    }

    public static ClientRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientRepository();
        }
        return INSTANCE;
    }

    public List<ClientHandler> getHandlers() {
        return handlers;
    }
}
