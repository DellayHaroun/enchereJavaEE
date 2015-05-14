/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Category {

    private final static String URL = "jdbc:mysql://localhost:3306/enchers";
    private final static String USER = "root";
    private final static String PWD = "";
    private Connection cnx = null;
    private PreparedStatement stat = null;
    
    private String label;
    
    
    public Category() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection(URL, USER, PWD);
        }catch(Exception e)
        {
            e.printStackTrace();
        } 
    }

   
    public String getLabel() {
        return label;
    }

   
    public void setLabel(String label) {
        this.label = label;
    }
    
//-------------------------------DAO----------------------------------------
    
     public void addCategory(Category c){ //NOT YET TESTED
        String req = "INSERT INTO category VALUES( null , ?);";
        try {
            stat = cnx.prepareStatement(req);
            stat.setString(1, c.label);
            
            stat.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public List<Category> getAllCategory(){ //TESTED WITH SUCCESS

        
        String req = "SELECT * FROM category;";
        List<Category> l = new ArrayList<Category>();
        
        try{
            stat = cnx.prepareStatement(req);
            ResultSet result = stat.executeQuery();
            
            while(result.next()){

                Category c = new Category();
                fillCategory(c,result);
                l.add(c);
            }    
                

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return l;   
    }
    
    private void fillCategory(Category c , ResultSet result)throws SQLException{ //NOT YET TESTED
        c.label = result.getString("label");
    }
    
}            
