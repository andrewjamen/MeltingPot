/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.StatusDAO;
import model.Status;

/**
 *
 * @author josh
 */
public class StatusController {
    
    private Status status;

    public StatusController() {
        status = new Status();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status userBean) {
        this.status = status;
    }
    
    public void updateStatus() {
        StatusDAO statusDAO = new StatusDAO();

        int status1 = statusDAO.changeStatus(status);
        if (status1 == 0)
        {
//            status change failed
        }


    }

    
}
