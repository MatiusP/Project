package by.epamtc.protsko.rentcar.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.protsko.rentcar.entity.user.Role;
import by.epamtc.protsko.rentcar.entity.user.User;
import by.epamtc.protsko.rentcar.dao.UserDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.UserDAOException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class implementation of {@link UserDAO}. Methods use
 * {@link ConnectionPool} to connect to database and work with users.
 *
 * @author Matvey Protsko
 */

public class SQLUserDAO implements UserDAO {
    /**
     * A single instance of the class {@code ConnectionPool} (pattern Singleton)
     */
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(true);

    private static final String AUTH_USER_MESSAGE = "Incorrect login or password";
    private static final String USER_EXISTS_MESSAGE = "Login exists";
    private static final String SAVE_USER_ERROR_MESSAGE = "Error while saving new user";
    private static final String EDIT_USER_ERROR_MESSAGE = "Error while editing user";
    private static final String DELETE_USER_ERROR_MESSAGE = "Error while deleting user";
    private static final String FIND_USER_ERROR_MESSAGE = "Error while finding user";
    private static final String GET_USER_ERROR_MESSAGE = "Error while getting user by id";
    private static final String READ_RESULT_SET_ERROR_MESSAGE = "Error while reading result set";
    private static final String NEW_LOGIN_MARK = "#";

    private static final String IS_USER_EXISTS_QUERY = "SELECT * FROM users WHERE login=?";
    private static final String INSERT_USER_TO_DATABASE_QUERY = "INSERT INTO users" +
            "(login, password, surname, name, passport_id_number," +
            " driver_license, date_of_birth, e_mail, phone)" +
            " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String EDIT_USER_DATA_QUERY = "UPDATE users SET" +
            " login=?, password=coalesce(?, password), surname=?, name=?, passport_id_number=?, " +
            " driver_license=?, date_of_birth=?, e_mail=?, phone=?, is_deleted=?, role_id=? WHERE id=?";
    private static final String DELETE_USER_QUERY = "UPDATE users SET is_deleted=1 WHERE id=?";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id=?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE ";

    /**
     * Method {@code authentication} checked entered user's login and password.
     *
     * @param login    - entered user's login.
     * @param password - entered user's password.
     * @return User object which matching the entered login and password. If entered user's
     * login and (or) password does not exist in the database, or if user's parameter isDeleted
     * equals false, this method throws UserDAOException.
     * @throws UserDAOException when problems with database access occur.
     */
    @Override
    public User authentication(String login, String password) throws UserDAOException {
        User userData = getRegistrationUserData(login, password);

        if ((userData != null) && (userData.isDeleted())) {
            throw new UserDAOException(AUTH_USER_MESSAGE);
        }
        return userData;
    }

