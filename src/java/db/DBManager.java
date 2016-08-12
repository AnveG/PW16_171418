/*
 * DBManager
 * Created on: Oct 26, 2012 3:54:33 PM
 * 
 */

package db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DBManager implements Serializable {
    // transient == non viene serializzato
    private transient Connection con;
    
    public DBManager(String dburl) throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch(Exception e) {
            throw new RuntimeException(e.toString(), e);
        }
        
        Connection con = DriverManager.getConnection(dburl);
        this.con = con;

    }
    
    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }

    /**
     * Autentica un utente in base a un nome utente e a una password
     * 
     * @param username il nome utente
     * @param password la password
     * @return null se l'utente non è autenticato, un oggetto User se l'utente esiste ed è autenticato
     */
    public User authenticate(String username, String password) throws SQLException {
        // usare SEMPRE i PreparedStatement, anche per query banali. 
        // *** MAI E POI MAI COSTRUIRE LE QUERY CONCATENANDO STRINGHE !!!! ***
        PreparedStatement stm = con.prepareStatement("SELECT * FROM users WHERE nickname = ? AND password = ?");
        try {
            stm.setString(1, username);
            stm.setString(2, password);
            
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(username);
                    user.setFullname(rs.getString("name"));
                    
                    return user;
                } else {
                    return null;
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally 
            stm.close();
        }
    }
    
    /**
     * Ottiene la lista dei prodotti dal DB
     * 
     * @return
     * @throws SQLException 
     */
    public List<Product> getProducts() throws SQLException {
        List<Product> products = new ArrayList<Product>();
        
        PreparedStatement stm = con.prepareStatement("SELECT * FROM products");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                
                while(rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getFloat("price"));
                    
                    products.add(p);
                }
                
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        return products;
    }
    
    /**
     * Ottiene un singolo prodotto per id. Ritorna null se non lo trova
     * 
     * @return
     * @throws SQLException 
     */
    public Product getProduct(int id) throws SQLException {
        
        PreparedStatement stm = con.prepareStatement("SELECT * FROM products WHERE id = ?");
        try {
            stm.setInt(1, id);
            
            ResultSet rs = stm.executeQuery();
            try {
                
                if(rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getFloat("price"));
                    
                    return p;
                } else {
                    return null;
                }
                
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
    }
    
    public Product saveProduct(Product prod) throws SQLException {
        PreparedStatement stm = null;
        try {
            con.setAutoCommit(false); // disabilito il commit automatico
            
            if (prod.getId() == null) {
                stm = con.prepareStatement("INSERT INTO products (name,price) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            } else {
                stm = con.prepareStatement("UPDATE products SET name=?,price=? WHERE id = ?");
                stm.setInt(3, prod.getId());
            }
            
            stm.setString(1, prod.getName());
            stm.setFloat(2, prod.getPrice());
            
            stm.executeUpdate();

            // con il seguente comando, ottengo dal driver JDBC la chiave primaria generata in automatico
            // dal database, che altrimenti non potrei ottenere
            // faccio questo comando solo se avevo lanciato una INSERT e non quando faccio un UPDATE
            if (prod.getId() == null) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs != null) {
                    try {
                        if (rs.next()) {
                            prod.setId(rs.getInt(1));
                        }
                    } finally {
                        rs.close();
                    }
                }
            }
            
            con.commit();
            
            return prod;
        } catch(SQLException sqle) {
            // siccome ho disabilitato il commit automatico, devo fare rollback
            if (!con.getAutoCommit()) {
                con.rollback();
            }
            // dopo il rollback, rilancio l'eccezione in alto per farla gestire al layer di applicazione
            throw sqle;
        } finally {
            if (stm != null) {
                stm.close();
            }
            // riabilito il commit automatico
            if (!con.getAutoCommit()) {
                con.setAutoCommit(true);
            }
        }
    }
    
}
