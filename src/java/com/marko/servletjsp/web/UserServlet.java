/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marko.servletjsp.web;

import com.marko.servletjsp.dao.UserDAOImpl;
import com.marko.servletjsp.model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marko
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {

private UserDAOImpl userDAO;

@Override
    public void init() {
        userDAO = new UserDAOImpl();
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String action = request.getServletPath();
        try{
        switch(action){
           case "/new": showNewForm(request, response);
             break;
          case "/insert": insertUser(request, response);
            break;
           case "/delete": deleteUserById(request, response);
            break; 
           case "/edit": editUserForm(request, response);
            break; 
           case "/update": updateUser(request, response);
            break; 
           default: listUsers(request, response);
            break;
        }
        } catch(SQLException ex){ex.printStackTrace();}
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
      List<User> listUser = userDAO.getAllUsers();
      request.setAttribute("listUser", listUser);
      RequestDispatcher rd = request.getRequestDispatcher("user-list.jsp");
      rd.forward(request, response);
    }
    
    
   
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher rd = request.getRequestDispatcher("user-form.jsp");
        rd.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException{
    
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        userDAO.createUser(new User(name, email, country));
        response.sendRedirect("list");
    
    }
    
    private void deleteUserById(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException{
        
       int id = Integer.parseInt(request.getParameter("id"));
       userDAO.deleteUserById(id);
       response.sendRedirect("list");
    }
 
    private void editUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.getUserById(id);
        RequestDispatcher rd = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        rd.forward(request, response);
    }
    
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException{
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User user = new User(id, name, email, country);
        System.out.println(user.toString());
        userDAO.updateUser(user);
        response.sendRedirect("list");
    }

}
