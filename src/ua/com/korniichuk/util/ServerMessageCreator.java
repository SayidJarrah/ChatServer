package ua.com.korniichuk.util;

import ua.com.korniichuk.client.ClientRepository;

import java.io.IOException;


public class ServerMessageCreator implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100000);
                int currentSize = ClientRepository.getInstance().size();
                new MessagePublisher().publish(System.getProperty("line.separator") + "***ServerMessage*** Online " + currentSize + " users");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
