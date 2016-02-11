package ua.com.korniichuk.util;


import ua.com.korniichuk.client.ClientRepository;

public class CommonMessages {
    public static final String WELCOME = "Welcome in myChat 1.0" + System.getProperty("line.separator") +
            System.getProperty("line.separator") +
            "Enter, pls, your nick" + System.getProperty("line.separator");

    public static final String ONLINE = " online";
    public static final String OFFLINE = " left chat";
    public static final String STATUS = "Now " + ClientRepository.getInstance().size() + " users online";

}
