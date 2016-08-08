/*
 * WebappContextListener
 * Created on: Oct 26, 2012 5:27:14 PM
 * 
 * Copyright 2012 EnginSoft S.p.A.
 * All rights reserved
 */

package listeners;

import db.DBManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * Web application lifecycle listener.
 *
 * @author Marco Dalla Vecchia (m.dallavecchia AT enginsoft.it)
 */
public class WebappContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        String dburl = sce.getServletContext().getInitParameter("dburl");
        try {
            DBManager manager = new DBManager(dburl);
            sce.getServletContext().setAttribute("dbmanager", manager);
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // Il database Derby deve essere "spento" tentando di connettersi al database con shutdown=true        
        DBManager.shutdown();
    }
}
