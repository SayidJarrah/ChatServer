package ua.com.korniichuk.dao;


import java.util.HashMap;

public interface IDao {

    void save(HashMap<String,String> usersMap);

    HashMap<String,String> load();
}
