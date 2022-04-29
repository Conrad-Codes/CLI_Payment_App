package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User findByUsername(String username);

    int findIdByUsername(String username);

    int findAccountIdByUserId(int accountId);

    String getUsernameById(int accountId);

    boolean create(String username, String password);
}
