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
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})  //possibile cambiarli
public class RegisterServlet extends HttpServlet {
private DBManager manager;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
        
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
    }
       
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // controllo nel DB se esiste un utente con lo stesso username + password

        int res;
        try {
            res = manager.register(email, password);

        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        
        if (res == 1) { //Utente registrato
            // metto il messaggio di errore come attributo di Request, così nel JSP si vede il messaggio
            //Nella pagina jsp bisogna gestire la visualizzazione del messaggio
            //Siccome la registrazione avviene tramite conferma via email, dobbiamo anche 
            req.setAttribute("message", "Utente registrato correttamente!");

            RequestDispatcher rd = req.getRequestDispatcher("/login.jsp"); //In caso cambiare l'url
            rd.forward(req, resp);

        } else if (res == 0){ //Errore inserimento utente
            //vedi sopra
            req.setAttribute("message", "Errore nella registrazione dell'utente");

            RequestDispatcher rd = req.getRequestDispatcher("/login.jsp"); //In caso cambiare l'url
            //l'url dovrebbe essere uguale, perchè in teoria il login e la registrazione sono 2 form
            //della stessa pagina
            rd.forward(req, resp);
        } else if (res == -1){ //Email già esistente!
            //codice
            req.setAttribute("message", "Email già esistente!");

            RequestDispatcher rd = req.getRequestDispatcher("/login.jsp"); //In caso cambiare l'url (vedi sopra)
            rd.forward(req, resp);
        }  
    }
}
