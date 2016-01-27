package ua.com.korniichuk.client;

import ua.com.korniichuk.util.MessageCreator;
import ua.com.korniichuk.util.MessagePublisher;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private BufferedReader in;
    private BufferedWriter out;
    private String nick;

    public void configure(Socket socket) throws IOException {

        setIn(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        setOut(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

    }


    @Override
    public void run() {
        ClientRepository.getInstance().register(this);
        MessagePublisher messagePublisher = new MessagePublisher();
        try {
            out.write(ClientRepository.getInstance().size() - 1 + " users online" + System.getProperty("line.separator"));
            out.write("Hello! Enter, pls, your nick:");
            out.flush();
            setNick(in.readLine());
            messagePublisher.publish(getNick() + " online.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(getNick() + " online.");
        MessageCreator messageCreator = new MessageCreator();
        while (true) {
            try {
                String input = in.readLine();
                if (input == null) {
                    messagePublisher.publish(getNick() + " left chat room");
                    ClientRepository.getInstance().unregister(this);
                    messagePublisher.publish(ClientRepository.getInstance().size()+ " users online" + System.getProperty("line.separator"));
                    break;
                }
                String newMessage = messageCreator.createMessage(getNick(), input);
                System.out.println(newMessage);
                messagePublisher.publish(newMessage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public void setOut(BufferedWriter out) {
        this.out = out;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
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
