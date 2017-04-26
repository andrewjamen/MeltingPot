/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.StudentDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.StudentBean;

/**
 *
 * @author Andrew
 */
@ManagedBean
@SessionScoped
public class StudentUpdateController {

    private StudentBean theModel;
    private String updateStatus;

    public StudentUpdateController() {
        theModel = new StudentBean();
    }

    public StudentBean getTheModel() {
        return theModel;
    }

    public void setTheModel(StudentBean theModel) {
        this.theModel = theModel;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String retrieveProfile(StudentBean theBean) {
        theModel = theBean;
        StudentDAO aStudentDAO = new StudentDAO();
        ArrayList users = aStudentDAO.findByUserName(theModel.getUsername()); 
        theModel = (StudentBean) users.get(0); 
        if (theModel != null) {
            return "StudentUpdate.xhtml"; 
        } else {
            return "error.xhtml";
        }
    }

    //TODO: dont log out
    public String updateUser() {
        StudentDAO aStudentDAO = new StudentDAO(); 
        int status1 = aStudentDAO.updateProfile(theModel); 
        if (status1 != 0) {
            updateStatus = "User Record updated successfully ...";
        } else {
            updateStatus = "User Record update failed!";
        }

        if (status1 != 0) {
            StudentLoginController login = new StudentLoginController();
            login.logout();
            return "Login.xhtml?faces-redirect=true";
        } else {
            return "error.xhtml";
        }

    }

    public String checkAvailable() {

        if ("".equals(theModel.getUsername()) || theModel.getUsername() == null) {
            return "Enter a username!";
        }
        boolean goodLogin = true;
        StudentDAO aStudentDAO = new StudentDAO();
        ArrayList<StudentBean> allUsers = aStudentDAO.findAll();

        for (int i = 0; i < allUsers.size(); i++) {
            if (theModel.getUsername().equals(allUsers.get(i).getUsername())) {
                goodLogin = false;
            }
        }

        if (goodLogin == false) {
            return "Username already taken!";
        } else {
            return "Username available!";
        }
    }

    public String checkMatch() {

        if ("".equals(theModel.getConfirm()) || theModel.getConfirm() == null) {
            return "Enter a password!";
        }
        boolean goodPassword = false;

        if (theModel.getPassword().equals(theModel.getConfirm())) {
            goodPassword = true;
        }

        if (goodPassword == false) {
            return "Passwords do not match!";
        } else {
            return "Passwords match!";
        }
    }

}
