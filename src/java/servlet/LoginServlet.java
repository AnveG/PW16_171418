/*
 * LoginServlet
 * Created on: Oct 26, 2012 3:38:26 PM
 * 
 * Copyright 2012 EnginSoft S.p.A.
 * All rights reserved
 */

package servlet;

import db.DBManager;
import db.User;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * Changelog: <ul> <li>2012/10/26: initial version </ul>
 *
 * @author Marco Dalla Vecchia (m.dallavecchia AT enginsoft.it)
 * @version 1.0.20121026
 */
public class LoginServlet extends HttpServlet {
    private DBManager manager;
    
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("nickname");
        String password = req.getParameter("password");

        // controllo nel DB se esiste un utente con lo stesso username + password
        User user;
        try {
            user = manager.authenticate(username, password);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        // se non esiste, ridirigo verso pagina di login con messaggio di errore
        if (user == null) {
            // metto il messaggio di errore come attributo di Request, così nel JSP si vede il messaggio
            req.setAttribute("message", "Username/password non esistente !");
            RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
            rd.forward(req, resp);

        } else {

            // imposto l'utente connesso come attributo di sessione
            // per adesso e' solo un oggetto String con il nome dell'utente, ma posso metterci anche un oggetto User
            // con, ad esempio, il timestamp di login
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            // mando un redirect alla servlet che carica i prodotti
            resp.sendRedirect(req.getContextPath() + "/LoadProducts");
        }
    }

}
