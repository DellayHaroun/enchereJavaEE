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

/**
 *
 * @author amine
 */
@ManagedBean
@RequestScoped
public class User {

    private final static String URL = "jdbc:mysql://localhost:3306/enchers";
    private final static String USER = "root";
    private final static String PWD = "";
    
    private static Connection cnx = null;
    private static PreparedStatement stat = null;

    private Long id;
    private String login;
    private String pwd;
    private String type;
    private String tel;
    private String name;
    private String adress;

    
    
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

    public String getAdress() {
        return adress;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

  
 //-------------------------------DAO----------------------------------------
    
    

    public String addUser(){ // TESTED WITH SUCCESS
        if(alreadyExist(login)) return "produiuts.xhtml?inscription=false";
        
        String req = "INSERT INTO users VALUES (null, ? , ? , ? , ? , ? , ? );";
        
        try{
            stat = cnx.prepareStatement(req);
            stat.setString(1, login);
            stat.setString(2, pwd);
            stat.setString(3, type);
            stat.setString(4, tel);
            stat.setString(5, name);
            stat.setString(6, adress);

            stat.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "index.xhtml?inscription=true";
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
    
   private static void fillUser(User u ,ResultSet result)throws SQLException{ //NOT YET TESTED
        
        u.id =result.getLong("id");
        u.login = result.getString("login");
        u.pwd = result.getString("pwd");
        u.type = result.getString("type");
        u.tel = result.getString("tel");
        u.name = result.getString("name");
        u.adress = result.getString("adress");
        
        
    }
   
   public static User getElementById(Long userId){ //NOT YET TESTED
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
   
   
   public List<User> searchUser(User u){ //NOT YET TESTED
            
        String req = "SELECT * FROM users WHERE 1 ";
            req += (u.id != null)? "AND id = "+u.id : "";
            req += (u.login != null)? "AND login = "+u.login : "";
            req += (u.type != null)? "AND type = "+u.type : "";
            req += (u.name != null)? "AND name = "+u.name : "";
            req +=";";
        try{
            stat = cnx.prepareStatement(req);
            ResultSet result = stat.executeQuery();
            List<User> l = new ArrayList<User>();
            while(result.next()){
                User uAux = new User();
                fillUser(uAux, result);
                l.add(uAux);
            }
            return l;
            
        }
        catch(SQLException e)
        {
                e.printStackTrace();
        }
        return null;
    }
   
   
   public boolean alreadyExist(String login){ //TESTED WITH SUCCESS
        
        String req = "SELECT id FROM users WHERE login = ?;";

        try{
            stat = cnx.prepareStatement(req);
            stat.setString(1,login);
            ResultSet result = stat.executeQuery();
            return result.isBeforeFirst();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
        
    }
   
   public boolean exist(String login , String pwd){
       String req = "SELECT id FROM users WHERE login = ? AND pwd = ?;";
       
       try{
           stat = cnx.prepareStatement(req);
           stat.setString(1,login);
           stat.setString(2,pwd);
           ResultSet result = stat.executeQuery();
           return result.isBeforeFirst();
       }catch(SQLException e){
           e.printStackTrace();
       }
       return false;
   }
  
   public Long getIdByLogin(String login){
       String req = "SELECT id FROM users WHERE login = ?";
       try{
           stat = cnx.prepareStatement(req);
           stat.setString(1,login);
           ResultSet result = stat.executeQuery();
           if(result.next() == false) return null;
           
           return result.getLong("id");
       }catch(SQLException e){
           e.printStackTrace();
       }
       return null;
   }
   
    public User getUser(Long userId){ //NOT YET TESTED
        String req = "SELECT * FROM user WHERE id = ?";
        
        try{
            stat = cnx.prepareStatement(req);
            stat.setLong(1, userId);
            
            ResultSet result = stat.executeQuery();
            result.next();
            
            fillUser(this, result);
            
            return this;
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
            
}
