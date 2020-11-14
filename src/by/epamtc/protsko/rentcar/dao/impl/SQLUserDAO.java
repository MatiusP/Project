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

public class SQLUserDAO implements UserDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

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

    @Override
    public User authentication(String login, String password) throws UserDAOException {
        User userData = getRegistrationUserData(login, password);

        if ((userData != null) && (userData.isDeleted())) {
            throw new UserDAOException(AUTH_USER_MESSAGE);
        }
        return userData;
    }

    @Override
    public boolean add(User user) throws UserDAOException {

        if (isLoginExist(user.getLogin())) {
            throw new UserDAOException(USER_EXISTS_MESSAGE);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER_TO_DATABASE_QUERY);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
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
