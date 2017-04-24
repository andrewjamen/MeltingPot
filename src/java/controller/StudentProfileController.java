/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.StudentDAO;
import dao.UnivDAO;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.StudentBean;
import model.UnivBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class StudentProfileController {

    private StudentBean studentModel;
    String requestMessage = "";
    Date date;

    public StudentProfileController() {
        studentModel = new StudentBean();
    }

    public StudentBean getStudentModel() {
        return studentModel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setStudentModel(StudentBean studentModel) {
        this.studentModel = studentModel;
    }

    public String getProfilePage(String username) {
        ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(username);
        studentModel = tmp.get(0);
        return "StudentProfile.xhtml?faces-redirect=true";
    }

    public void sendStudentRequest(String sender, String request) {

        StudentDAO aStudentDAO = new StudentDAO();

        findProfile(studentModel.getUsername());
        if (!studentModel.getRequest().equals("")) {
            requestMessage = studentModel.getRequest() + "\n";
        }

        requestMessage += "Message from " + sender
                + ":  \t" + request;

        aStudentDAO.insertRequest(studentModel, requestMessage);
    }

    public void scheduleAppt(String sender) {
        StudentDAO aStudentDAO = new StudentDAO();

        ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName(sender);
        UnivBean theUnivBean = tmp.get(0);
        sender = theUnivBean.getName();

        findProfile(studentModel.getUsername());
        if (!studentModel.getRequest().equals("")) {
            requestMessage = studentModel.getRequest() + "\n";
        }

        requestMessage += "Appointment request from " + sender
                + " at:  \t" + date;

        aStudentDAO.insertRequest(studentModel, requestMessage);
    }
    
    public void findProfile(String username){
        ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(username);
        studentModel = tmp.get(0);
    }
}
