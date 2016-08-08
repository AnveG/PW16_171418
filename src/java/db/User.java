/*
 * User
 * Created on: Oct 26, 2012 4:07:02 PM
 * 
 */

package db;

import java.io.Serializable;

public class User implements Serializable {

    private String username;

    private String fullname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
