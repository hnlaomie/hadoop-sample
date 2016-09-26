package com.github.sample.presto;

import com.github.sample.util.TestUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

/**
 * @author <a href="mailto:lichunhui@adwo.com">李春辉</a>
 * @date 2016-9-23
 */
public class PrestoTest {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.facebook.presto.jdbc.PrestoDriver";
    static final String DB_URL = "jdbc:presto://localhost:8090/cassandra/dmp";

    //  Database credentials
    static final String USER = "";
    static final String PASS = "";

    public static void main(String[] args) throws URISyntaxException {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, "test", null);

            //STEP 4: Execute a query
            stmt = conn.createStatement();
            String sql;
            sql = "select * from admaster_user_labels limit 10";
            ResultSet rs = stmt.executeQuery(sql);

            TestUtil.printRs(rs);

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
