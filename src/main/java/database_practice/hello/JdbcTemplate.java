package database_practice.hello;

import database_practice.hello.dao.ConnectionManger;
import database_practice.hello.dao.PrepareStatementSetter;
import database_practice.hello.dao.RowMapper;
import database_practice.hello.dao.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
    public void executeUpdate(User user, String sql, PrepareStatementSetter pstsetter) throws SQLException {
        // String sql -> 외부로 부터 받아오면 변경되는 부분 해결
        Connection connection = null;
        PreparedStatement pstmt = null;

        //PreparedStatement를 외부로 부터 받아오려면, Connection을 외부로 부터 PrepareStatement 연결 해서 가져와야 한다.
        //Setting 된 부분만 외부로 부터 받아옴
        try {
            connection = ConnectionManger.getConnection();
            pstmt = connection.prepareStatement(sql);

            pstsetter.setter(pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    //Object로 설정한 이유??
    //-> User로 설정하면 존속이 걸리기 때문
    public Object executeQuery(String userId, String sql, PrepareStatementSetter pstsetter, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        //조회된 값을 받아올 것
        ResultSet rst = null;

        try {
            con = ConnectionManger.getConnection();
            pstmt = con.prepareStatement(sql);
            pstsetter.setter(pstmt);

            rst = pstmt.executeQuery();

            Object obj = null;
            if (rst.next()) {
                return rowMapper.map(rst);
            }
            return obj;
        } finally {
            //역순으로 닫아준다.
            if (rst != null) {
                rst.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
}
