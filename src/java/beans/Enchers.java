/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author amine
 */
@ManagedBean
@RequestScoped
public class Enchers {

    private final static String URL = "jdbc:mysql://localhost:3306/enchers";
    private final static String USER = "root";
    private final static String PWD = "";
    
    private static java.sql.Connection cnx = null;
    private PreparedStatement stat = null;
    
    private Long id;
    private Date date;
    private Float price;
    private User user;
    private Product product;
    
    public Enchers() {
        user = new User();
        product = new Product();
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection(URL, USER, PWD);
            }catch(Exception e)
        {
            e.printStackTrace();
        }   
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Float getPrice() {
        return price;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
    public void addEnchers(){ //NOT YET TESTED
        String req = "INSERT INTO enchers VALUES( null , '?' , ? , ? , ?);";
        try {
            stat = cnx.prepareStatement(req);
            stat.setDate(1, date);
            stat.setFloat(2 ,price);
            stat.setLong(3 , user.getId());
            stat.setLong(4 , product.getId());
            
            stat.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Enchers> getEnchersByUser(Long  userId){ //NOT YET TESTED
        String req = "SELECT * FROM enchers WHERE user = ?";
        List<Enchers> l = new ArrayList();
        try{
            stat = cnx.prepareStatement(req);
            stat.setLong(1, userId);
            
            ResultSet result = stat.executeQuery();
            
            while(result.next()){
                Enchers e = new Enchers();
                fillEnchers(this,result);
                l.add(e);
            }
            return l;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    private void fillEnchers(Enchers e , ResultSet result)throws SQLException{ //NOT YET TESTED
        e.id = result.getLong("id");
        e.date = result.getDate("date");
        e.price = result.getFloat("price");
        e.user = User.getElementById(result.getLong("user"));
        e.product = Product.getElementById(result.getLong("product"));
        
    }
    
}
