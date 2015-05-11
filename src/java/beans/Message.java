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
public class Message {

    private final static String URL = "jdbc:mysql://localhost:3306/test";
    private final static String USER = "root";
    private final static String PWD = "";
    private Connection cnx = null;
    private Statement stat = null;
    
    
    private Long id;
    private String msg;
    private BaseCalendar.Date date;
    private User sender;
    private User receiver;
    
    
    public Message() {
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

    public String getMsg() {
        return msg;
    }

    public BaseCalendar.Date getDate() {
        return date;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setDate(BaseCalendar.Date date) {
        this.date = date;
    }
    
//-------------------------------DAO----------------------------------------
    
}
