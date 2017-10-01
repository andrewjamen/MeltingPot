package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import model.UserBean;
import model.AdminBean;

/**
 *
 * @author Andrew Amen
 */
public class AdminDAO {

    final int actRange = 5;
    final int gpaRange = 1;
    final static HashMap<String, String> stateCodes = new HashMap<String, String>(50);

    public AdminDAO() {
        stateCodes.put("al", "alabama");
        stateCodes.put("ak", "alaska");
        stateCodes.put("az", "arizona");
        stateCodes.put("ar", "arkansas");
        stateCodes.put("ca", "california");
        stateCodes.put("co", "colorado");
        stateCodes.put("ct", "connecticut");
        stateCodes.put("de", "delaware");
        stateCodes.put("fl", "florida");
        stateCodes.put("ga", "georgia");
        stateCodes.put("hi", "hawaii");
        stateCodes.put("id", "idaho");
        stateCodes.put("il", "illinois");
        stateCodes.put("in", "indiana");
        stateCodes.put("ia", "iowa");
        stateCodes.put("ks", "kansas");
        stateCodes.put("ky", "kentucky");
        stateCodes.put("la", "louisiana");
        stateCodes.put("me", "maine");
        stateCodes.put("md", "maryland");
        stateCodes.put("ma", "massachusetts");
        stateCodes.put("mi", "michigan");
        stateCodes.put("mn", "minnesota");
        stateCodes.put("ms", "mississippi");
        stateCodes.put("mo", "missouri");
        stateCodes.put("mt", "montana");
        stateCodes.put("ne", "nebraska");
        stateCodes.put("nv", "nevada");
        stateCodes.put("nh", "new hampshire");
        stateCodes.put("nj", "new jersey");
        stateCodes.put("nm", "new mexico");
        stateCodes.put("ny", "new york");
        stateCodes.put("nc", "north carolina");
        stateCodes.put("nd", "north dakota");
        stateCodes.put("oh", "ohio");
        stateCodes.put("ok", "oklahoma");
        stateCodes.put("or", "oregon");
        stateCodes.put("pa", "pennsylvania");
        stateCodes.put("ri", "rhode island");
        stateCodes.put("sc", "south carolina");
        stateCodes.put("sd", "south dakota");
        stateCodes.put("tn", "tennessee");
        stateCodes.put("tx", "texas");
        stateCodes.put("ut", "utah");
        stateCodes.put("vt", "vermont");
        stateCodes.put("va", "virginia");
        stateCodes.put("wa", "washington");
        stateCodes.put("wv", "west virginia");
        stateCodes.put("wi", "wisconsin");
        stateCodes.put("wy", "wyoming");
    }

    public int createProfile(AdminBean aAdminBean) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/MeltingPotDB";
            Connection DBConn = DriverManager.getConnection(myDB, "admin", "melt");

            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO APP.Admins VALUES ('"
                    + aAdminBean.getUsername()
                    + "','" + aAdminBean.getPassword()
                    + "','" + aAdminBean.getName()
                    + "','')";

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

        String query = "SELECT * FROM APP.Admins";
        ArrayList aAdminBeanCollection = selectProfilesFromDB(query);
        return aAdminBeanCollection;

    }

    private ArrayList selectProfilesFromDB(String query) {
        ArrayList aAdminBeanCollection = new ArrayList();
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/MeltingPotDB";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "admin", "melt");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String name, username, password, reviews;
            AdminBean aAdminBean;
            while (rs.next()) {
                // 1. if a float (say PRICE) is to be retrieved, use rs.getFloat("PRICE");
                // 2. Instead of using column name, can alternatively use: rs.getString(1); // not 0
                name = rs.getString("Name");
                username = rs.getString("UserName");
                password = rs.getString("Password");
                reviews = rs.getString("Reviews");

                // make a ProfileBean object out of the values
                aAdminBean = new AdminBean(username, password, name, reviews);
                // add the newly created object to the collection
                aAdminBeanCollection.add(aAdminBean);
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
        return aAdminBeanCollection;
    }

    public ArrayList findByUserName(String aName) {
        // if interested in matching wild cards, use: LIKE and '%" + aName + "%'";
        String query = "SELECT * FROM APP.Admins ";
        query += "WHERE UserName = '" + aName + "'";

        ArrayList aAdminBeanCollection = selectProfilesFromDB(query);
        return aAdminBeanCollection;
    }

    public ArrayList findByName(String aName) {
        // if interested in matching wild cards, use: LIKE and '%" + aName + "%'";
        String query = "SELECT * FROM APP.Admins ";
        query += "WHERE Name = '" + aName + "'";

        ArrayList aAdminBeanCollection = selectProfilesFromDB(query);
        return aAdminBeanCollection;
    }

    public int updateProfile(AdminBean profile) {
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/MeltingPotDB";
            DBConn = DriverManager.getConnection(myDB, "admin", "melt");

            String updateString;
            Statement stmt = DBConn.createStatement();

            // SQL UPDATE Syntax [http://www.w3schools.com]:
            // UPDATE table_name
            // SET column1=value, column2=value2,...
            // WHERE some_column=some_value
            // Note: Notice the WHERE clause in the UPDATE syntax. The WHERE clause specifies which record or records that should be updated. If you omit the WHERE clause, all records will be updated!
            updateString = "UPDATE APP.Admins SET "
                    + "Name = '" + profile.getName() + "', "
                    + "Password = '" + profile.getPassword() + "', "
                    + "Reviews = '" + profile.getReviews() + "' " 
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
    
    public int insertRequest(AdminBean profile, String review) {
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/MeltingPotDB";
            DBConn = DriverManager.getConnection(myDB, "admin", "melt");

            String updateString;
            Statement stmt = DBConn.createStatement();

            // SQL UPDATE Syntax [http://www.w3schools.com]:
            // UPDATE table_name
            // SET column1=value, column2=value2,...
            // WHERE some_column=some_value
            // Note: Notice the WHERE clause in the UPDATE syntax. The WHERE clause specifies which record or records that should be updated. If you omit the WHERE clause, all records will be updated!
            updateString = "UPDATE APP.Admins SET "
                    + "Name = '" + profile.getName() + "', "
                    + "Password = '" + profile.getPassword() + "', "
                    + "Reviews = '" + review + "' "
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
    
    /**
    *  Converts from a given state code to a state name (returns original string if no match)
    */
    private String findState(String stateCode) {
        stateCode = stateCode.toLowerCase();
        if (stateCodes.containsKey(stateCode)) {
            return stateCodes.get(stateCode);
        }
        return stateCode;
    }
    
    

}