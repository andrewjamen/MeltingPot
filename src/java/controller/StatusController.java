/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Status;
import model.UserBean;

/**
 *
 * @author Perry Kaufman
 */
@ManagedBean
@SessionScoped
public class StatusController {
    
    private Status statusModel;
    private UserBean userModel;
    private String newStatus;
    
    /**
     * Creates a new instance of StatusController
     */
    public StatusController() {
        this.userModel = null;
        this.statusModel = null;
        this.newStatus = "";
    }
 
     /**
     * Initializes the userModel and statusModel.
     */
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.userModel = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginController}", LoginController.class).getTheModel();
        this.statusModel = new Status(userModel);
    }
    
    public String getCurrentStatus() {
        if (statusModel == null) init();
        return statusModel.getCurrentStatus();
    }

    public void updateCurrentStatus() {
        if (statusModel == null) init();
        statusModel.setCurrentStatus(newStatus);
        this.newStatus = "";
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
