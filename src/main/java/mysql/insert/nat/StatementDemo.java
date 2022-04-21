package mysql.insert.nat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by aoyonggang on 2022/4/18.
 */
public class StatementDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("The task is begin...");
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://39.97.173.219/eBusiness",
                "root", "123XKB!@#.com");
        Statement stmt = conn.createStatement();

        //normalInsert(stmt);//about 360s handle 10000 records
        //batchInsert(stmt);//about 225s handle 10000 records
        multiValueInsert(stmt, 5000);//about 41s handle 1 million records
        stmt.close();
        conn.close();
        System.out.println("The task is done.");
    }

    public static void normalInsert(Statement stmt) throws SQLException {
        long begin = System.currentTimeMillis();
        String sql = "insert into `user`(id, name, age, sex) values(%s, 'axax', 23, 1)";
        for (int i = 1; i< 10001; i++) {
            String realsql = String.format(sql, i);

            stmt.execute(realsql);
        }
        System.out.println("normalinsert Time Elcapsed: " + (System.currentTimeMillis() - begin) / 1000 + "S.");
    }

    public static void batchInsert(Statement stmt) throws SQLException {
        long begin = System.currentTimeMillis();
        String sql = "insert into `user`(id, name, age, sex) values(%s, 'axax', 23, 1)";
        for (int i = 10001; i< 20001; i++) {
            String realsql = String.format(sql, i);
            stmt.addBatch(realsql);
            if ((i % 2000) == 0) {
                stmt.executeBatch();
                stmt.clearBatch();
            }
        }
        System.out.println("batchinsert Time Elcapsed: " + (System.currentTimeMillis() - begin) / 1000 + "S.");
    }

    public static void multiValueInsert(Statement stmt, int packagecount) throws SQLException {
        long begin = System.currentTimeMillis();
        String sql_prefix = "insert into `user`(id, name, age, sex) values";
        String sql = "(%s, 'axax', 23, 1)";
        for (int i = 1; i< 1000001; i++) {
            String realsql = String.format(sql, i);
            sql_prefix = sql_prefix + realsql;
            if ((i % packagecount) == 0) {
                stmt.execute(sql_prefix);
                sql_prefix = "insert into `user`(id, name, age, sex) values";
            } else {
                sql_prefix = sql_prefix + ",";
            }
        }
        System.out.println("multivalueinsert Time Elcapsed: " + (System.currentTimeMillis() - begin) / 1000 + "S.");
    }
}
