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

    /*public void currentUsersStatus(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int currentSize = ClientRepository.getInstance().size();
                try {
                    new MessagePublisher().publish("Online "+ currentSize + "users");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },0*20*1000);
    }*/

    @Override
    public void run() {
        ClientRepository.getInstance().register(this);
        MessagePublisher messagePublisher = new MessagePublisher();
        try {
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
                String newMessage = messageCreator.createMessage(getNick(), in.readLine());
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
