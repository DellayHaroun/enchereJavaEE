/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

/**
 *
 * @author amine
 */
@ManagedBean
@RequestScoped
public class Product {
    private final static String URL = "jdbc:mysql://localhost:3306/enchers";
    private final static String USER = "root";
    private final static String PWD = "";
    
    private static Connection cnx = null;
    private static PreparedStatement stat = null;
    
    
    private Long id;
    private String label;
    private Integer quantity;
    private Float basicPrice;
    private Date date;
    private String status;
    private String description;
    private User buyer;
    private User seller;
    private Local country;
    private Category category;
    private Part image;
    private String imagePath;

    
    public Product() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection(URL, USER, PWD);
            }catch(Exception e)
        {
            e.printStackTrace();
        }   
    }

    
    public void setImage(Part image) {
        this.image = image;
    }

    public Part getImage() {
        return image;
    }
    
    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getBasicPrice() {
        return basicPrice;
    }

    public Date getDate() {
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

    public Category getCategory() {
        return category;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setBasicPrice(Float basicPrice) {
        this.basicPrice = basicPrice;
    }

    public void setDate(Date date) {
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

    public void setCategory(Category category) {
        this.category = category;
    }
    
  
 //-------------------------------DAO----------------------------------------
    


    public void addProduct(){ //NOT YET TESTED
        upload();
        String req = "INSERT INTO products VALUES( null , '?', ?, ?, '?'"
                + ", '?', '?', ?, ?, ?, ?, '?');";
        try {
            stat = cnx.prepareStatement(req);
            stat.setString(1,label);
            stat.setInt(2,quantity);
            stat.setFloat(3,basicPrice);
            stat.setDate(4,date);
            stat.setString(5,status);
            stat.setString(6,description);
            stat.setLong(7,buyer.getId());
            stat.setLong(8,seller.getId());
            stat.setLong(9,country.getId());
            stat.setLong(10,category.getId());
            stat.setString(11,imagePath);
            
            stat.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteProduct(Long productId){ //NOT YET TESTED
        String req = "DELETE FROM products WHERE id = ?;";
        
        try{
            stat = cnx.prepareStatement(req);
            stat.setLong(1,productId);
            stat.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public List<Product> getAllProducts(){
        String req = "SELECT * FROM products;";
        List<Product> l = new ArrayList<Product>();
        
        try{
            stat = cnx.prepareStatement(req);
            ResultSet result = stat.executeQuery();
            
            while(result.next()){
                Product p = new Product();
                
                fillProduct(p,result);
                l.add(p);
            }    
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return l;   
    }
    
    public void updateProduct(){//NOT YET TESTED
        String req = "UPDATE products SET id = ?";
            req += (label != null)? ", label = '?' " : "";
            req += (quantity != null)? ", quantity = ? " : "";
            req += (date != null)? ", date = '?'" : ""; //NOT SURE THIS WILL WORK
            req += (status != null)? ", status = '?' " : "";
            req += (seller != null)? ", seller = '?' " : "";
            req += "WHERE id = "+id+";";
           
        try{
            stat = cnx.prepareStatement(req);
            
            stat.setLong(1, id);
            stat.setString(2, label);
            stat.setInt(3, quantity);
            stat.setDate(4, date);
            stat.setString(5, status);
            stat.setLong(6, seller.getId());
            
            stat.executeQuery();
            
        }catch(SQLException e){
            e.printStackTrace();
        }        
    }
    
    
    public static Product getElementById(Long productId){
        String req = "SELECT * FROM products WHERE id = ?";
        
        try{
            stat= cnx.prepareStatement(req);
            stat.setLong(1, productId);
            ResultSet result = stat.executeQuery();
            result.next();
            
            Product p = new Product();
            fillProduct(p, result);
            return p;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    private static void fillProduct(Product p , ResultSet result)throws SQLException{ //NOT YET TESTED
        
        p.id = result.getLong("id");
        p.label = result.getString("label");
        p.quantity = result.getInt("quantity");
        p.basicPrice = result.getFloat("basicPrice");
        p.date = result.getDate("date");
        p.status = result.getString("status");
        p.description = result.getString("description");
        Long buyerId = result.getLong("buyer");
        Long sellerId = result.getLong("seller");
        Long countryId = result.getLong("country");
        Long categoryId = result.getLong("category");
        p.imagePath = result.getString("image");
        
        p.buyer = new User(); p.seller = new User(); p.country = new Local();
        p.category = new Category();
        
        p.buyer.getUser(buyerId);
        p.seller.getUser(sellerId);
        p.country.getLocal(countryId);
        p.category.getCategory(categoryId);
    }
    
    public List<Product> searchProduct(Product p){ //NOT YET TESTED
        String req = "SELECT * FROM products WHERE 1 ";
            req += (p.id != null)? "AND id = "+p.id : "";
            req += (p.label != null)? "AND label = "+p.label : "";
            req += (p.basicPrice != null)? "AND basicPrice = "+p.basicPrice : "";
            req += (p.date != null)? "AND date = "+p.date : "";
            req += (p.status != null)? "AND status = "+p.status : "";
            req += (p.buyer.getId() != null)? "AND buyer = "+p.buyer.getId() : "";
            req += (p.seller.getId() != null)? "AND seller = "+p.seller.getId() : "";
            req += (p.country.getCountry() != null)? "AND country = "+p.country.getCountry() : "";
            req += (p.category.getLabel() != null)? "AND category = "+p.category.getLabel() : "";
        try{
            stat = cnx.prepareStatement(req);
            ResultSet result = stat.executeQuery();
            List<Product> l = new ArrayList<Product>();
            while(result.next()){
                Product pAux = new Product();
                fillProduct(pAux, result);
                l.add(pAux);
            }
            return l;
            
        }
        catch(SQLException e)
        {
                e.printStackTrace();
        }
        return null;
    }
    

    public boolean exist(Long productId , String label){ //NOT YET TESTED

        
        String req = "SELECT id FROM products WHERE 1 ";
            req += (productId != null)? "AND id = " + productId : "";
            req += (label != null)? "AND label = " + label : "";
            
        
        try{
            stat = cnx.prepareStatement(req);
            ResultSet result = stat.executeQuery();
            return result.isBeforeFirst();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public void upload(){
        try{
            String name = image.getSubmittedFileName();
            
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
            imagePath = ctx.getRealPath("/")+"../../web/ressources/images/"+name;
              
            InputStream in = image.getInputStream();
           
            OutputStream out = new FileOutputStream(imagePath);
         
            
            int c;
            while((c = in.read() ) != -1 ){
                out.write(c);
            }         
        }  
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
