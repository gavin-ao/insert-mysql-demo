package mysql.insert.localfile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by aoyonggang on 2022/4/18.
 */
public class LoadDataDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        System.out.println("The task is begin...");
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://39.97.173.219/eBusiness",
                "root", "123XKB!@#.com");
        Statement stmt = conn.createStatement();
        long begin = System.currentTimeMillis();
        String loadDataSQL = "load data LOCAL INFILE '/Users/aoyonggang/Downloads/1million.txt' \n" +
                "into table `user` fields TERMINATED by ',' lines TERMINATED by '\\n'";
        stmt.execute(loadDataSQL);
        stmt.close();
        conn.close();
        System.out.println("Time Elcapsed: " + (System.currentTimeMillis() - begin) / 1000 + "S.");//about 7s handle 1 million records
        System.out.println("The task is done.");
    }
}
