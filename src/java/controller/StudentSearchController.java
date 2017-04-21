package controller;

import dao.StudentDAO;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.StudentBean;

/**
 *
 * @author jfoss
 */
@ManagedBean
@SessionScoped
public class StudentSearchController {
    private String results;
    private StudentBean theModel;
    
    public StudentSearchController() {
        theModel = new StudentBean();
    }

    public StudentBean getTheModel() {
        return theModel;
    }

    public void setTheModel(StudentBean theModel) {
        this.theModel = theModel;
    }

    public ArrayList<StudentBean> getResults() {
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<StudentBean> users = studentDAO.searchForUsers(theModel.getUsername(), theModel.getFirstName(), theModel.getLastName(), theModel.getState(), theModel.getAct(), theModel.getGpa());
        /*
        String resultStr = "";
        if(!users.isEmpty()){
            for(int i=0; i<users.size(); i++){
                resultStr += users.get(i).getUsername() + " - " + users.get(i).getFirstName() + " " + users.get(i).getLastName() + " " + "<button type=\"button\">View Profile</button>" + "<br/>";
            }
        } else{
            resultStr += "Nothing found! No students match the given parameters";
        }
        results = resultStr;
        */
               
        
        return users;
    }

    public void setResults(String results) {
        this.results = results;
    }
    
    public String search(){
        if(!theModel.getUsername().equals("") || !theModel.getFirstName().equals("") || !theModel.getLastName().equals("") || !theModel.getState().equals("")
                || theModel.getAct() != 0 || theModel.getGpa() != 0.0){
            return "StudentResults.xhtml";
        } else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Please provide information before searching"));
            return "StudentSearch.xhtml";
        }
    }
}
