/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.StatusDAO;

/**
 *
 * @author josh vetter
 * @author Perry Kaufman
 */
public class Status {

    private String currentStatus;
    private UserBean user;

    public Status(UserBean user) {
        this.user = user;
        this.currentStatus = null;
        initStatus();
    }
    
    private void initStatus() {
        String previousStatus = StatusDAO.getCurrentStatus(user.getUsername());
        if (previousStatus == null) {
            StatusDAO.insertStatus("", user.getUsername());
            previousStatus = "";
        }
        this.currentStatus = previousStatus;
    }

    public String getCurrentStatus() {
        this.currentStatus = StatusDAO.getCurrentStatus(user.getUsername());
        if (this.currentStatus == null) return "";
        return this.currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        StatusDAO.updateStatus(user.getUsername(), currentStatus);
        this.currentStatus = currentStatus;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

}
