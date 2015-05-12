package beans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;


@ManagedBean
@RequestScoped
public class Connection {

   
    public void logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response =(HttpServletResponse)context.getExternalContext().getResponse();

            response.sendRedirect("index.xhtml");

            context.responseComplete();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    public boolean connected(){
        
        Object o = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id");
        
        return (o != null)? true : false;
    }
    
    public String connect(int id){
        Object o = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("id", id);
        return "page1";
                
    }
    
    public Long getId(){ //NOT YET TESTED
        Long id = (Long)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id");
        
        return id;

    }
    
   
    
    
}
