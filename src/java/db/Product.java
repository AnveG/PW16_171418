/*
 * Product
 * Created on: Oct 26, 2012 4:10:16 PM
 * 
 */

package db;

import java.io.Serializable;

public class Product implements Serializable {

    private Integer id;
    
    private String name;

    private Float price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}
