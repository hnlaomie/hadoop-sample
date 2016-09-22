package com.github.sample.util;

/**
 * @author <a href="mailto:hnlaomie@hotmail.com">laomie</a>
 * @version V1
 * @date 2014-11-30 11:11
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class TestUtil {
    public static void printRs(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = rsmd.getColumnLabel(i);
            System.out.print(columnName + "\t");
        }
        System.out.println();

        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                Object obj = rs.getObject(i);
                System.out.print(obj + "\t");
            }
            System.out.println();
        }
    }

    public static String getFilePath(String relativePath) throws Exception {
        URL url = TestUtil.class.getClassLoader().getResource(relativePath);
        Path path = Paths.get(url.toURI());
        File dir = path.toFile();
        String absolutePath = dir.getAbsolutePath();
        return absolutePath;
    }
}
