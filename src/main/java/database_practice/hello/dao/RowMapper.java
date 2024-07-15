package database_practice.hello.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {
    Object map(ResultSet rst) throws SQLException;
}
