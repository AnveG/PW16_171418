/*
 * LoggedInFilter
 * Created on: Nov 22, 2012 5:06:55 PM
 * 
 * Copyright 2012 EnginSoft S.p.A.
 * All rights reserved
 */

package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * Changelog:
 * <ul>
 * <li>2012/11/22: initial version
 * </ul>
 * @author Marco Dalla Vecchia (m.dallavecchia AT enginsoft.it)
 * @version 1.0.20121122
 */
public class LoggedInFilter implements Filter {

    public void init(FilterConfig fc) throws ServletException {
        
    }

    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest)sr;
        final HttpServletResponse resp = (HttpServletResponse)sr1;

        // controllo se la sessione esiste e contiene un utente loggato
        // se non esiste o non conteiene un utente, ridirigo alla pagina iniziale di login
        HttpSession session = (req).getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath());
        } else {
            fc.doFilter(sr, sr1);
        }
    }

    public void destroy() {
        
    }

}
