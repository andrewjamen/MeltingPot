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
 */
public class Status {

    private String currentStatus;
    private UserBean user;

    public Status(UserBean user) {
        this.user = user;
        this.currentStatus = StatusDAO.getCurrentStatus(user.getUsername());
    }

    public void updateCurrentStatus(String username) {
        currentStatus = StatusDAO.getCurrentStatus(username);
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void changeCurrentStatus(String currentStatus) {
        if (this.currentStatus == null) {
           StatusDAO.insertStatus(currentStatus, user.getUsername());
        }
        StatusDAO.updateStatus(user.getUsername(), currentStatus);
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

}
