/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author josh vetter
 */
public class Status {
    
    private int statusID;
    private String status;
    private Date dateTime;
    private String username;
    
    public Status(int statusID, String status, Date dateTime,String username) {
    this.statusID = statusID;
    this.status = status;
    this.dateTime = dateTime;
    this.username = username;

    }

    public Status() {
    }
    
     public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }
    
     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
     public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    
    public String getUsername(){
        return username;
    }
    
}
