package test;

import by.epamtc.protsko.rentcar.dao.exception.UserDAOException;
import by.epamtc.protsko.rentcar.dao.impl.SQLUserDAO;
import by.epamtc.protsko.rentcar.entity.user.Role;
import by.epamtc.protsko.rentcar.entity.user.User;
import com.ibatis.common.jdbc.ScriptRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import test.testconnector.TestConnectionPool;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class SQLUserDAOTest {
    private static final TestConnectionPool testConnectionPool = TestConnectionPool.getInstance();
    private static SQLUserDAO userDAO = new SQLUserDAO();
    private static final String CREATE_TEST_RENT_CAR_DB_SCRIPT = "D://createTest_rentCar_DB-practic.sql";

    @BeforeClass
    public static void initSQLUserDAOTest() throws IOException, SQLException {
        Connection connection = testConnectionPool.takeConnection();
        Statement statement = connection.createStatement();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(CREATE_TEST_RENT_CAR_DB_SCRIPT));
        ScriptRunner scriptRunner = new ScriptRunner(connection, false, false);
        scriptRunner.runScript(bufferedReader);
        if (connection != null) {
            testConnectionPool.closeConnection(connection, statement);
        }
    }

    @Test
    public void authenticationTest() throws UserDAOException {
        final String login = "Katya";
        final String password = "Katya1%";

        User expectedUser = new User();
        expectedUser.setId(3);
        expectedUser.setLogin(login);
        expectedUser.setPassword("$2a$10$xtf.RnuctmZp688T8NlXnuwzVboBQ4J99AWOtvuQ7NyLtRQiKK1oS");
        expectedUser.setSurname("Сухая");
        expectedUser.setName("Катя");
        expectedUser.setPassportIdNumber("4369852TY1258");
        expectedUser.setDriverLicense("369TU2587");
        expectedUser.setDateOfBirth(LocalDate.parse("1996-03-28"));
        expectedUser.seteMail("kat@gmail.com");
        expectedUser.setPhone("+375297505480");
        expectedUser.setDeleted(false);
        expectedUser.setRole(Role.CLIENT);

        User actualUser = userDAO.authentication(login, password);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void authenticationExceptionTest() throws UserDAOException {
        final String incorrectLogin = "Katyaa";
        String password = "Katya1%";
        try {
            userDAO.authentication(incorrectLogin, password);
        } catch (UserDAOException e) {
            assertEquals("Incorrect login or password", e.getMessage());
        }
    }

    @Test
    public void addTest() throws UserDAOException {
        User expectedUser = new User();

        expectedUser.setLogin("Dmitry");
        expectedUser.setPassword("Dmitry1%");
        expectedUser.setSurname("Дмитрович");
        expectedUser.setName("Дмитрий");
        expectedUser.setPassportIdNumber("4369852DD1258");
        expectedUser.setDriverLicense("369DD2587");
        expectedUser.setDateOfBirth(LocalDate.parse("1995-04-25"));
        expectedUser.seteMail("dmit@gmail.com");
        expectedUser.setPhone("+375297505433");

        final boolean add = userDAO.add(expectedUser);
        assertTrue(add);
    }
}
