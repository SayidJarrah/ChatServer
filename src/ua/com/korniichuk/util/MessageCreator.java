package ua.com.korniichuk.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MessageCreator {
    public String createMessage(String nick, String message){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
              sb.append(nick)
                .append(" [")
                .append(format.format(new Date()))
                .append("] ")
                .append(message);
        return String.valueOf(sb);
    }

}
