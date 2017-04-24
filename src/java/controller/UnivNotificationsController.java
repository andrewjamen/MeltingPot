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
 * @author Andrew
 */
@ManagedBean
@SessionScoped
public class UnivNotificationsController {

    UnivBean univBean;
    ArrayList<String> notifications = new ArrayList();

    public UnivNotificationsController() {
        univBean = new UnivBean();
    }

    public UnivBean getUnivBean() {
        return univBean;
    }

    public void setUnivBean(UnivBean univBean) {
        this.univBean = univBean;
    }

    public ArrayList<String> getNotifications() {
        string2list(univBean.getRequest());
        return notifications;
    }

    public void setNotifications(ArrayList<String> notifications) {
        this.notifications = notifications;
    }

    public String goToPage(String username) {
        ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName(username);
        univBean = tmp.get(0);
        return "UnivNotifications.xhtml?faces-redirect=true";
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

    public String getSender(String request) {
        String firstName = "";
        String lastName = "";
        
        if (!request.contains("Appointment")) {
            int middle = request.indexOf(" ", 13);
            int end = request.indexOf(":");
            firstName = request.substring(13, middle);
            lastName = request.substring(middle + 1, end);
        } else {
            int middle = request.indexOf(" ", 25);
            int end = request.indexOf("at:");
            firstName = request.substring(25, middle);
            lastName = request.substring(middle + 1, end);
        }
        ArrayList<StudentBean> tmp = (new StudentDAO()).findByName(firstName, lastName);
        StudentBean theBean = tmp.get(0);

        String sender = theBean.getUsername();

        return sender;
    }

}
