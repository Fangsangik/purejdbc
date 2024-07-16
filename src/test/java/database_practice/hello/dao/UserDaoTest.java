package database_practice.hello.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    @BeforeEach
    //test 코드 수행 하기전 실행
    void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        //해당 testCode 수행하기 전에 읽어서 table을 형성
        populator.addScript(new ClassPathResource("db_schema.sql"));
        //ConnectionManager -> dataSource를 받아오기 위함
        DatabasePopulatorUtils.execute(populator, ConnectionManger.getDataSource());
    }

    @Test
    void createTest() throws SQLException {
        UserDao userDao = new UserDao();
        userDao.create(new User("id", "password", "name", "email"));

        User user = userDao.findById("id");
        assertThat(user).isEqualTo(new User("id", "password", "name", "email"));

    }
}
