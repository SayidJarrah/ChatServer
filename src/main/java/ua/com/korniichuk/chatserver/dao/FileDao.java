package ua.com.korniichuk.chatserver.dao;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class FileDao implements IDao {
    private static final String USERS_HISTORY_PATH = "C:\\Users\\Dima\\IdeaProjects\\ChatServer\\UsersHistory.txt"; //TODO: remove hardcode
    private static final int KEY = 0;
    private static final int VALUE = 1;

    @Override
    public void save(HashMap<String, String> usersMap) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(USERS_HISTORY_PATH)))) {
            for (Map.Entry<String, String> entry : usersMap.entrySet()) {
                writer.write(entry.getKey() + "-" + entry.getValue()+System.getProperty("line.separator"));
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public HashMap<String, String> load() {
        HashMap<String, String> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(USERS_HISTORY_PATH)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("-");
                map.put(values[KEY], values[VALUE]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}

