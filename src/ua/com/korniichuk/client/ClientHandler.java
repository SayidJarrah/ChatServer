package ua.com.korniichuk.client;

import ua.com.korniichuk.util.MessagePublisher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String nick;
    private Socket socket;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        MessagePublisher messagePublisher = new MessagePublisher();
        try {
            out.writeObject("Welcome in myChat 1.0" + System.getProperty("line.separator") +
                    "Now " + ClientRepository.getInstance().size() + " users online" +
                    System.getProperty("line.separator") +
                    "Enter, pls, your nick:" + System.getProperty("line.separator"));
            out.flush();
            Object obj = in.readObject();
            if (obj instanceof String) {
                setNick((String) obj);
            }
               //need for removing metadata from inputStream
            System.out.println(getNick() + " online.");
            ClientRepository.getInstance().register(this);
            messagePublisher.publish(getNick() + " online.");
            messagePublisher.publishServiceMessage();

            while (true) {
                Message newMessage;
                try {
                    newMessage = (Message) in.readObject();
                    messagePublisher.publishServiceMessage(); //TODO: move on the beginning
                    messagePublisher.publish(newMessage);
                } catch (IOException e) {
                    ClientRepository.getInstance().unregister(this);
                    messagePublisher.publish(this.getNick() + " left chat");
                    messagePublisher.publishServiceMessage();
                    break;
                }
             //   System.out.println(newMessage);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public String getNick() {
        return nick;
    }

    @Override
    public String toString() {
        return "ClientHandler{" +
                "in=" + in +
                ", out=" + out +
                ", nick='" + nick + '\'' +
                '}';
    }
}
