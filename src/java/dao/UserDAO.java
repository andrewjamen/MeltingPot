package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.UserBean;

/**
 *
 * @author Andrew Amen
 */
public class UserDAO {

    public int createProfile(UserBean aUserBean) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/MeltingPotLocal";
            Connection DBConn = DriverManager.getConnection(myDB, "melt", "pot");

            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO MELT.Users VALUES ('"
                    + aUserBean.getUsername()
                    + "','" + aUserBean.getPassword()
                    + "','" + aUserBean.getName()
                    + "', " + aUserBean.getAge()
                    + ",'" + aUserBean.getGender()
                    + "','" + aUserBean.getCity()
                    + "','" + aUserBean.getState()
                    + "','" + aUserBean.getReligion()
                    + "','" + aUserBean.getRace()
                    + "','" + aUserBean.getPolitics()
                    + "','" + aUserBean.getBio()
                    + "'," + aUserBean.getRating()
                    + ",'" + aUserBean.getEmail()
                    + "','','','')";

            rowCount = stmt.executeUpdate(insertString);
            System.out.println("insert string =" + insertString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;
    }

    public ArrayList findAll() {

        String query = "SELECT * FROM MELT.Users";
        ArrayList aStudentBeanCollection = selectProfilesFromDB(query);
        return aStudentBeanCollection;

    }

    private ArrayList selectProfilesFromDB(String query) {
        ArrayList aUserBeanCollection = new ArrayList();
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/MeltingPotLocal";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "melt", "pot");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String username, password, email, city, state, name, gender, religion,
                    race, politics, bio, messages, friendRequest, friendList;
            int age;
            double rating;
            UserBean aUserBean;
            while (rs.next()) {
                // 1. if a float (say PRICE) is to be retrieved, use rs.getFloat("PRICE");
                // 2. Instead of using column name, can alternatively use: rs.getString(1); // not 0
                username = rs.getString("UserName");
                password = rs.getString("Password");
                name = rs.getString("Name");
                age = rs.getInt("Age");
                gender = rs.getString("gender");
                city = rs.getString("City");
                state = rs.getString("State");
                religion = rs.getString("Religion");
                race = rs.getString("Race");
                politics = rs.getString("Politics");
                bio = rs.getString("Bio");
                rating = rs.getDouble("Rating");
                email = rs.getString("Email");
                messages = rs.getString("Messages");
                friendRequest = rs.getString("FriendRequest");
                friendList = rs.getString("FriendList");

                // make a ProfileBean object out of the values
                aUserBean = new UserBean(username, password, name, age, gender, city, state, religion, race, politics, bio, rating, email, messages, friendRequest, friendList);
                // add the newly created object to the collection
                aUserBeanCollection.add(aUserBean);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return aUserBeanCollection;
    }

    public ArrayList findByUsername(String aName) {
        // if interested in matching wild cards, use: LIKE and '%" + aName + "%'";
        String query = "SELECT * FROM MELT.Users ";
        query += "WHERE UserName = '" + aName + "'";

        ArrayList aUserBeanCollection = selectProfilesFromDB(query);
        return aUserBeanCollection;
    }

    public ArrayList findByName(String name) {
        // if interested in matching wild cards, use: LIKE and '%" + aName + "%'";
        String query = "SELECT * FROM MELT.Users ";
        query += "WHERE Name = '" + name + "' ";

        ArrayList aStudentBeanCollection = selectProfilesFromDB(query);
        return aStudentBeanCollection;
    }

    public int updateProfile(UserBean profile) {
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/MeltingPotLocal";
            DBConn = DriverManager.getConnection(myDB, "melt", "pot");

            String updateString;
            Statement stmt = DBConn.createStatement();

            updateString = "UPDATE MELT.Users SET "
                    + "Name = '" + profile.getName() + "', "
                    + "Password = '" + profile.getPassword() + "', "
                    + "Email = '" + profile.getEmail() + "', "
                    + "City = '" + profile.getCity() + "', "
                    + "State = '" + profile.getState() + "', "
                    + "Age = " + profile.getAge() + ", "
                    + "Rating = " + profile.getRating() + ", "
                    + "Gender = '" + profile.getGender() + "', "
                    + "Religion = '" + profile.getReligion() + "', "
                    + "Race = '" + profile.getRace() + "', "
                    + "Bio = '" + profile.getBio() + "', "
                    + "Politics = '" + profile.getPolitics() + "', "
                    + "Messages = '" + profile.getMessages() + "', "
                    + "FriendRequest = '" + profile.getFriendRequest() + "', "
                    + "FriendList = '" + profile.getFriendList() + "' "
                    + "WHERE UserName = '" + profile.getUsername() + "'";
            rowCount = stmt.executeUpdate(updateString);
            System.out.println("updateString =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }

    public ArrayList searchForUsers(String name, String gender, int age, String city,
            String state, String religion, String race, String politics) {
        String query = "SELECT * FROM MELT.Users WHERE ";
        query += "LOWER(Name) LIKE '%" + name.toLowerCase() + "%' ";
        if (!gender.equals("Any")) {
            query += "AND Gender = '" + gender + "' ";
        }
        if (age != 0) {
            query += "AND Age = " + age;
        }
        if (!city.equals("")) {
            query += "AND City = '" + city + "' ";
        }
        if (!state.equals("Any")) {
            query += "AND State = '" + state + "' ";
        }
        if (!religion.equals("Any")) {
            query += "AND religion = '" + religion + "' ";
        }
        if (!race.equals("Any")) {
            query += "AND Race = '" + race + "' ";
        }
        if (!politics.equals("Any")) {
            query += "AND Politics = '" + politics + "' ";
        }

        ArrayList aUserBeanCollection = selectProfilesFromDB(query);
        return aUserBeanCollection;
    }

    //TODO: move to new MessageDAO class
    public int insertMessage(UserBean profile, String messages) {
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/MeltingPotLocal";
            DBConn = DriverManager.getConnection(myDB, "melt", "pot");

            String updateString = "";
            Statement stmt = DBConn.createStatement();

            updateString = "UPDATE MELT.Users SET "
                    + "Name = '" + profile.getName() + "', "
                    + "Password = '" + profile.getPassword() + "', "
                    + "Email = '" + profile.getEmail() + "', "
                    + "City = '" + profile.getCity() + "', "
                    + "State = '" + profile.getState() + "', "
                    + "Age = " + profile.getAge() + ", "
                    + "Rating = " + profile.getRating() + ", "
                    + "Gender = '" + profile.getGender() + "', "
                    + "Religion = '" + profile.getReligion() + "', "
                    + "Race = '" + profile.getRace() + "', "
                    + "Bio = '" + profile.getBio() + "', "
                    + "Politics = '" + profile.getPolitics() + "', "
                    + "Messages = '" + messages + "', "
                    + "FriendRequest = '" + profile.getFriendRequest() + "', "
                    + "FriendList = '" + profile.getFriendList() + "' "
                    + "WHERE UserName = '" + profile.getUsername() + "'";
            rowCount = stmt.executeUpdate(updateString);
            System.out.println("updateString =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }

    //TODO: move to new FriendDAO class
    public int sendFriendRequest(UserBean profile, String requests) {
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/MeltingPotLocal";
            DBConn = DriverManager.getConnection(myDB, "melt", "pot");

            String updateString = "";
            Statement stmt = DBConn.createStatement();

            updateString = "UPDATE MELT.Users SET "
                    + "Name = '" + profile.getName() + "', "
                    + "Password = '" + profile.getPassword() + "', "
                    + "Email = '" + profile.getEmail() + "', "
                    + "City = '" + profile.getCity() + "', "
                    + "State = '" + profile.getState() + "', "
                    + "Age = " + profile.getAge() + ", "
                    + "Rating = " + profile.getRating() + ", "
                    + "Gender = '" + profile.getGender() + "', "
                    + "Religion = '" + profile.getReligion() + "', "
                    + "Race = '" + profile.getRace() + "', "
                    + "Bio = '" + profile.getBio() + "', "
                    + "Politics = '" + profile.getPolitics() + "', "
                    + "Messages = '" + profile.getMessages() + "', "
                    + "FriendRequest = '" + requests + "', "
                    + "FriendList = '" + profile.getFriendList() + "' "
                    + "WHERE UserName = '" + profile.getUsername() + "'";
            rowCount = stmt.executeUpdate(updateString);
            System.out.println("updateString =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }

    //TODO: move to new FriendDAO class
    public int addFriend(UserBean profile, String friends) {
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/MeltingPotLocal";
            DBConn = DriverManager.getConnection(myDB, "melt", "pot");

            String updateString = "";
            Statement stmt = DBConn.createStatement();

            updateString = "UPDATE MELT.Users SET "
                    + "Name = '" + profile.getName() + "', "
                    + "Password = '" + profile.getPassword() + "', "
                    + "Email = '" + profile.getEmail() + "', "
                    + "City = '" + profile.getCity() + "', "
                    + "State = '" + profile.getState() + "', "
                    + "Age = " + profile.getAge() + ", "
                    + "Rating = " + profile.getRating() + ", "
                    + "Gender = '" + profile.getGender() + "', "
                    + "Religion = '" + profile.getReligion() + "', "
                    + "Race = '" + profile.getRace() + "', "
                    + "Bio = '" + profile.getBio() + "', "
                    + "Politics = '" + profile.getPolitics() + "', "
                    + "Messages = '" + profile.getMessages() + "', "
                    + "FriendRequest = '" + profile.getFriendRequest() + "', "
                    + "FriendList = '" + friends + "' "
                    + "WHERE UserName = '" + profile.getUsername() + "'";
            rowCount = stmt.executeUpdate(updateString);
            System.out.println("updateString =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }

    //TODO: move to new FriendDAO class
    public int removeRequest(UserBean profile, String requests) {
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/MeltingPotLocal";
            DBConn = DriverManager.getConnection(myDB, "melt", "pot");

            String updateString = "";
            Statement stmt = DBConn.createStatement();

            updateString = "UPDATE MELT.Users SET "
                    + "Name = '" + profile.getName() + "', "
                    + "Password = '" + profile.getPassword() + "', "
                    + "Email = '" + profile.getEmail() + "', "
                    + "City = '" + profile.getCity() + "', "
                    + "State = '" + profile.getState() + "', "
                    + "Age = " + profile.getAge() + ", "
                    + "Rating = " + profile.getRating() + ", "
                    + "Gender = '" + profile.getGender() + "', "
                    + "Religion = '" + profile.getReligion() + "', "
                    + "Race = '" + profile.getRace() + "', "
                    + "Bio = '" + profile.getBio() + "', "
                    + "Politics = '" + profile.getPolitics() + "', "
                    + "Messages = '" + profile.getMessages() + "', "
                    + "FriendRequest = '" + requests + "', "
                    + "FriendList = '" + profile.getFriendList() + "' "
                    + "WHERE UserName = '" + profile.getUsername() + "'";
            rowCount = stmt.executeUpdate(updateString);
            System.out.println("updateString =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }
}
