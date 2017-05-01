/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UnivDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import model.UnivBean;

/**
 *
 * @author IT353S712
 */
@ManagedBean
@ApplicationScoped
public class ShowcasedUnivController {
    private UnivBean model;
    
    public ShowcasedUnivController() {
        ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName("ilstu");
        model = tmp.get(0);
    }

    /**
     * @return the model
     */
    public UnivBean getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(UnivBean model) {
        this.model = model;
    }
    
    public void setShowcasedUniv(String username) {
        ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName(username);
        model = tmp.get(0);    
    }
}
