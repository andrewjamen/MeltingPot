package controller;

import dao.StudentDAO;
import dao.UnivDAO;
import isu.ISUGoogleMapsService;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.ws.WebServiceRef;
import model.StudentBean;
import model.UnivBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class UnivProfileController {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/gfish.it.ilstu.edu_8080/ISUGoogleMaps/ISUGoogleMapsService.wsdl")
    private ISUGoogleMapsService service;

    private UnivBean univModel;
    String requestMessage = "";
    Date date;

    public UnivProfileController() {
        univModel = new UnivBean();
    }

    public UnivBean getUnivModel() {
        return univModel;
    }

    public void setUnivModel(UnivBean univModel) {
        this.univModel = univModel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        
        findProfile(univModel.getUsername());
        if (!univModel.getRequest().equals("")) {
            requestMessage = univModel.getRequest() + "\n";
        }

        requestMessage += "Message from " + sender
                + ":  \t" + request;

        aUnivDAO.insertRequest(univModel, requestMessage);
    }

    //TODO: check if null & make button animation
    public void scheduleAppt(String sender) {
        UnivDAO aUnivDAO = new UnivDAO();

        ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(sender);
        StudentBean theStudentBean = tmp.get(0);
        sender = theStudentBean.getFirstName() + " " + theStudentBean.getLastName();

        findProfile(univModel.getUsername());
        if (!univModel.getRequest().equals("")) {
            requestMessage = univModel.getRequest() + "\n";
        }

        requestMessage += "Appointment request from " + sender
                + " at:  \t" + date;

        aUnivDAO.insertRequest(univModel, requestMessage);
    }
    
    //TODO: check if null & make button animation
    public void findProfile(String username){
        ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName(username);
        univModel = tmp.get(0);
    }

    //TODO: check if null & make button animation
    public void apply(String sender) {
        UnivDAO aUnivDAO = new UnivDAO();

        ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(sender);
        StudentBean theStudentBean = tmp.get(0);
        sender = theStudentBean.getFirstName() + " " + theStudentBean.getLastName();

        findProfile(univModel.getUsername());
        if (!univModel.getRequest().equals("")) {
            requestMessage = univModel.getRequest() + "\n";
        }

        requestMessage += sender + " has submitted an application! \t View their profile to review student statstics:";

        aUnivDAO.insertRequest(univModel, requestMessage);
    }
    
    public void map(String startLocationCity, String startLocationState, String endLocationCity, String endLocationState){
        String result = getDirectionsByCityState(startLocationCity, startLocationState, endLocationCity, endLocationState);
        //outputting to file
        FileOutputStream out;
        try {
            out = new FileOutputStream("SearchMap.html");
            PrintStream p = new PrintStream(out);
            p.println(result);
            //opening file programmatically
            Desktop.getDesktop().open(new File("SearchMap.html"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String getDirectionsByCityState(java.lang.String startLocationCity, java.lang.String startLocationState, java.lang.String endLocationCity, java.lang.String endLocationState) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        isu.ISUGoogleMaps port = service.getISUGoogleMapsPort();
        return port.getDirectionsByCityState(startLocationCity, startLocationState, endLocationCity, endLocationState);
    }
}
