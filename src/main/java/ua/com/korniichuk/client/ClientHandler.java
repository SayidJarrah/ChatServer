package ua.com.korniichuk.client;

import org.apache.log4j.Logger;
import ua.com.korniichuk.util.CommonMessages;
import ua.com.korniichuk.util.MessagePublisher;
import ua.com.korniichuk.util.UsersCache;

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
        UsersCache usersCache = new UsersCache();
        try {
            String userAddress = (String) in.readObject();
            System.out.println(userAddress);  //read ip-address

            if (usersCache.getMap().containsKey(userAddress)) {
                nick = usersCache.getMap().get(userAddress);
                System.out.println(nick);
                ClientRepository.getInstance().register(this);
            } else {
               /* out.writeObject(CommonMessages.WELCOME);
                out.flush();*/
                Object obj = in.readObject();
                if (obj instanceof String) {
                    nick = (String) obj;
                }
                ClientRepository.getInstance().register(this);
                usersCache.appendUsersName(userAddress,nick);
                LOG.info("User log in");
                usersCache.appendUsersName(userAddress, nick);
            }
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

    public ObjectOutputStream getOut() {
        return out;
    }

    public String getNick() {
        return nick;
    }

}
