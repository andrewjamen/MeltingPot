/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.UnivBean;

/**
 *
 * @author IT353S712
 */
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
}
