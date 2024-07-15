package database_practice.hello.dao;

import database_practice.hello.JdbcTemplate;

import java.sql.*;

public class UserDao {

    public void create(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "insert into users values (?, ?, ?, ?)";

        //몇개가 될지는 모르겠지만 자신에 맞게 setting
        //API 입장에서 내부에서 Connection을 닫아주고 있기 때문에 고민X
        jdbcTemplate.executeUpdate(user, sql, pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });
    }


    public User findById(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "select userId, password, name, email from users where userId=?";
        return (User) jdbcTemplate.executeQuery(userId, sql, pstmt -> pstmt.setString(1, userId), new RowMapper() {

            //변경되는 부분은 API 호출자 입장에서 전달
            @Override
            public Object map(ResultSet rst) throws SQLException {
                return new User(rst.getString("userId"),
                        rst.getString("password"),
                        rst.getString("name"),
                        rst.getString("email"));
            }
        });
    }
}
