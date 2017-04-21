/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UnivDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.StudentBean;
import model.UnivBean;

/**
 *
 * @author IT353S712
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

        return "UnivProfile.xhtml";
    }
    
    //TODO: must write
    public void apply(StudentBean sutdentBean){
        
        
    }
    
    //TODO: must write
    public void requestInfo(StudentBean studentBean){
        
        
    }
    
    //TODO: must write
    public void scheduleAppt(StudentBean studentBean){
        
        
    }
}
    
