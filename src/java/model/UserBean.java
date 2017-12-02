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
    private String email;
    private String friendRequest;
    private String friendList;
    private boolean banned;

    public UserBean() {
    }

    public UserBean(String username, String password, String name, int age, String gender,
            String city, String state, String religion, String race, String politics,
            String bio, String email, boolean banned, String friendRequest, String friendList) {
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
        this.email = email;
        this.banned = banned;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
    
    
    public static class UserBeanBuilder
   {
      private String nestedUsername;
      private String nestedPassword;
      private String nestedConfirm;
      private String nestedName;
      private int nestedAge;
      private String nestedGender;
      private String nestedCity;
      private String nestedState;
      private String nestedReligion;
      private String nestedRace;
      private String nestedPolitics;
      private String nestedBio;
      private String nestedEmail;
      private String nestedFriendRequest;
      private String nestedFriendList;
      private boolean nestedBanned;
      

      public UserBeanBuilder(
         final String newUsername,
         final String newPassword,
         final String newBio) 
      {
         this.nestedUsername = newUsername;
         this.nestedPassword = newPassword;
         this.nestedBio = newBio;
      }

      public UserBeanBuilder username(String newUsername)
      {
         this.nestedUsername = newUsername;
         return this;
      }

      public UserBeanBuilder password(String newPassword)
      {
         this.nestedPassword = newPassword;
         return this;
      }

      public UserBeanBuilder confirm(String newConfirm)
      {
         this.nestedConfirm = newConfirm;
         return this;
      }

      public UserBeanBuilder name(String newName)
      {
         this.nestedName = newName;
         return this;
      }

      public UserBeanBuilder age(int newAge)
      {
         this.nestedAge = newAge;
         return this;
      }

      public UserBeanBuilder gender(String newGender)
      {
         this.nestedGender = newGender;
         return this;
      }

      public UserBeanBuilder city(String newCity)
      {
         this.nestedCity = newCity;
         return this;
      }

      public UserBeanBuilder state(String newState)
      {
         this.nestedState = newState;
         return this;
      }

      public UserBeanBuilder religion(String newReligion)
      {
         this.nestedReligion = newReligion;
         return this;
      }

      public UserBeanBuilder politics(String newPolitics)
      {
         this.nestedPolitics = newPolitics;
         return this;
      }

      public UserBeanBuilder bio(String newBio)
      {
         this.nestedBio = newBio;
         return this;
      }
      
      public UserBeanBuilder email(String newEmail)
      {
         this.nestedEmail = newEmail;
         return this;
      }
      public UserBeanBuilder friendRequest(String newFriendRequest)
      {
         this.nestedFriendRequest = newFriendRequest;
         return this;
      }
      public UserBeanBuilder friendLIst(String newFriendList)
      {
         this.nestedFriendList = newFriendList;
         return this;
      }
      public UserBeanBuilder banned(boolean newBanned)
      {
         this.nestedBanned = newBanned;
         return this;
      }
      

      public UserBean createUserBear()
      {
         return new UserBean(
            nestedUsername, nestedPassword,
            nestedName, nestedAge, nestedGender, nestedCity, 
            nestedState, nestedReligion, nestedRace, nestedPolitics, nestedBio,
            nestedEmail, nestedBanned, nestedFriendRequest, nestedFriendList);
      }
   } 
}
