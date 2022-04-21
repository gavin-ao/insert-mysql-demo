package mysql.insert.nat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by aoyonggang on 2022/4/18.
 */
public class PrepareStatementDemo {
    public static void main(String[] args) throws Exception {
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://3g.19:3306/eBu4siness" +
                "?useServerPrepStmts=true&cachePreStmts=true";
        String username="root";
        String password="1m";
        Class.forName(driverClassName);
        Connection conn = DriverManager.getConnection(url, username, password);
        String sql = "insert into `user`(id, name, age, sex) values(?, 'axax', 23, 1)";
        System.out.println("task is begin...");
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //normalinsert(pstmt);
        batchinsert(pstmt, 1000);
        conn.close();


    }

    public static void normalinsert(PreparedStatement pstmt) throws SQLException {

        long begin = System.currentTimeMillis();
        for (int i = 1; i< 10001; i++) {
            pstmt.setInt(1, i);

            pstmt.execute();
        }
        pstmt.close();
        System.out.println("preparenormalinsert Time Elcapsed: " + (System.currentTimeMillis() - begin) / 1000 + "S.");
    }



    public static void batchinsert(PreparedStatement pstmt,  int packagecount) throws SQLException {

        long begin = System.currentTimeMillis();
        for (int i = 1; i< 10001; i++) {
            pstmt.setInt(1, i);
            pstmt.addBatch();
            if ((i % packagecount) == 0) {
                pstmt.executeBatch();
                pstmt.clearBatch();
            }
        }
        pstmt.close();
        System.out.println("batchinsert Time Elcapsed: " + (System.currentTimeMillis() - begin) / 1000 + "S.");
    }
}
