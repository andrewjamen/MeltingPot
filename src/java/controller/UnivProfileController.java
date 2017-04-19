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

    /**
     * @return the univModel
     */
    public UnivBean getUnivModel() {
        return univModel;
    }

    /**
     * @param univModel the univModel to set
     */
    public void setUnivModel(UnivBean univModel) {
        this.univModel = univModel;
    }
    
    public String getProfilePage() {
        ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName("ilstu");
        
        univModel = tmp.get(0);
        
        return "UnivProfile.xhtml";
    }
}
