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
public class Local {

    private final static String URL = "jdbc:mysql://localhost:3306/enchers";
    private final static String USER = "root";
    private final static String PWD = "";
    private Connection cnx = null;
    private PreparedStatement stat = null;
    
    private Long id;
    private String country;

    public void setId(Long id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }
    
    
    public Local() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection(URL, USER, PWD);
        }catch(Exception e)
        {
            e.printStackTrace();
        } 
    }

 
//-------------------------------DAO----------------------------------------
    
      public void addLocal(Local l){ //NOT YET TESTED
        String req = "INSERT INTO local VALUES( null , '?');";
        try {
            stat = cnx.prepareStatement(req);
            stat.setString(1, l.country);
            
            stat.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteLocal(Local l){ //NOT YET TESTED
        String req = "DELETE FROM local WHERE 1"; 
            req += (l.id != null)? "AND id = "+l.id : "";
            req += (l.country != null)? "AND country = "+l.country : "";
        
            
        try{
            stat = cnx.prepareStatement(req);
            stat.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public List<Local> getAllLocal(){ // NOT YET TESTED
        String req = "SELECT * FROM local;";
        List<Local> l = new ArrayList<Local>();
        
        try{
            stat = cnx.prepareStatement(req);
            ResultSet result = stat.executeQuery();
            
            while(result.next()){
                Local lo = new Local();
                fillLocal(lo,result);
                l.add(lo);
            }    
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return l;   
    }
    
    private void fillLocal(Local lo , ResultSet result)throws SQLException{ //NOT YET TESTED
        lo.id = result.getLong("id");
        lo.country = result.getString("country");
    }
    
    public void updateLocal(Local lo){//NOT YET TESTED
        String req = "UPDATE local  SET country = '?' WHERE id = ?";
        
        try{
            stat = cnx.prepareStatement(req);
            stat.setString(1, lo.country);
            stat.setLong(2, lo.id);
            stat.executeQuery();
            
        }catch(SQLException e){
            e.printStackTrace();
            
        }        
    }
    
     public Local getLocal(Long localId){ //NOT YET TESTED
        String req = "SELECT * FROM local WHERE id = ?";
        
        try{
            stat = cnx.prepareStatement(req);
            stat.setLong(1, localId);
            
            ResultSet result = stat.executeQuery();
            result.next();
            
            id = localId;
            country = result.getString("country");
            
            return this;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
            
    
    
}
