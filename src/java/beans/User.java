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
import javax.faces.bean.SessionScoped;

/**
 *
 * @author amine
 */
@ManagedBean
@SessionScoped
public class User {

    private final static String URL = "jdbc:mysql://localhost:3306/test";
    private final static String USER = "root";
    private final static String PWD = "";
    
    private Connection cnx = null;
    private PreparedStatement stat = null;

    private Long id;
    private String login;
    private String pwd;
    private String type;
    private String tel;
    private String name;
    private Local country;

    
    
     public User() {
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection(URL, USER, PWD);
            }catch(Exception e)
        {
            e.printStackTrace();
        }   
    }
    
  
    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

  
    public void setType(String type) {
        this.type = type;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

   
    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

  
    public String getType() {
        return type;
    }

    public String getTel() {
        return tel;
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return name;
    }

    public Local getCountry() {
        return country;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Local country) {
        this.country = country;
    }

  
 //-------------------------------DAO----------------------------------------
    
    
    public void addUser(User u){ // NOT YET TESTED
        String req = "INSERT INTO users VALUES (null, '?','?','?','?','?',?);";
        
        try{
            stat = cnx.prepareStatement(req);
            stat.setString(1, u.login);
            stat.setString(1, u.pwd);
            stat.setString(1, u.type);
            stat.setString(1, u.tel);
            stat.setString(1, u.name);
            stat.setLong(1, u.country.getId());
            
            stat.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
    public void deleteUser(Long userId){ //NOT YET TESTED
        String req = "DELETE FROM users WHERE id = "+userId+";";
        
        try{
            cnx.createStatement().executeUpdate(req);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    
    public List<User> getAllUser(){ //NOT YET TESTED
        String req = "SELECT * FROM users;";
        List<User> l = new ArrayList<User>();
        
        try{
            stat = cnx.prepareStatement(req);
            ResultSet result = stat.executeQuery();
            
            while(result.next()){
                User u = new User();
                
                fillUser(u,result);
                l.add(u);
            }    
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return l;   
    }
    
   private void fillUser(User u , ResultSet result)throws SQLException{ //NOT YET TESTED
        
        u.id = result.getLong("id");
        u.login = result.getString("login");
        u.pwd = result.getString("pwd");
        u.type = result.getString("type");
        u.tel = result.getString("tel");
        u.name = result.getString("name");
//NOT YET   u.country = result.getString("country");        
    }
   
   public User getElementById(Long userId){
        String req = "SELECT * FROM users WHERE id = ?";
        
        try{
            stat= cnx.prepareStatement(req);
            stat.setLong(1, userId);
            ResultSet result = stat.executeQuery();
            result.next();
            
            User u = new User();
            fillUser(u, result);
            return u;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
   
   
}