    /**
     * Method {@code add} adds a new user to database.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement})
     * and add a new user to database.
     *
     * @param user contains entered user's data from service layer.
     * @return true if the user was successfully added to database. If user has not been
     * added to the database, this method throws UserDAOException.
     * @throws UserDAOException when problems with database access occur.
     */
    @Override
    public boolean add(User user) throws UserDAOException {

        if (isLoginExist(user.getLogin())) {
            throw new UserDAOException(USER_EXISTS_MESSAGE);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        BCryptPasswordEncoder bCryptPasswordEncoder;

        try {
            connection = connectionPool.takeConnection();
            bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashPassword = bCryptPasswordEncoder.encode(user.getPassword());
            preparedStatement = connection.prepareStatement(INSERT_USER_TO_DATABASE_QUERY);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, hashPassword);
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getPassportIdNumber());
            preparedStatement.setString(6, user.getDriverLicense());
            preparedStatement.setDate(7, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setString(8, user.geteMail());
            preparedStatement.setString(9, user.getPhone().replace(" ", ""));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UserDAOException(SAVE_USER_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return true;
    }

    /**
     * Method {@code edit} edit (update) user's data in database.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement})
     * and add a new user to database.
     *
     * @param user contains entered new user's data from service layer.
     * @return true if the user was successfully edited in database. If user has not been
     * edited in the database, this method throws UserDAOException.
     * @throws UserDAOException when problems with database access occur.
     */
    @Override
    public boolean edit(User user) throws UserDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        BCryptPasswordEncoder bCryptPasswordEncoder;
        String userLogin = user.getLogin();
        String hashPassword = null;

        if (userLogin.startsWith(NEW_LOGIN_MARK)) {
            userLogin = userLogin.substring(1);
            if (isLoginExist(userLogin)) {
                throw new UserDAOException(USER_EXISTS_MESSAGE);
            }
        }

        try {
            connection = connectionPool.takeConnection();
            if ((user.getPassword() != null) && (!user.getPassword().isEmpty())) {
                bCryptPasswordEncoder = new BCryptPasswordEncoder();
                hashPassword = bCryptPasswordEncoder.encode(user.getPassword());
            }

            preparedStatement = connection.prepareStatement(EDIT_USER_DATA_QUERY);

            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, hashPassword);
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getPassportIdNumber());
            preparedStatement.setString(6, user.getDriverLicense());
            preparedStatement.setDate(7, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setString(8, user.geteMail());
            preparedStatement.setString(9, user.getPhone().replace(" ", ""));
            preparedStatement.setBoolean(10, user.isDeleted());
            preparedStatement.setInt(11, user.getRole().ordinal() + 1);
            preparedStatement.setInt(12, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UserDAOException(EDIT_USER_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return true;
    }

    /**
     * Method {@code delete} removes a user from the system.
     * This method change user's parameter isDeleted from false to true.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement})
     * and remove a user from system.
     *
     * @param userId - id of the user we want to remove from the system.
     * @return true if the user was successfully removed from system. If user has not been
     * removed from the system, this method returns false.
     * @throws UserDAOException when problems with database access occur.
     */
    @Override
    public boolean delete(int userId) throws UserDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setInt(1, userId);

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new UserDAOException(DELETE_USER_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    /**
     * Method {@code findById} finds a user by user id.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement})
     * find and return user's data from database.
     *
     * @param userId - id of the user we want to find in the system.
     * @return User object which id matches userId parameter value.
     * @throws UserDAOException when problems with database access occur.
     */
    @Override
    public User findById(int userId) throws UserDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_ID_QUERY);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = fillUserDataFromDB(resultSet);
            }
        } catch (SQLException e) {
            throw new UserDAOException(GET_USER_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return user;
    }

    /**
     * Method {@code findBySearchCriteria} finds users by search criteria.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement})
     * find and return users list from database, whose data match the search criteria.
     *
     * @param searchCriteria - criteria that can contains one or more user's data parameters.
     * @return List of users whose data match the search criteria.
     * @throws UserDAOException when problems with database access occur.
     */
    @Override
    public List<User> findBySearchCriteria(String searchCriteria) throws UserDAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        List<User> users = new ArrayList<>();
        User user;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();

            if (!searchCriteria.isEmpty()) {
                resultSet = statement.executeQuery(FIND_USER_QUERY + "(" + searchCriteria + ")");
            } else {
                resultSet = statement.executeQuery(GET_ALL_USERS_QUERY);
            }

            while (resultSet.next()) {
                user = fillUserDataFromDB(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new UserDAOException(FIND_USER_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, statement, resultSet);
            }
        }
        return users;
    }

    /**
     * Method {@code getRegistrationUserData} is additional method, which used
     * authentication {@code authentication} method for creating User object from
     * database.
     * <p>
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement}) and
     * ResultSet object {@code ResultSet} and search user in database.
     * If the user is found, this method create and return founded user like User object.
     *
     * @param login    - entered user's login.
     * @param password - entered user's password.
     * @return User object which contains user's data by entered login and password.
     * @throws UserDAOException when problems with database access occur.
     */
    private User getRegistrationUserData(String login, String password) throws UserDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = null;

        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(IS_USER_EXISTS_QUERY);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String userHashPassword = resultSet.getString(3);
                if (bCryptPasswordEncoder.matches(password, userHashPassword)) {
                    user = fillUserDataFromDB(resultSet);
                }
            } else {
                throw new UserDAOException(AUTH_USER_MESSAGE);
            }
        } catch (SQLException e) {
            throw new UserDAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return user;
    }

    /**
     * Method {@code isLoginExist} is additional method, which used
     * add {@code add} and edit {@code edit} methods to check the login
     * for existence in the database.
     * <p>
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement}) and
     * ResultSet object {@code ResultSet} and search entered login in database.
     *
     * @param login - entered user's login for check by existence in the database.
     * @return false if entered login has not exists in database. If entered login exists
     * in database, this method throws UserDAOException.
     * @throws UserDAOException when problems with database access occur.
     */
    private boolean isLoginExist(String login) throws UserDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(IS_USER_EXISTS_QUERY);
            preparedStatement.setString(1, login);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new UserDAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return false;
    }

    /**
     * Method {@code fillUserDataFromDB} is additional method which used
     * for build user from database data like User object.
     *
     * @param resultSet - resultSet, that contains user's data from database.
     * @return User object which contains user's data from database.
     * @throws UserDAOException when problems with database access occur.
     */
    private User fillUserDataFromDB(ResultSet resultSet) throws UserDAOException {
        User user = new User();

        try {
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
        } catch (SQLException e) {
            throw new UserDAOException(READ_RESULT_SET_ERROR_MESSAGE, e);
        }
        return user;
    }
}
