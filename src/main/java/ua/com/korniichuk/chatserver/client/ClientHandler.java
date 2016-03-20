package ua.com.korniichuk.chatserver.client;

import org.apache.log4j.Logger;
import ua.com.korniichuk.chatserver.util.CommonMessages;
import ua.com.korniichuk.chatserver.util.UsersCache;
import ua.com.korniichuk.chatserver.util.MessagePublisher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String nick;
    public static final Logger LOG = Logger.getLogger(ClientHandler.class);

    public ClientHandler(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        LOG.info("Thread for new user was created");
    }

    @Override
    public void run() {
        MessagePublisher messagePublisher = new MessagePublisher();
        authorization();
        try {
            messagePublisher.publish(nick + CommonMessages.ONLINE);
            messagePublisher.publishServiceMessage();

            while (true) {
                try {
                    Object objectInput = in.readObject();
                    if (objectInput instanceof Message) {
                        messagePublisher.publishServiceMessage();
                        messagePublisher.publish((Message) objectInput);
                    }
                } catch (IOException e) {
                    ClientRepository.getInstance().unregister(this);
                    LOG.info("User log out");
                    messagePublisher.publish(nick + CommonMessages.OFFLINE);
                    messagePublisher.publishServiceMessage();
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("Something wrong :" + e);
            throw new RuntimeException(e);
        }
    }

    public void authorization() {
        try {

            UsersCache usersCache = new UsersCache();
            String userAddress = (String) in.readObject();

            if (usersCache.getMap().containsKey(userAddress)) {
                nick = usersCache.getMap().get(userAddress);
                ClientRepository.getInstance().register(this);
                Account account = new Account();
                account.setExistingNickName(nick);
                out.writeObject(account);
            } else {
                out.writeObject(CommonMessages.WELCOME);
                out.flush();
                Object obj = in.readObject();
                if (obj instanceof String) {
                    nick = (String) obj;
                }
                ClientRepository.getInstance().register(this);
                usersCache.appendUsersName(userAddress, nick);
                LOG.info("User log in");
                usersCache.appendUsersName(userAddress, nick);
            }
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("Something wrong :" + e);
            throw new RuntimeException(e);
        }
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public String getNick() {
        return nick;
    }

}
