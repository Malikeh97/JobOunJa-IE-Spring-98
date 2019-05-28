package datalayer;



import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * do not reinvent the wheel!!!
 *
 * you can use DBCP or other libraries.
 *
 * @see <a href="https://www.baeldung.com/java-connection-pooling">A Simple Guide to Connection Pooling in Java</a>
 *
 * */
public class DBCPDBConnectionPool {

    private static BasicDataSource ds = new BasicDataSource();
    private final static String dbURL = "jdbc:mysql://db:3306/jobOunJa?useUnicode=yes&characterEncoding=UTF-8";

    static {
        System.out.println(System.getenv().get("test"));
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(dbURL);
        ds.setUsername("root");
        ds.setPassword("password");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void close() throws SQLException {
        ds.close();
    }

    private DBCPDBConnectionPool(){ }
}
