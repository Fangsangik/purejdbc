package database_practice.hello.dao;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Connection과 관련된 부분 
@Getter
public class ConnectionManger {

    public static final String DB_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:tcp://localhost/~/jdbc-practice";
    public static final int MAX_POOL_SIZE = 40;

    private static final DataSource ds;

    //ConnectionPool을 하나만 갖도록 설정
    static {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        //얼만큼 Connection을 갖을지
        //높게 잡는다 해서 좋은 것은 아님
        dataSource.setMaximumPoolSize(MAX_POOL_SIZE);

        ds = dataSource;
    }

    public static Connection getConnection() {
        //dataSource로 부터 connection을 받아온다.
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }
}

