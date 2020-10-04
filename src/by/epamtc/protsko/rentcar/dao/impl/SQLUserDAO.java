package by.epamtc.protsko.rentcar.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import by.epamtc.protsko.rentcar.bean.User;
import by.epamtc.protsko.rentcar.bean.UserData;
import by.epamtc.protsko.rentcar.dao.UserDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPoolException;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;
import by.epamtc.protsko.rentcar.dao.util.UserUtil;

public class SQLUserDAO implements UserDAO {
    private final UserUtil userUtil = new UserUtil();
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private static final String IS_USER_EXIST = "SELECT * FROM users WHERE (login=? AND password=?)";
    private static final String INSERT_USER_TO_DATABASE = "INSERT INTO users (login, password, surname,"
            + " name, passport_id_number, driver_license, date_of_birth, e_mail, phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";




    @Override
    public User authentication(String login, String password) throws DAOException {
        User user = null;

        if (userUtil.isAuthenticationDataValid(login, password)) {
            user = getUserFromDatabase(login, password);
        }
        return user;
    }

    @Override
    public boolean registration(UserData userData) throws DAOException {
        String userLogin = userData.getLogin();
        String userPassword = userData.getPassword();

        if (getUserFromDatabase(userLogin, userPassword) != null) {
            throw new DAOException("Sorry, but the login you entered exists. Try a different login.");
        }

        if (userUtil.isRegistrationDataValid(userData)) {
            try {
                connection = connectionPool.takeConnection();
                preparedStatement = connection.prepareStatement(INSERT_USER_TO_DATABASE);

                preparedStatement.setString(1, userData.getLogin());
                preparedStatement.setString(2, userData.getPassword());
                preparedStatement.setString(3, userData.getSurname());
                preparedStatement.setString(4, userData.getName());
                preparedStatement.setString(5, userData.getPassportIdNumber());
                preparedStatement.setString(6, userData.getDriverLicense());
                preparedStatement.setDate(7, Date.valueOf(userData.getDateOfBirth()));
                preparedStatement.setString(8, userData.geteMail());
                preparedStatement.setString(9, userData.getPhone().replaceAll(" ", ""));

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                //log
                throw new DAOException("Save new user error", e);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Connection pool exception", e);
            } finally {
                closeConnection(connection, preparedStatement, resultSet);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean editUserData(UserData userData) throws DAOException {






        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteUser(int userId) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<User> getUser(String criteria) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    private User getUserFromDatabase(String login, String password) throws DAOException {
        User user = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(IS_USER_EXIST);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setSurname(resultSet.getString(4));
                user.setName(resultSet.getString(5));
                user.setPassportIdNumber(resultSet.getString(6));
                user.setDriverLicense(resultSet.getString(7));
                user.setDateOfBirth(resultSet.getDate(8).toLocalDate());
                user.seteMail(resultSet.getString(9));
                user.setPhone(resultSet.getString(10));
                user.setRole(resultSet.getInt(11));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            //logger
            throw new DAOException(e);
        } finally {
            closeConnection(connection, preparedStatement, resultSet);
        }
        return user;
    }

    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet)
            throws DAOException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DAOException("Result set close error", e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException("Statement close error", e);
            }
        }
        try {
            connectionPool.returnConnection(connection);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Error return connection");
        }
    }
}
