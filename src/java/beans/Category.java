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

/**
 *
 * @author amine
 */
@ManagedBean
@RequestScoped
public class Category {

    private final static String URL = "jdbc:mysql://localhost:3306/test";
    private final static String USER = "root";
    private final static String PWD = "";
    private Connection cnx = null;
    private Statement stat = null;
    
    private Long id;
    private String label;
    
    
    public Category() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection(URL, USER, PWD);
            stat = cnx.createStatement();
        }catch(Exception e)
        {
            e.printStackTrace();
        } 
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
//-------------------------------DAO----------------------------------------

    
    
}
