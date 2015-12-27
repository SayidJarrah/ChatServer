package ua.com.korniichuk.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DSK4 on 22.12.2015.
 */
public class ClientRepository {

    List<ClientHandler> handlers = new ArrayList<ClientHandler>();
    private static ClientRepository INSTANCE;

    private ClientRepository() {
    }

    public void register(ClientHandler clientHandler) {
        handlers.add(clientHandler);

    }

    public void unregister(ClientHandler clientHandler) {
        handlers.remove(clientHandler);

    }

    public ClientHandler get(String nick) {
        ClientHandler result = null;
        for (ClientHandler clientHandler : handlers) {
            if (nick.equals(clientHandler.getNick())) {
                result = clientHandler;
            }

        }
        return result;
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
