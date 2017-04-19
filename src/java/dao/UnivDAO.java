
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.StudentBean;
import model.UnivBean;

/**
 *
 * @author Andrew Amen
 */
public class UnivDAO {

    public int createProfile(UnivBean aUnivBean) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String insertString;
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO ITKSTU.Universities VALUES ('"
                    + aUnivBean.getUsername()
                    + "','" + aUnivBean.getPassword()
                    + "','" + aUnivBean.getName()
                    + "','" + aUnivBean.getEmail()
                    + "','" + aUnivBean.getAddress()
                    + "','" + aUnivBean.getCity()
                    + "','" + aUnivBean.getState()
                    + "','" + aUnivBean.getAvgAct()
                    + "','" + aUnivBean.getAvgGpa()
                    + "')";

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

        String query = "SELECT * FROM ITKSTU.Universities";
        ArrayList aStudentBeanCollection = selectProfilesFromDB(query);
        return aStudentBeanCollection;

    }

    private ArrayList selectProfilesFromDB(String query) {
        ArrayList aUnivBeanCollection = new ArrayList();
        Connection DBConn = null;
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:derby://localhost:1527/Project353";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String name, userName, password, email, address, city, state, avgAct, avgGpa;
            UnivBean aUnivBean;
            while (rs.next()) {
                // 1. if a float (say PRICE) is to be retrieved, use rs.getFloat("PRICE");
                // 2. Instead of using column name, can alternatively use: rs.getString(1); // not 0
                name = rs.getString("Name");
                userName = rs.getString("UserName");
                password = rs.getString("Password");
                email = rs.getString("Email");
                address = rs.getString("Address");
                city = rs.getString("City");
                state = rs.getString("State");
                avgAct = rs.getString("AvgACT");
                avgGpa = rs.getString("AvgGPA");


                // make a ProfileBean object out of the values
                aUnivBean = new UnivBean(name, userName, password, email, address, city, state, avgAct, avgGpa);
                // add the newly created object to the collection
                aUnivBeanCollection.add(aUnivBean);
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
        return aUnivBeanCollection;
    }

    public ArrayList findByUserName(String aName) {
        // if interested in matching wild cards, use: LIKE and '%" + aName + "%'";
        String query = "SELECT * FROM ITKSTU.Universities ";
        query += "WHERE UserName = '" + aName + "'";

        ArrayList aUnivBeanCollection = selectProfilesFromDB(query);
        return aUnivBeanCollection;
    }

    public int updateProfile(UnivBean pro) {
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        int rowCount = 0;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String updateString;
            Statement stmt = DBConn.createStatement();

            // SQL UPDATE Syntax [http://www.w3schools.com]:
            // UPDATE table_name
            // SET column1=value, column2=value2,...
            // WHERE some_column=some_value
            // Note: Notice the WHERE clause in the UPDATE syntax. The WHERE clause specifies which record or records that should be updated. If you omit the WHERE clause, all records will be updated!
            updateString = "UPDATE ITKSTU.Universities SET "
                    + "Name = '" + pro.getName() + "', "
                    + "Password = '" + pro.getPassword() + "', "
                    + "Email = '" + pro.getEmail() + "', "
                    + "Address = '" + pro.getAddress() + "', "
                    + "City = '" + pro.getCity() + "' "
                    + "State = '" + pro.getState() + "' "
                    + "AvgACT = '" + pro.getAvgAct() + "' "
                    + "AvgGPA = '" + pro.getAvgGpa() + "' "
                    + "WHERE UserName = '" + pro.getUsername() + "'";
            rowCount = stmt.executeUpdate(updateString);
            System.out.println("updateString =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }
    
    //TODO: not Working
    public boolean findUser(UnivBean aUnivBean) {
        Connection DBConn = null;
        boolean result = false;
        try {
            String myDB = "jdbc:derby://localhost:1527/Project353";
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            String sqlStr = "SELECT * FROM Project353.Universities WHERE username = ? and password = ?";
            PreparedStatement stmt = DBConn.prepareStatement(sqlStr);
            stmt.setString(1, aUnivBean.getUsername());
            stmt.setString(2, aUnivBean.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = true;
                aUnivBean.setUsername(rs.getString("Username"));
                aUnivBean.setPassword(rs.getString("Password"));
                aUnivBean.setName(rs.getString("Name"));
                aUnivBean.setAvgGpa(rs.getString("AvgGPA"));
                aUnivBean.setEmail(rs.getString("Email"));
                aUnivBean.setCity(rs.getString("City"));
                aUnivBean.setState(rs.getString("State"));
                aUnivBean.setAddress(rs.getString("Address"));
                aUnivBean.setAvgAct(rs.getString("AvgACT"));


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
        return result;
    }
}