package beans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@RequestScoped
public class Connection {
    
    private String login;
    private String pwd;

    public String getLogin() {
        return login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        return "index.xhtml";

    }
    
    public boolean connected(){
        
        Object o = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id");
        
        return (o != null)? true : false;
    }
    
    public String connect(){ //NOT YET TESTED
        
        User u = new User();
        
        if(!u.exist(login, pwd))
            return "index.xhtml?connected=false";
        
        Long id = u.getIdByLogin(login);

        Object o = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("id", id);
        return "index.xhtml?connected=true";
    }
    
    public Long getId(){ //NOT YET TESTED
        Long id = (Long)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id");
        
        return id;
    }
}
