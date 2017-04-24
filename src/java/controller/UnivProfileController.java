package controller;

import dao.StudentDAO;
import dao.UnivDAO;
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
public class UnivProfileController {

    private UnivBean univModel;
    String requestMessage = "";

    public UnivProfileController() {
        univModel = new UnivBean();
    }

    public UnivBean getUnivModel() {
        return univModel;
    }

    public void setUnivModel(UnivBean univModel) {
        this.univModel = univModel;
    }

    public String getProfilePage(String username) {
        ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName(username);
        univModel = tmp.get(0);

        return "UnivProfile.xhtml?faces-redirect=true";
    }

    public void sendUnivRequest(String sender, String request) {
        UnivDAO aUnivDAO = new UnivDAO();

        ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(sender);
        StudentBean theStudentBean = tmp.get(0);
        sender = theStudentBean.getFirstName() + " " + theStudentBean.getLastName();

        if (!univModel.getRequest().equals("")) {
            requestMessage = univModel.getRequest() + "\n";
        }

        requestMessage += "Message from " + sender
                + ":  \t" + request;

        aUnivDAO.insertRequest(univModel, requestMessage);
    }

    //TODO: must write
    public void apply(StudentBean sutdentBean) {

    }

    //TODO: must write
    public void scheduleAppt(StudentBean studentBean) {

    }
}
