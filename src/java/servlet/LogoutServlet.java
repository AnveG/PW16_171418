/*
 * LogoutServlet
 * Created on: Oct 26, 2012 5:49:07 PM
 * 
 * Copyright 2012 EnginSoft S.p.A.
 * All rights reserved
 */

package servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * Changelog:
 * <ul>
 * <li>2012/10/26: initial version
 * </ul>
 * @author Marco Dalla Vecchia (m.dallavecchia AT enginsoft.it)
 * @version 1.0.20121026
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
            session.invalidate();
        }
        req.setAttribute("message", "Logout effettuato con successo");
        
        RequestDispatcher rd = req.getRequestDispatcher("/");
        rd.forward(req, resp);
    }
    
}
