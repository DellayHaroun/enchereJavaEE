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
    private Statement stat = null;

    private Integer id;
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
            stat = cnx.createStatement();
            }catch(Exception e)
        {
            e.printStackTrace();
        }   
    }
    
  
    public void setId(Integer id) {
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

   
    public Integer getId() {
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
    
    
    
    
   
    
}
