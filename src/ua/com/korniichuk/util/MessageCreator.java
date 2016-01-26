package ua.com.korniichuk.util;

import java.util.Date;


public class MessageCreator {
    public String createMessage(String nick, String message){
        StringBuilder sb = new StringBuilder();
              sb.append(nick)
                .append(" [")
                .append(new Date())
                .append("] ")
                .append(message);
        return String.valueOf(sb);
    }

}
