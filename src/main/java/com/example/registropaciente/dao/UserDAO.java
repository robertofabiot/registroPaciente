package com.example.registropaciente.dao;

import com.example.registropaciente.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    List<User> userList = new ArrayList<>();

    public UserDAO(){
        userList.add(new User("admin", "admin"));
    }

    public void addUser(User newUser){
        userList.add(newUser);
    }

    public List<User> getUsers(){
        return userList;
    }
}
