package controller;

import model.StudentBean;

/**
 *
 * @author Andrew
 */
public class StudentAccountController {
    
    private StudentBean studentModel;
    private String accountInfo;
    
    public StudentAccountController(StudentBean studentModel) {
        this.studentModel = studentModel;
    }

    public StudentBean getStudentModel() {
        return studentModel;
    }

    public void setStudentModel(StudentBean studentModel) {
        this.studentModel = studentModel;
    }

    public String getAccountInfo() {
        accountInfo = "";
        accountInfo += studentModel.getFirstName() + studentModel.getLastName() + "\'s Account <br/>";
        accountInfo += "Location <br/>";
        accountInfo += "Address: " + studentModel.getAddress() + "<br/>";
        accountInfo += "City: " + studentModel.getCity() + "<br/>";
        accountInfo += "State: " + studentModel.getState() + "<br/>" + "<br/>";
        accountInfo += "High School: " + studentModel.getHighSchool() + "<br/><br/>";
        accountInfo += "Contact Information <br/>";
        accountInfo += "Email: " + studentModel.getEmail() + "<br/><br/>";
        accountInfo += "Statistics:";
        accountInfo += "ACT: " + studentModel.getAct() + "<br/>";
        accountInfo += "GPA: " + studentModel.getGpa() + "<br/>";
        accountInfo += "Desired Major: " + studentModel.getMajor() + "<br/><br/>";
        accountInfo += "Personal Information <br/>";
        accountInfo += "Extra Curricular Activities: " + "<br/>" + studentModel.getExtra() + "<br/><br/>";
        accountInfo += "Personal Statement: " + "<br/>" + studentModel.getStatement() + "<br/><br/>";

        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }
    
    
}
