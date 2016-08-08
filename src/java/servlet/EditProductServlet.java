/*
 * EditProduct
 * Created on: Nov 22, 2012 3:51:08 PM
 * 
 * Copyright 2012 EnginSoft S.p.A.
 * All rights reserved
 */

package servlet;

import db.DBManager;
import db.Product;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditProductServlet extends HttpServlet {

    static Logger log = Logger.getLogger("EditProduct");
    
    private DBManager manager;
    
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager)super.getServletContext().getAttribute("dbmanager");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // interpreto le GET per mostrare la pagina di creazione / modifica di un prodotto
        // se alla servlet passo un ID di prodotto, carico il prodotto da modificare dal DB.
        // se alla servlet non passo niente, non carico nulla e la pagina consente di creare un prodotto nuovo
        
        String prodid = request.getParameter("prodid");
        Product product = null;
        
        if (prodid != null && !prodid.trim().isEmpty()) {
            // cerco il prodotto per ID nel DB
            try {
                Integer id = Integer.valueOf(prodid);
                
                product = manager.getProduct(id);
                request.setAttribute("product", product);
                
                // product potrebbe essere null, se mi passano un id che non esiste
                if (product == null) {
                    response.sendError(500, "No such product id = " + id);
                    return;
                }
                
            } catch (NumberFormatException nfe) {
                // mi hanno passato un ID non numerico, lancio eccezione in alto e blocco tutto
                response.sendError(500, "ID format error: " + nfe.getMessage());
                return;
            } catch (SQLException sqle) {
                response.sendError(500, "SQLException: " + sqle.getMessage());
                return;
            }
        } 
        
        RequestDispatcher rd = request.getRequestDispatcher("/editproduct.jsp");
        rd.forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // interpreto la POST per salvare il prodotto nel DB
        
        // ottengo tutti i parametri presenti nel form
        String prodid = request.getParameter("prodid");
        String prodname = request.getParameter("prodname");
        String prodprice = request.getParameter("prodprice");
        
        
        Product product = new Product();
        
        if (prodid != null && !prodid.trim().isEmpty()) {
            product.setId(Integer.valueOf(prodid));
        }
        product.setName(prodname);
        
        try {
            Float price = new Float(prodprice);
            product.setPrice(price);
        } catch (NumberFormatException nfe) {
            // il formato dati del prezzo è errato, rimostro la pagina
            // reimposto il prodotto come prima
            request.setAttribute("product", product);
            request.setAttribute("priceerror", "Valore del prezzo non corretto");
            
            RequestDispatcher rd = request.getRequestDispatcher("/editproduct.jsp");
            rd.forward(request, response);
            return;
        }
        
        try {
            manager.saveProduct(product);
        } catch (SQLException ex) {
            response.sendError(500, "Errore nel salvataggio del prodotto: " + ex.getMessage());
            return;
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/LoadProducts");
        rd.forward(request, response);
    }

}
