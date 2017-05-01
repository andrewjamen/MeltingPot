package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
//import model.StudentBean;
import model.UnivBean;

/**
 *
 * @author Andrew Amen
 */
public class UnivDAO {

    final int actRange = 5;
    final int gpaRange = 1;
    final static HashMap<String, String> stateCodes = new HashMap<String, String>(50);
    private Connection DBConn;

    public UnivDAO() {
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
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/cmohrfe_Sp2018_Universities";
            DBConn = DriverManager.getConnection(myDB, "itkstu", "student");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public int createProfile(UnivBean aUnivBean) {
        /**
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        */

        int rowCount = 0;
        try {
            //String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/cmohrfe_Sp2018_Universities";
            //Connection DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            /*
            Statement stmt = DBConn.createStatement();
            insertString = "INSERT INTO APP.Universities VALUES ('"
                    + aUnivBean.getUsername()
                    + "','" + aUnivBean.getPassword()
                    + "','" + aUnivBean.getName()
                    + "','" + aUnivBean.getEmail()
                    + "','" + aUnivBean.getAddress()
                    + "','" + aUnivBean.getCity()
                    + "','" + aUnivBean.getState()
                    + "'," + aUnivBean.getAvgAct()
                    + "," + aUnivBean.getAvgGpa() + ",''"
                    + ")";
            */
            String insertString = "INSERT INTO APP.Universities "
                                + "(USERNAME, PASSWORD, NAME, EMAIL, ADDRESS, CITY, STATE, AVGACT, AVGGPA, REQUESTS)"
                                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
            
            PreparedStatement pstmt = DBConn.prepareStatement(insertString);
            
            pstmt.setString(1, aUnivBean.getUsername());
            pstmt.setString(2, aUnivBean.getPassword());
            pstmt.setString(3, aUnivBean.getName());
            pstmt.setString(4, aUnivBean.getEmail());
            pstmt.setString(5, aUnivBean.getAddress());
            pstmt.setString(6, aUnivBean.getCity());
            pstmt.setString(7, aUnivBean.getState());
            pstmt.setInt(8, aUnivBean.getAvgAct());
            pstmt.setDouble(9, aUnivBean.getAvgGpa());
            pstmt.setString(10, "");

            rowCount = pstmt.executeUpdate();
            System.out.println("insert string =" + insertString);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;
    }

    public ArrayList findAll() {

        String query = "SELECT * FROM APP.Universities";
        PreparedStatement pstmt = null;
        try
        {
            pstmt = DBConn.prepareStatement(query);
        }
        catch (SQLException e)
        {
            System.err.println("ERROR: Problems with prepared statement");
            e.printStackTrace();
        }
                
        ArrayList aStudentBeanCollection = selectProfilesFromDB(pstmt);
        return aStudentBeanCollection;

    }

    private ArrayList selectProfilesFromDB(PreparedStatement ps) {
        ArrayList aUnivBeanCollection = new ArrayList();
        //Connection DBConn = null;
        try {
            //DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            // if doing the above in Oracle: DBHelper.loadDriver("oracle.jdbc.driver.OracleDriver");
            //String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/cmohrfe_Sp2018_Universities";
            // if doing the above in Oracle:  String myDB = "jdbc:oracle:thin:@oracle.itk.ilstu.edu:1521:ora478";
            //DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            //Statement stmt = DBConn.createStatement();
            ResultSet rs = ps.executeQuery();
            String name, userName, password, email, address, city, state, request;
            int avgAct;
            double avgGpa;
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
                avgAct = rs.getInt("AvgACT");
                avgGpa = rs.getDouble("AvgGPA");
                request = rs.getString("Requests");

                // make a ProfileBean object out of the values
                aUnivBean = new UnivBean(name, userName, password, email, address, city, findState(state), avgAct, avgGpa, request);
                // add the newly created object to the collection
                aUnivBeanCollection.add(aUnivBean);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        /*
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        */
        return aUnivBeanCollection;
    }

    public ArrayList findByUserName(String aName) {
        // if interested in matching wild cards, use: LIKE and '%" + aName + "%'";
        /*
        String query = "SELECT * FROM APP.Universities ";
        query += "WHERE UserName = '" + aName + "'";
        */
        
        String query = "SELECT * FROM APP.Universities "
                     + "    WHERE UserName = ?";
        
        PreparedStatement pstmt = null;
        
        try
        {
            pstmt = DBConn.prepareStatement(query);
            pstmt.setString(1, aName);
        }
        catch (SQLException e)
        {
            System.err.println("ERROR: Problems with prepared statement");
            e.printStackTrace();
        }
        
        ArrayList aUnivBeanCollection = selectProfilesFromDB(pstmt);
        return aUnivBeanCollection;
    }

    public ArrayList findByName(String aName) {
        // if interested in matching wild cards, use: LIKE and '%" + aName + "%'";
        String query = "SELECT * FROM APP.Universities ";
        query += "WHERE Name = '" + aName + "'";
        
        PreparedStatement pstmt = null;
        
        try
        {
            pstmt = DBConn.prepareStatement(query);
            pstmt.setString(1, aName);
        }
        catch (SQLException e)
        {
            System.err.println("ERROR: Problems with prepared statement");
            e.printStackTrace();
        }

        ArrayList aUnivBeanCollection = selectProfilesFromDB(pstmt);
        return aUnivBeanCollection;
    }

    public int updateProfile(UnivBean pro) {
        /*
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        */
        int rowCount = 0;
        try {
            //String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/cmohrfe_Sp2018_Universities";
            //DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String updateString = "UPDATE APP.Universities SET "
                                + "Name = ?, "
                                + "Password = ?, "
                                + "Email = ?, "
                                + "Address = ?, "
                                + "City = ?, "
                                + "State = ?, "
                                + "AvgACT = ?, "
                                + "AvgGPA = ?, "
                                + "Requests = ? "
                                + "WHERE UserName = ?" ;
            PreparedStatement pstmt = DBConn.prepareStatement(updateString);
            pstmt.setString(1, pro.getName());
            pstmt.setString(2, pro.getPassword());
            pstmt.setString(3, pro.getEmail());
            pstmt.setString(4, pro.getAddress());
            pstmt.setString(5, pro.getCity());
            pstmt.setString(6, pro.getState());
            pstmt.setInt(7, pro.getAvgAct());
            pstmt.setDouble(8, pro.getAvgGpa());
            pstmt.setString(9, pro.getRequest());
            pstmt.setString(10, pro.getUsername());

            // SQL UPDATE Syntax [http://www.w3schools.com]:
            // UPDATE table_name
            // SET column1=value, column2=value2,...
            // WHERE some_column=some_value
            // Note: Notice the WHERE clause in the UPDATE syntax. The WHERE clause specifies which record or records that should be updated. If you omit the WHERE clause, all records will be updated!
            /*
            updateString = "UPDATE APP.Universities SET "
                    + "Name = '" + pro.getName() + "', "
                    + "Password = '" + pro.getPassword() + "', "
                    + "Email = '" + pro.getEmail() + "', "
                    + "Address = '" + pro.getAddress() + "', "
                    + "City = '" + pro.getCity() + "', "
                    + "State = '" + pro.getState() + "', "
                    + "AvgACT = " + pro.getAvgAct() + ", "
                    + "AvgGPA = " + pro.getAvgGpa() + ", "
                    + "Requests = '" + pro.getRequest() + "' " 
                    + "WHERE UserName = '" + pro.getUsername() + "'";
            */
            rowCount = pstmt.executeUpdate();
            System.out.println("updateString =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }
    
    public int insertRequest(UnivBean pro, String request) {
        /*
        Connection DBConn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        */
        int rowCount = 0;
        try {
            //String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/cmohrfe_Sp2018_Universities";
            //DBConn = DriverManager.getConnection(myDB, "itkstu", "student");

            String updateString = "UPDATE APP.Universities SET "
                                + "Name = ?, "
                                + "Password = ?, "
                                + "Email = ?, "
                                + "Address = ?, "
                                + "City = ?, "
                                + "State = ?, "
                                + "AvgACT = ?, "
                                + "AvgGPA = ?, "
                                + "Requests = ? "
                                + "WHERE UserName = ?;" ;
            PreparedStatement pstmt = DBConn.prepareStatement(updateString);
            pstmt.setString(1, pro.getName());
            pstmt.setString(2, pro.getPassword());
            pstmt.setString(3, pro.getEmail());
            pstmt.setString(4, pro.getAddress());
            pstmt.setString(5, pro.getCity());
            pstmt.setString(6, pro.getState());
            pstmt.setInt(7, pro.getAvgAct());
            pstmt.setDouble(8, pro.getAvgGpa());
            pstmt.setString(9, pro.getRequest());
            pstmt.setString(10, pro.getUsername());

            // SQL UPDATE Syntax [http://www.w3schools.com]:
            // UPDATE table_name
            // SET column1=value, column2=value2,...
            // WHERE some_column=some_value
            // Note: Notice the WHERE clause in the UPDATE syntax. The WHERE clause specifies which record or records that should be updated. If you omit the WHERE clause, all records will be updated!
            updateString = "UPDATE APP.Universities SET "
                    + "Name = '" + pro.getName() + "', "
                    + "Password = '" + pro.getPassword() + "', "
                    + "Email = '" + pro.getEmail() + "', "
                    + "Address = '" + pro.getAddress() + "', "
                    + "City = '" + pro.getCity() + "', "
                    + "State = '" + pro.getState() + "', "
                    + "AvgACT = " + pro.getAvgAct() + ", "
                    + "AvgGPA = " + pro.getAvgGpa() + ", "
                    + "Requests = '" + request + "' "
                    + "WHERE UserName = '" + pro.getUsername() + "'";
            rowCount = pstmt.executeUpdate();
            System.out.println("updateString =" + updateString);
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }
    
    //Not tested
    public boolean findUser(UnivBean aUnivBean) {
        //Connection DBConn = null;
        boolean result = false;
        try {
            //String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/cmohrfe_Sp2018_Universities";
            //DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            //DBConn = DBHelper.connect2DB(myDB, "itkstu", "student");

            // With the connection made, create a statement to talk to the DB server.
            // Create a SQL statement to query, retrieve the rows one by one (by going to the
            // columns), and formulate the result string to send back to the client.
            String sqlStr = "SELECT * FROM APP.Universities WHERE username = ? and password = ?";
            PreparedStatement stmt = DBConn.prepareStatement(sqlStr);
            stmt.setString(1, aUnivBean.getUsername());
            stmt.setString(2, aUnivBean.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = true;
                aUnivBean.setUsername(rs.getString("Username"));
                aUnivBean.setPassword(rs.getString("Password"));
                aUnivBean.setName(rs.getString("Name"));
                aUnivBean.setAvgGpa(rs.getDouble("AvgGPA"));
                aUnivBean.setEmail(rs.getString("Email"));
                aUnivBean.setCity(rs.getString("City"));
                aUnivBean.setState(rs.getString("State"));
                aUnivBean.setAddress(rs.getString("Address"));
                aUnivBean.setAvgAct(rs.getInt("AvgACT"));

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

    /*
    author: jfoss
        Search for user based on given information (name, state, avgAct, avgGpa)
     */
    public ArrayList searchForUsers(String name, String state, int avgAct, double avgGpa) {
        /*
        String query = "SELECT * FROM APP.Universities ";
        query += "WHERE LOWER(Name) LIKE '%" + name.toLowerCase() + "%' ";
        */
        int count = 1;
        String query = "SELECT * FROM APP.Universities " +
                       " WHERE LOWER(Name) LIKE ? ";
        PreparedStatement pstmt = null;
        boolean st = false, act = false, gpa = false;
        
        try
        {
            //pstmt.setString(count++, "%" + name.toLowerCase() + "%");
            if (!state.equals("")) {
                query += "AND (LOWER(State) = ? OR LOWER(State) = ?) ";
                st = true;
                //pstmt.setString(count++, state.toLowerCase());                
                //pstmt.setString(count++, findState(state));
            }
            if (avgAct != 0) {
                query += "AND (AvgACT >= ? AND AvgACT <= ?) ";
                act = true;
                //pstmt.setInt(count++, (avgAct - actRange));                
                //pstmt.setInt(count++, (avgAct - actRange));
            }
            if (avgGpa != 0) {
                query += "AND (AvgGPA >= ? AND AvgGPA <= ?) ";
                gpa = true;
                //pstmt.setDouble(count++, (avgGpa - gpaRange));                
                //pstmt.setDouble(count++, (avgGpa - gpaRange));
            }
            pstmt = DBConn.prepareStatement(query);
            pstmt.setString(count++, "%" + name.toLowerCase() + "%");
            if (st) {
                pstmt.setString(count++, state.toLowerCase());                
                pstmt.setString(count++, findState(state));
            }
            if (act) {
                pstmt.setInt(count++, (avgAct - actRange));                
                pstmt.setInt(count++, (avgAct - actRange));
            }
            if (gpa) {
                pstmt.setDouble(count++, (avgGpa - gpaRange));                
                pstmt.setDouble(count++, (avgGpa - gpaRange));
            }
        }
        catch (SQLException e)
        {
            System.err.println("ERROR: Problems with prepared statement");
            e.printStackTrace();
        }
        
        /*
        if (!state.equals("")) {
            query += "AND (LOWER(State) = '" + state.toLowerCase() + "' OR LOWER(State) = '" + findState(state) + "') ";
        }
        if (avgAct != 0) {
            query += "AND (AvgACT >= " + (avgAct - actRange) + " AND AvgACT <= " + (avgAct + actRange) + ") ";
        }
        if (avgGpa != 0) {
            query += "AND (AvgGPA >= " + (avgGpa - gpaRange) + " AND AvgGPA <= " + (avgGpa + gpaRange) + ") ";
        }
        */

        ArrayList aUnivBeanCollection = selectProfilesFromDB(pstmt);
        return aUnivBeanCollection;
    }
    
    /*
    author: jfoss
        Search for user based on given information (name, state, avgAct, avgGpa)
        EXCEPT for a certain user
     */
    public ArrayList searchForUsersExcept(String excluded, String name, String state, int avgAct, double avgGpa) {
        String query = "SELECT * FROM APP.Universities ";
        int count = 1;
        PreparedStatement pstmt = null;
        boolean st = false, act = true, gpa = true;
        
        try
        {
            query += "WHERE LOWER(Name) LIKE ? ";
            if (!state.equals("")) {
                query += "AND (LOWER(State) = ? OR LOWER(State) = ?) ";
                st = true;
                //pstmt.setString(count++, state.toLowerCase());                
                //pstmt.setString(count++, findState(state));
            }
            if (avgAct != 0) {
                query += "AND (AvgACT >= ? AND AvgACT <= ?) ";
                act = true;
                //pstmt.setInt(count++, (avgAct - actRange));                
                //pstmt.setInt(count++, (avgAct - actRange));
            }
            if (avgGpa != 0) {
                query += "AND (AvgGPA >= ? AND AvgGPA <= ?) ";
                gpa = true;
                //pstmt.setDouble(count++, (avgGpa - gpaRange));                
                //pstmt.setDouble(count++, (avgGpa - gpaRange));
            }
            query += "AND Username NOT IN (?)";
            pstmt = DBConn.prepareStatement(query);
            pstmt.setString(count++, "%" + name.toLowerCase() + "%");
            if (st) {
                pstmt.setString(count++, state.toLowerCase());                
                pstmt.setString(count++, findState(state));
            }
            if (act) {
                pstmt.setInt(count++, (avgAct - actRange));                
                pstmt.setInt(count++, (avgAct - actRange));
            }
            if (gpa) {
                pstmt.setDouble(count++, (avgGpa - gpaRange));                
                pstmt.setDouble(count++, (avgGpa - gpaRange));
            }
            pstmt.setString(count++, excluded);
        }
        catch (SQLException e)
        {
            System.err.println("ERROR: Problems with prepared statement");
            e.printStackTrace();
        }
        /*
        query += "WHERE LOWER(Name) LIKE '%" + name.toLowerCase() + "%' ";
        if (!state.equals("")) {
            query += "AND (LOWER(State) = '" + state.toLowerCase() + "' OR LOWER(State) = '" + findState(state) + "') ";
        }
        if (avgAct != 0) {
            query += "AND (AvgACT >= " + (avgAct - actRange) + " AND AvgACT <= " + (avgAct + actRange) + ") ";
        }
        if (avgGpa != 0) {
            query += "AND (AvgGPA >= " + (avgGpa - gpaRange) + " AND AvgGPA <= " + (avgGpa + gpaRange) + ") ";
        }
        query += "AND Username NOT IN ('" + excluded + "')";
        */

        ArrayList aUnivBeanCollection = selectProfilesFromDB(pstmt);
        return aUnivBeanCollection;
    }

    /*
    author: jfoss
       Converts from a given state code to a state name (returns original string if no match)
     */
    private String findState(String stateCode) {
        stateCode = stateCode.toLowerCase();
        if (stateCodes.containsKey(stateCode)) {
            return stateCodes.get(stateCode);
        }
        return stateCode;
    }
    
    

}
