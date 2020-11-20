/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marko.servletjsp.dao;

import com.marko.servletjsp.model.User;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Marko
 */
public interface UserDAO {
    
    Connection getConnection();
    List<User>getAllUsers();
    boolean deleteUserById(int id);
    User getUserById(int id);
    void createUser(User user);
    boolean updateUser (User user);
}
