package controller;

import dao.UserDAO;
import dao.AdminDAO;
import java.util.ArrayList;
import java.util.Date;
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
public class AdminReviewsController {

    AdminBean adminBean;
    ArrayList<String> reviews = new ArrayList();

    public AdminReviewsController() {
        adminBean = new AdminBean();
    }

    public AdminBean getAdminBean() {
        return adminBean;
    }

    public void setAdminBean(AdminBean univBean) {
        this.adminBean = univBean;
    }

    public ArrayList<String> getReviews() {
        string2list(adminBean.getReviews());
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public String goToPage(String username) {
        ArrayList<AdminBean> tmp = (new AdminDAO()).findByUserName(username);
        adminBean = tmp.get(0);
        return "UnivNotifications.xhtml?faces-redirect=true";
    }

    public int number() {
        int length = 0;
        ArrayList<String> temp = new ArrayList();
        temp = reviews;
        reviews = getReviews();
        length = temp.size();
        reviews = temp;

        return length;
    }

    public ArrayList<String> string2list(String request) {

        if (request.equals("")) {
            return reviews;
        }
        if (reviews.size() > 0) {
            String lastNotif = reviews.get(reviews.size() - 1);
            int index = request.lastIndexOf(lastNotif);

            request = request.substring(index + lastNotif.length());
        }

        String lines[] = request.split("\\r?\\n");

        if (!lines[0].equals("")) {
            for (int i = 0; i < lines.length; i++) {
                reviews.add(lines[i]);
            }
        }

        return reviews;
    }

    public String getSender(String review) { 
        
        int index = review.indexOf(":");
        String name = review.substring(0, index-1);


        ArrayList<UserBean> tmp = (new UserDAO()).findByName(name);
        UserBean theBean = tmp.get(0);

        String sender = theBean.getUsername();

        return sender;
    }

}
