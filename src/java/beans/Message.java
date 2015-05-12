/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

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
    private PreparedStatement stat = null;
    
    
    private Long id;
    private String msg;
    private Date date;
    private User sender;
    private User receiver;
    
    
    public Message() {
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

    public String getMsg() {
        return msg;
    }

    public Date getDate() {
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

    public void setDate(Date date) {
        this.date = date;
    }
    
//-------------------------------DAO----------------------------------------
    
    public void addMessage(Message m){ //NOT YET TESTED
        String req = "INSERT INTO message VALUES( null , '?', NOW(), ?, '?');";
        try {
            stat = cnx.prepareStatement(req);
            stat.setString(1, m.msg);
            stat.setLong(2,m.sender.getId());
            stat.setLong(3,m.receiver.getId());
            
            stat.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteMessage(Message m){ //NOT YET TESTED
        String req = "DELETE FROM message WHERE  1 ";
            req += (m.id != null)? "AND id = "+ m.id : "";
            req += (m.receiver.getLogin() != null)? "AND    receiver = '"+m.receiver.getLogin()+"' " : "";
            req += (m.sender.getLogin() != null)? "AND    sender = '"+m.sender.getLogin()+"' " : "";
            
        
        try{
            stat = cnx.prepareStatement(req);
            stat.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
     
    public List<Message> getMessage(Long messageId , User sender, User receiver){ //NOT YET TESTED
        String req = "SELECT * FROM message WHERE 1 ";
            req += (messageId != null)?" AND id = "+messageId :"";
            req += (sender != null)? " AND sender = "+sender.getId() : "";
            req += (receiver != null)? " AND receiver = "+receiver.getId() : "";
            req += ";";
            
        try{
            List<Message> l = new ArrayList<Message>();
            stat = cnx.prepareStatement(req);
            ResultSet result = stat.executeQuery();
            
            while(result.next()){
                Message m = new Message();
                fillMessage(m,result);
                
                l.add(m);
                return l;
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;            
    }
    
    private void fillMessage(Message m , ResultSet result)throws SQLException{ //NOT YET TESTED
        m.msg = result.getString("msg");
        m.date=result.getDate("date");
 //NOT YET       m.sender = result.getLong("sender");
//NOT YET        m.receiver = result.getLong("receiver");
    }
    
    
    
    
}
