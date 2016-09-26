package com.github.sample.hive;

/**
 * @author <a href="mailto:hnlaomie@hotmail.com">laomie</a>
 * @version V1
 * @date 2014-07-22 11:01
 */
import com.github.sample.util.TestUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class HiveTest {
    public static void main(String[] args) throws Exception {
        HiveTest sample = new HiveTest();
        sample.createCountry();
        //sample.createCity();
        //sample.select();
    }

    private Connection getConnection() throws Exception {
        // for hive 0.11 hiveserver2
        //String driverName = "org.apache.hive.jdbc.HiveDriver";
        //String url = "jdbc:hive2://192.168.56.101:10000/default";
        //String url = "jdbc:hive2://192.168.56.101:21050/;auth=noSasl";
        // for hive 0.14 hiveserver
        String driverName = "org.apache.hive.jdbc.HiveDriver";
        String url = "jdbc:hive2://localhost:10000/default";
        Class.forName(driverName);
        // laomie is hadoop user
        Connection conn = DriverManager.getConnection(url, "laomie", "");
        return conn;
    }

    public void select() throws Exception {
        String sql = "select a.country as `国家`, b.city as `城市` from country a, city b where a.country_id = b.country_id";
        //String sql = "select FROM_UNIXTIME(UNIX_TIMESTAMP('2014-07-01 13:33:06.012'), 'HH:mm:ss')";
        //Path file = Paths.get("f:/test.sql");
        //String sql = FileUtils.fileToString(file);
        //String sql = "select FROM_UNIXTIME(UNIX_TIMESTAMP())";
        //String sql1 = "create table time(id string, year int, quarter int, month int, day int)";
        //String sql = "select 时间.year year from time 时间";
        //String sql = "show tables";
        try (
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
        ) {
            TestUtil.printRs(rs);
        }
    }

    public void createCountry() throws Exception {
        String tableName = "country";
        String dataFile = TestUtil.getFilePath("hive/country.csv");
        String dropSql = "drop table if exists " + tableName;
        String createSql = "create table " + tableName + "(country_id int, country string, last_update timestamp) row format delimited fields terminated by '\t'";
        String loadSql = "load data local inpath '" + dataFile + "' overwrite into table " + tableName;
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
        ) {
            stmt.execute(dropSql);
            stmt.execute(createSql);
            stmt.execute(loadSql);
        }
        System.out.println("====== end of loading " + tableName + ".");
    }

    public void createCity() throws Exception {
        String tableName = "city";
        String dataFile = TestUtil.getFilePath("hive/city.csv");
        String dropSql = "drop table if exists " + tableName;
        String createSql = "create table " + tableName + "(city_id int, city string, country_id int, last_update timestamp) row format delimited fields terminated by '\t'";
        String loadSql = "load data local inpath '" + dataFile + "' overwrite into table " + tableName;
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
        ) {
            stmt.execute(dropSql);
            stmt.execute(createSql);
            stmt.execute(loadSql);
        }
        System.out.println("====== end of loading " + tableName + ".");
    }

}
