package controller;

import dao.UserDAO;
import dao.AdminDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.UserBean;
import model.AdminBean;

/**
 *
 * @author Andrew
 */
@ManagedBean
@SessionScoped
public class UserMessagesController {

    UserBean userBean;
    ArrayList<String> messages = new ArrayList();

    public UserMessagesController() {
        userBean = new UserBean();
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean studentBean) {
        this.userBean = studentBean;
    }

    public ArrayList<String> getMessages() {
        string2list(userBean.getMessages());
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public String goToPage(String username) {
        ArrayList<UserBean> tmp = (new UserDAO()).findByUsername(username);
        userBean = tmp.get(0);
        return "UserNotifications.xhtml?faces-redirect=true";
    }

    public int number() {
        int length = 0;
        ArrayList<String> temp = new ArrayList();
        temp = messages;
        messages = getMessages();
        length = temp.size();
        messages = temp;

        return length;
    }

    public ArrayList<String> string2list(String request) {

        if (request.equals("")) {
            return messages;
        }
        if (messages.size() > 0) {
            String lastNotif = messages.get(messages.size() - 1);
            int index = request.lastIndexOf(lastNotif);

            request = request.substring(index + lastNotif.length());
        }

        String lines[] = request.split("\\r?\\n");

        if (!lines[0].equals("")) {
            for (int i = 0; i < lines.length; i++) {
                messages.add(lines[i]);
            }
        }

        return messages;
    }

    //TODO: need to fix to user name (incase of repeat names)
    public String getSender(String message) {
        
        int index = message.indexOf(":");
        String name = message.substring(13, index);
        
        ArrayList<UserBean> tmp = (new UserDAO()).findByUsername(name);
        UserBean theBean = tmp.get(0);

        String sender = theBean.getUsername();

        return sender;
    }

}
