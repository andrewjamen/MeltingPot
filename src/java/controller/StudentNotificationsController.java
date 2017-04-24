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
 * @author Andrew
 */
@ManagedBean
@SessionScoped
public class StudentNotificationsController {

    StudentBean studentBean;
    ArrayList<String> notifications = new ArrayList();

    public StudentNotificationsController() {
        studentBean = new StudentBean();
    }

    public StudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(StudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public ArrayList<String> getNotifications() {
        string2list(studentBean.getRequest());
        return notifications;
    }

    public void setNotifications(ArrayList<String> notifications) {
        this.notifications = notifications;
    }

    public String goToPage(String username) {
        ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(username);
        studentBean = tmp.get(0);
        return "StudentNotifications.xhtml?faces-redirect=true";
    }

    public int number() {
        int length = 0;
        ArrayList<String> temp = new ArrayList();
        temp = notifications;
        notifications = getNotifications();
        length = temp.size();
        notifications = temp;

        return length;
    }

    public ArrayList<String> string2list(String request) {

        if (request.equals("")) {
            return notifications;
        }
        if (notifications.size() > 0) {
            String lastNotif = notifications.get(notifications.size() - 1);
            int index = request.lastIndexOf(lastNotif);

            request = request.substring(index + lastNotif.length());
        }

        String lines[] = request.split("\\r?\\n");

        if (!lines[0].equals("")) {
            for (int i = 0; i < lines.length; i++) {
                notifications.add(lines[i]);
            }
        }

        return notifications;
    }
    
    public String getSender(String request){
        int end = request.indexOf(":");
        String sender = request.substring(13, end);
        
        ArrayList<UnivBean> tmp = (new UnivDAO()).findByName(sender);
        UnivBean theBean = tmp.get(0);
        
        sender = theBean.getUsername();
        
        return sender;
    }
    

}
