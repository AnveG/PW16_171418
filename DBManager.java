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

    private transient Connection con;
    
    public DBManager(String dburl) throws SQLException {

        try {

            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader()); //Percorso del db

        } catch(Exception e) {
            throw new RuntimeException(e.toString(), e);
        }
        
        Connection con = DriverManager.getConnection(dburl);
        this.con = con;

    }


    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby://localhost:1527/intro_prog_db;user=stefaninog;password=temp");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }
    
    public User authenticate(String username, String password) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        try {
            stm.setString(1, username);
            stm.setString(2, password);
            
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(username);
                    user.setFullname(rs.getString("fullname"));
                    
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
    
    public int register(String email, String password) throws SQLException {
        
        //Guardo se l'email è già memorizzata
        boolean exists;
        PreparedStatement stm1 = con.prepareStatement("SELECT * FROM users WHERE email = ?");
        try {
            stm1.setString(1, email);            
            ResultSet rs = stm1.executeQuery();
            try {
                if (rs.next()) {
                    exists = true;
                } else {
                    exists = false;
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally 
            stm1.close();
        }
        
        if (exists == false){  //creo il nuovo utente inserendo i dati nel db        
            //Eventualmente da sistemare con i campi corretti!
            PreparedStatement stm2 = con.prepareStatement("INSERT INTO User(email, password) VALUES(?,?)");
            try {
                stm2.setString(1, email);
                stm2.setString(2, password);
                //I campi "nome" e "cognome" vengono settati a null                

                int rs = stm2.executeUpdate();
                
                if(rs==1){ //inserito correttamente
                    return 1;
                } else {  // non inserito
                    return 0;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
            } finally {
                stm2.close();
            }
              
        } 
        return -1;
    }
    
}