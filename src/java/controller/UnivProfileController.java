package controller;

import dao.UnivDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.StudentBean;
import model.UnivBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class UnivProfileController {
    
    private UnivBean univModel;
    
    public UnivProfileController() {
        univModel = new UnivBean();
    }

    public UnivBean getUnivModel() {
        return univModel;
    }

    public void setUnivModel(UnivBean univModel) {
        this.univModel = univModel;
    }
    
    public String getProfilePage(String username) {
        ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName(username);
        univModel = tmp.get(0);            

        return "UnivProfile.xhtml?faces-redirect=true";
    }
    
    //TODO: must write
    public void apply(StudentBean sutdentBean){
        
        
    }
    
    //TODO: must write
    public void requestInfo(StudentBean studentBean, String request){
        univModel.setRequest(request);
        univModel.setRequestSender(studentBean.getFirstName() + " " + studentBean.getLastName());  
        
    }
    
    //TODO: must write
    public void scheduleAppt(StudentBean studentBean){
        
        
    }
}
    
