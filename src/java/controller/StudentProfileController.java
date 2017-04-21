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
import model.UnivBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class StudentProfileController {
    private StudentBean studentModel;
    
    public StudentProfileController() {
        studentModel = new StudentBean();
    }

    public StudentBean getStudentModel() {
        return studentModel;
    }

    public void setStudentModel(StudentBean studentModel) {
        this.studentModel = studentModel;
    }
    
    public String getProfilePage(String username) {
        ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(username);
        studentModel = tmp.get(0);
        
        return "StudentProfile.xhtml?faces-redirect=true";
    }    
    
    
    //TODO: must write
    public void requestInfo(UnivBean univBean, String request){
        studentModel.setRequest(request);
        studentModel.setRequestSender(univBean.getName());        
    }
    
    //TODO: must write
    public void scheduleAppt(UnivBean univBean){
        
        
    }
}
