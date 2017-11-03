package dao;

import java.sql.Connection;
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

    //Database Connection Info
    private final static String CONNECTION_STRING = "jdbc:derby://localhost:1527/MeltingPotLocal";
    private final static String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    private final static String USERNAME = "melt";
    private final static String PASSWORD = "pot";
    private final static String TABLE_NAME = "MELT.Users";

    public static int createProfile(UserBean aUserBean) {
        int rowCount = 0;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            String createString = "INSERT INTO " + TABLE_NAME + " VALUES('"
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
                    + "','" + aUserBean.getEmail()
                    + "','','')";

            PreparedStatement pstmt = conn.prepareStatement(createString);

            rowCount = pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (rowCount != 1) {
            System.err.println("ERROR: Message Insertion failed.");
        }
        return rowCount;
    }

    public static ArrayList<UserBean> findAll() {

        String query = "SELECT * FROM " + TABLE_NAME;
        ArrayList<UserBean> aStudentBeanCollection = selectProfilesFromDB(query);
        return aStudentBeanCollection;

    }

    //TODO: make prepared statments
    private static ArrayList<UserBean> selectProfilesFromDB(String query) {
        ArrayList<UserBean> aUserBeanCollection = new ArrayList();
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
                email = rs.getString("Email");
                friendRequest = rs.getString("FriendRequest");
                friendList = rs.getString("FriendList");

                // make a ProfileBean object out of the values
                aUserBean = new UserBean(username, password, name, age, gender, city, state, religion, race, politics, bio, email, friendRequest, friendList);
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

    public static UserBean findByUsername(String aName) {
        // if interested in matching wild cards, use: LIKE and '%" + aName + "%'";
        String query = "SELECT * FROM MELT.Users ";
        query += "WHERE UserName = '" + aName + "'";

        ArrayList<UserBean> aUserBean = selectProfilesFromDB(query);

        
        if (aUserBean.isEmpty()){
            return null;
        }

        return aUserBean.get(0);
    }

    public static ArrayList<UserBean> findByName(String name) {
        // if interested in matching wild cards, use: LIKE and '%" + aName + "%'";
        String query = "SELECT * FROM MELT.Users ";
        query += "WHERE Name = '" + name + "' ";

        ArrayList<UserBean> aStudentBeanCollection = selectProfilesFromDB(query);
        return aStudentBeanCollection;
    }

    public static int updateProfile(UserBean profile) {
        int rowCount = -1;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            String updateString = "UPDATE " + TABLE_NAME + " SET "
                    + "Name = '" + profile.getName() + "', "
                    + "Password = '" + profile.getPassword() + "', "
                    + "Email = '" + profile.getEmail() + "', "
                    + "City = '" + profile.getCity() + "', "
                    + "State = '" + profile.getState() + "', "
                    + "Age = " + profile.getAge() + ", "
                    + "Gender = '" + profile.getGender() + "', "
                    + "Religion = '" + profile.getReligion() + "', "
                    + "Race = '" + profile.getRace() + "', "
                    + "Bio = '" + profile.getBio() + "', "
                    + "Politics = '" + profile.getPolitics() + "', "
                    + "FriendRequest = '" + profile.getFriendRequest() + "', "
                    + "FriendList = '" + profile.getFriendList() + "' "
                    + "WHERE UserName = '" + profile.getUsername() + "'";

            PreparedStatement pstmt = conn.prepareStatement(updateString);

            rowCount = pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (rowCount != 1) {
            System.err.println("ERROR: Message Insertion failed.");
        }
        return rowCount;

    }

    public static ArrayList<UserBean> searchForUsers(String name, String gender, int age, String city,
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

        ArrayList<UserBean> aUserBeanCollection = selectProfilesFromDB(query);
        return aUserBeanCollection;
    }

}
