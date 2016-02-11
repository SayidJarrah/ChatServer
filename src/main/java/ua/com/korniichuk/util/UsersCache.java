package ua.com.korniichuk.util;

import ua.com.korniichuk.dao.FileDao;
import ua.com.korniichuk.dao.IDao;

import java.util.HashMap;
import java.util.Map;

public class UsersCache {
    private HashMap<String,String> map = new HashMap<>();
    private IDao dao = new FileDao();

    public void appendUsersName(String ipAddress, String nickName){
        map = dao.load();
        map.put(ipAddress,nickName);
        dao.save(map);
    }

    public Map<String, String> getMap() {
        return dao.load();
    }
}
