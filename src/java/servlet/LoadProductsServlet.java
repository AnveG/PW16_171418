/*
 * LoadProductsServlet
 * Created on: Oct 26, 2012 3:52:29 PM
 * 
 */

package servlet;

import db.DBManager;
import db.Product;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 */
public class LoadProductsServlet extends HttpServlet {

    private DBManager manager;
    
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGetPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGetPost(req, resp);
    }

    private void doGetPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            
            List<Product> products = manager.getProducts();

            session.setAttribute("products", products);

            RequestDispatcher rd = req.getRequestDispatcher("/products.jsp");
            rd.forward(req, resp);

        } catch (SQLException ex) {
            Logger.getLogger(LoadProductsServlet.class.getName()).log(Level.SEVERE, ex.toString(), ex);
            
            throw new ServletException(ex);
        }
    }
    
    
}
