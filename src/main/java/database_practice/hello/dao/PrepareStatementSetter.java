package database_practice.hello.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PrepareStatementSetter {
    void setter(PreparedStatement pstmt) throws SQLException;
}
