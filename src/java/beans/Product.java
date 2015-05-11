/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sun.util.calendar.BaseCalendar;

/**
 *
 * @author amine
 */
@ManagedBean
@RequestScoped
public class Product {
    private final static String URL = "jdbc:mysql://localhost:3306/test";
    private final static String USER = "root";
    private final static String PWD = "";
    
    private Connection cnx = null;
    private Statement stat = null;
    
    
    private Integer id;
    private String label;
    private String quantity;
    private String basicPrice;
    private BaseCalendar.Date date;
    private String status;
    private String description;
    private User buyer;
    private User seller;
    private Local country;
    private Category categorie;
    private String image;

    
    public Product() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection(URL, USER, PWD);
            stat = cnx.createStatement();
            }catch(Exception e)
        {
            e.printStackTrace();
        }   
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getBasicPrice() {
        return basicPrice;
    }

    public BaseCalendar.Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public User getBuyer() {
        return buyer;
    }

    public User getSeller() {
        return seller;
    }

    public Local getCountry() {
        return country;
    }

    public Category getCategorie() {
        return categorie;
    }

    public String getImage() {
        return image;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setBasicPrice(String basicPrice) {
        this.basicPrice = basicPrice;
    }

    public void setDate(BaseCalendar.Date date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setCountry(Local country) {
        this.country = country;
    }

    public void setCategorie(Category categorie) {
        this.categorie = categorie;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
  
 //-------------------------------DAO----------------------------------------
    
 
    
    
    
    
    
    
}
