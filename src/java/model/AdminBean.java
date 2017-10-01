package model;


/**
 *
 * @author Andrew Amen
 */
public class AdminBean{
    
    private String username;
    private String password;
    private String confirm;
    private String name;
    private String reviews;
    
    public AdminBean(){
    }

    public AdminBean(String username, String password, String name, String reviews) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.reviews = reviews;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }



    
}

