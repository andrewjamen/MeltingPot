/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.StatusDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import model.Status;

/**
 *
 * @author josh
 */
public class StatusController {
    
    private Status statusModel;
    private String username;
    private String currentStatus;
    

    
    /**
     * Creates a new instance of StatusController
     */
    public StatusController() {
        this.statusModel = null;
        this.username = "";
        this.currentStatus = "";
    }
    

    
    public Status getStatusModel() {
        return statusModel;
    }

    public void setStatusModel(Status statusModel) {
        this.statusModel = statusModel;
    }

    
     /**
     * Initializes the username.
     */
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.username = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginController}", LoginController.class).getTheModel().getUsername();
    }
    
    
    public String getCurrentStatus() {
        return currentStatus;
    }

    
    
    public void changeCurrentStatus(){
        
        if(username == null)
        {
            init();
        }
        
        try {
            //statusModel.changeCurrentStatus();
        } catch (Exception ex) {
            Logger.getLogger(StatusController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
   
    
    
    
    

    
}
