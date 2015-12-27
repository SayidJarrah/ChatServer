package ua.com.korniichuk.util;

import java.util.Date;

/**
 * Created by DSK4 on 22.12.2015.
 */
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
