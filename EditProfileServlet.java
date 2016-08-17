/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import db.DBManager;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thomas
 */
@WebServlet(name = "EditProfileServlet", urlPatterns = {"/EditProfileServlet"})
public class EditProfileServlet extends HttpServlet {
    
private DBManager manager;    
            
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
    }
        
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user;
        String res = null;
        HttpSession session = req.getSession(true);
        user = (User) session.getAttribute("user");
        
        String email = req.getParameter("email");  
        String password = req.getParameter("password");
        String nickname = req.getParameter("nickname");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        
        try {
            res = manager.modifica(user, name, surname, nickname, password, email);
        } catch (SQLException ex) {
            Logger.getLogger(EditProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        req.setAttribute("message", res);      

        RequestDispatcher rd = req.getRequestDispatcher("/"); //DA AGGIUNGERE IL LINK
        rd.forward(req, resp);
    }

}
    
