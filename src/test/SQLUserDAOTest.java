package test;

import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.UserDAOException;
import by.epamtc.protsko.rentcar.dao.impl.SQLUserDAO;
import by.epamtc.protsko.rentcar.entity.user.Role;
import by.epamtc.protsko.rentcar.entity.user.User;
import com.ibatis.common.jdbc.ScriptRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAOTest {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static SQLUserDAO userDAO = new SQLUserDAO();
    private static final String CREATE_TEST_RENT_CAR_DB_SCRIPT = "D://createTest_rentCar_DB-practic.sql";

    @BeforeClass
    public static void initSQLUserDAOTest() throws IOException, SQLException {
        Connection connection = connectionPool.takeConnection();
        Statement statement = connection.createStatement();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(CREATE_TEST_RENT_CAR_DB_SCRIPT));
        ScriptRunner scriptRunner = new ScriptRunner(connection, false, false);
        scriptRunner.runScript(bufferedReader);
        if (connection != null) {
            connectionPool.closeConnection(connection, statement);
        }
    }

    @Test
    public void testingAuthenticationMethod() throws UserDAOException {
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
    public void testingAuthenticationException() throws UserDAOException {
        final String incorrectLogin = "Katyaa";
        String password = "Katya1%";
        try {
            userDAO.authentication(incorrectLogin, password);
        } catch (UserDAOException e) {
            assertEquals("Incorrect login or password", e.getMessage());
        }
    }

    @Test
    public void testingAddMethod() throws UserDAOException, SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        User expectedUser = new User();
        expectedUser.setLogin("Dmitryy");
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

        connection = connectionPool.takeConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM `rentcar_db`.`users` WHERE login='Dmitryy'");
        User actualUser = new User();

        while (resultSet.next()) {
            actualUser.setLogin(resultSet.getString("login"));
            actualUser.setPassword("Dmitry1%");
            actualUser.setSurname(resultSet.getString("surname"));
            actualUser.setName(resultSet.getString("name"));
            actualUser.setPassportIdNumber(resultSet.getString("passport_id_number"));
            actualUser.setDriverLicense(resultSet.getString("driver_license"));
            actualUser.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
            actualUser.seteMail(resultSet.getString("e_mail"));
            actualUser.setPhone(resultSet.getString("phone"));
        }
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testingEditMethod() throws UserDAOException, SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        User expectedUser = new User();

        expectedUser.setId(17);
        expectedUser.setLogin("Dzimitry");
        expectedUser.setPassword("Dmitry1%");
        expectedUser.setSurname("Дмитров");
        expectedUser.setName("Дима");
        expectedUser.setPassportIdNumber("3333333DD1111");
        expectedUser.setDriverLicense("369DD2587");
        expectedUser.setDateOfBirth(LocalDate.parse("1995-04-25"));
        expectedUser.seteMail("dmit@mail.ru");
        expectedUser.setPhone("+375297505433");
        expectedUser.setRole(Role.CLIENT);

        final boolean edit = userDAO.edit(expectedUser);
        assertTrue(edit);

        connection = connectionPool.takeConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM `rentcar_db`.`users` WHERE login='Dzimitry'");
        User actualUser = new User();

        while (resultSet.next()) {
            actualUser.setId(resultSet.getInt("id"));
            actualUser.setLogin(resultSet.getString("login"));
            actualUser.setPassword("Dmitry1%");
            actualUser.setSurname(resultSet.getString("surname"));
            actualUser.setName(resultSet.getString("name"));
            actualUser.setPassportIdNumber(resultSet.getString("passport_id_number"));
            actualUser.setDriverLicense(resultSet.getString("driver_license"));
            actualUser.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
            actualUser.seteMail(resultSet.getString("e_mail"));
            actualUser.setPhone(resultSet.getString("phone"));
            actualUser.setRole(Role.getByOrderCode(resultSet.getInt("role_id")));
        }
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testingDeleteMethod() throws UserDAOException, SQLException {
        final int userId = 17;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        final boolean delete = userDAO.delete(userId);
        assertTrue(delete);

        connection = connectionPool.takeConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM `rentcar_db`.`users` WHERE login='Dzimitry'");
        User actualUser = new User();

        if (resultSet.next()) {
            actualUser.setDeleted(resultSet.getBoolean("is_deleted"));
        }
        assertTrue(actualUser.isDeleted());
    }

    @Test
    public void testingFindByIdMethod() throws UserDAOException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User expectedUser = new User();

        expectedUser.setId(17);
        expectedUser.setLogin("Dzimitry");
        expectedUser.setPassword("Dmitry1%");
        expectedUser.setSurname("Дмитров");
        expectedUser.setName("Дима");
        expectedUser.setPassportIdNumber("3333333DD1111");
        expectedUser.setDriverLicense("369DD2587");
        expectedUser.setDateOfBirth(LocalDate.parse("1995-04-25"));
        expectedUser.seteMail("dmit@mail.ru");
        expectedUser.setPhone("+375297505433");
        expectedUser.setRole(Role.CLIENT);

        User actualUser = userDAO.findById(17);
        if (bCryptPasswordEncoder.matches(expectedUser.getPassword(), actualUser.getPassword())) {
            expectedUser.setPassword(actualUser.getPassword());
            expectedUser.setDeleted(actualUser.isDeleted());
        }

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testingFindBySearchCriteriaMethod() throws UserDAOException, SQLException {
        final String searchCriteria = " role_id=1";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user = null;
        List<User> expectedUserList = new ArrayList<>();

        List<User> actualUserList = userDAO.findBySearchCriteria(searchCriteria);

        connection = connectionPool.takeConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM `rentcar_db`.`users` WHERE (role_id=1)");

        while (resultSet.next()) {
            user = new User();

            user.setId(resultSet.getInt(1));
            user.setLogin(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setSurname(resultSet.getString(4));
            user.setName(resultSet.getString(5));
            user.setPassportIdNumber(resultSet.getString(6));
            user.setDriverLicense(resultSet.getString(7));
            user.setDateOfBirth(resultSet.getDate(8).toLocalDate());
            user.seteMail(resultSet.getString(9));
            user.setPhone(resultSet.getString(10));
            user.setDeleted(resultSet.getBoolean(11));
            user.setRole(Role.getByOrderCode(resultSet.getInt(12)));
            expectedUserList.add(user);
        }
        assertArrayEquals(expectedUserList.toArray(), actualUserList.toArray());
    }

    @AfterClass
    public static void finishTest() throws SQLException {
        Connection connection = connectionPool.takeConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE `test_rentcar_db`.`users`, `test_rentcar_db`.`roles`");
        connectionPool.dispose();
    }
}
