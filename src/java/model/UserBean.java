package model;

/**
 *
 * @author Andrew Amen
 */
public class UserBean {

    private String username;
    private String password;
    private String confirm;
    private String name;
    private int age;
    private String gender;
    private String city;
    private String state;
    private String religion;
    private String race;
    private String politics;
    private String bio;
    private double rating;
    private String email;
    private String messages;
    private String friendRequest;
    private String friendList;

    public UserBean() {
    }

    public UserBean(String username, String password, String name, int age, String gender,
            String city, String state, String religion, String race, String politics,
            String bio, double rating, String email, String messages, String friendRequest, String friendList) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.city = city;
        this.state = state;
        this.religion = religion;
        this.race = race;
        this.politics = politics;
        this.bio = bio;
        this.rating = rating;
        this.email = email;
        this.messages = messages;
        this.friendRequest = friendRequest;
        this.friendList = friendList;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(String friendRequest) {
        this.friendRequest = friendRequest;
    }

    public String getFriendList() {
        return friendList;
    }

    public void setFriendList(String friendList) {
        this.friendList = friendList;
    }
    
    
}
