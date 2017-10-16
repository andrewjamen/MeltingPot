package controller;

import dao.UserDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.UserBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class FriendController {

    UserBean userBean;
    ArrayList<String> friendRequests = new ArrayList();
    ArrayList<String> friendList = new ArrayList();

    public FriendController() {
        userBean = new UserBean();
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean studentBean) {
        this.userBean = studentBean;
    }

    public ArrayList<String> getFriendRequests() {
        string2requests(userBean.getFriendRequest());
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<String> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public ArrayList<String> getFriendList() {
        string2list(userBean.getFriendList());
        return friendList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    public String viewFriendRequests(String username) {
        ArrayList<UserBean> tmp = (new UserDAO()).findByUsername(username);
        userBean = tmp.get(0);
        return "/Friend/FriendRequest.xhtml?faces-redirect=true";
    }

    public String viewFriendList(String username) {
        ArrayList<UserBean> tmp = (new UserDAO()).findByUsername(username);
        userBean = tmp.get(0);
        return "/Friend/FriendList.xhtml?faces-redirect=true";
    }

    public int numberRequests() {
        int length = 0;
        friendRequests = getFriendRequests();
        length = friendRequests.size();

        return length;
    }

    public int numberList() {
        int length = 0;
        friendList = getFriendList();
        length = friendList.size();

        return length;
    }

    public ArrayList<String> string2list(String str) {

        if (str.equals("") || str.equals("\n")) {
            return friendList;
        }
        if (friendList.size() > 0) {
            str = "";
        }

        String lines[] = str.split("\\r?\\n");

        if (!lines[0].equals("")) {
            for (int i = 0; i < lines.length; i++) {
                friendList.add(lines[i]);
            }
        }

        return friendList;
    }

    public ArrayList<String> string2requests(String str) {

        if (str.equals("") || str.equals("\n")) {
            return friendRequests;
        }
        if (friendRequests.size() > 0) {
            str = "";
        }

        String lines[] = str.split("\\r?\\n");

        if (!lines[0].equals("")) {
            for (int i = 0; i < lines.length; i++) {
                friendRequests.add(lines[i]);
            }
        }

        return friendRequests;
    }

    public String getRequestSender(String request) {

        int index = request.indexOf(" ");
        String username = request.substring(0, index);

        ArrayList<UserBean> tmp = (new UserDAO()).findByUsername(username);
        UserBean theBean = tmp.get(0);

        String sender = theBean.getUsername();

        return sender;
    }

    public String confrimFriend(String username) {

        UserDAO aUserDAO = new UserDAO();
        String allFriends = "";

        if (!userBean.getFriendList().equals("")) {
            allFriends = userBean.getFriendList();
        }

        allFriends += username + "\n";

        aUserDAO.addFriend(userBean, allFriends);
        aUserDAO.removeRequest(userBean, userBean.getFriendRequest());

        //TODO: reload and update page
        return "/Friend/FriendRequest.xhtml?faces-redirect=true";
    }

    public String denyFriend(String username) {

        UserDAO aUserDAO = new UserDAO();
        String allRequests = "";

        if (!userBean.getFriendRequest().equals("")) {
            allRequests = userBean.getFriendRequest();
        }

        int index = allRequests.indexOf(username);

        String removal = allRequests.substring(index, username.length() + index + 23);

        String[] lines = allRequests.split("\n");
        allRequests = "";

        for (int i = 0; i < lines.length; i++) {
            if (!lines[i].equals(removal)) {
                allRequests += lines[i] + "\n";
            }
        }

        aUserDAO.removeRequest(userBean, allRequests);

        //TODO: reload and update page
        return "/Friend/FriendRequest.xhtml?faces-redirect=true";
    }

}