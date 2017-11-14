/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.StatusDAO;
import java.util.Date;

/**
 *
 * @author josh vetter
 */
public class Status {
    
    private int id;
    private String currentStatus;
    private String username;
    
    public Status(String currentStatus, String username) {
    this.currentStatus = currentStatus;
    this.username = username;

    }

    public Status() {
    }


     public String getCurrentStatus(String username) {
        this.currentStatus = StatusDAO.getCurrentStatus(username);
        return this.currentStatus;
    }
     
    public void changeCurrentStatus() throws Exception {
        this.id = StatusDAO.changeCurrentStatus(username, currentStatus);
        if (id < 0) {
            throw new Exception("Status could not be changed.");
        }
    }
    
    public String getUsername(){
        return username;
    }
    
}
