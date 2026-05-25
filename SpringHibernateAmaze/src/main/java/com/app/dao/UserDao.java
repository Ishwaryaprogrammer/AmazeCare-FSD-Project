package com.app.dao;

import com.app.model.User;

public interface UserDao {
    User login(String mail, String pass) ;
}
