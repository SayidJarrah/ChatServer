package ua.com.korniichuk.client;

import ua.com.korniichuk.util.MessageCreator;
import ua.com.korniichuk.util.MessagePublisher;

import java.io.*;
import java.net.Socket;

/**
 * Created by DSK4 on 22.12.2015.
 */
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
        try {
            out.write("Hello! Enter your nick pls :");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            setNick(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessagePublisher messagePublisher = new MessagePublisher();
        try {
            messagePublisher.publish(getNick() + " online.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(getNick() + " online.");
        ClientRepository.getInstance().register(this);
       // System.out.println(ClientRepository.getInstance().size());

        MessageCreator messageCreator = new MessageCreator();

      /*  while (true){
            try {
                out.write(messageCreator.createMessage(getNick(),in.readLine()));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

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
