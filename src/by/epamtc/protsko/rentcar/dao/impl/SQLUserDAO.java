package by.epamtc.protsko.rentcar.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.protsko.rentcar.bean.user.User;
import by.epamtc.protsko.rentcar.dao.UserDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPoolException;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;

public class SQLUserDAO implements UserDAO {
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String USER_EXISTS_MESSAGE = "Sorry, but the login you entered exists. Try a different login.";
    private static final String SAVE_USER_ERROR_MESSAGE = "Save new user error";
    private static final String CONNECTION_ERROR_MESSAGE = "Connection pool exception";

    private static final String IS_USER_EXIST_QUERY = "SELECT * FROM users WHERE (login=? AND password=?)";
    private static final String IS_LOGIN_EXISTS_QUERY = "SELECT * FROM users WHERE login=?";
    private static final String INSERT_USER_TO_DATABASE_QUERY = "INSERT INTO users (login, password, surname,"
            + " name, passport_id_number, driver_license, date_of_birth, e_mail, phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String EDIT_FULL_USER_DATA_QUERY = "UPDATE users SET login=?, password=?, surname=?, name=?, passport_id_number=?, " +
            "driver_license=?, date_of_birth=?, e_mail=?, phone=?, role_id=? WHERE id=?";
    private static final String EDIT_USER_DATA_QUERY = "UPDATE users SET login=?, surname=?, name=?, passport_id_number=?, " +
            "driver_license=?, date_of_birth=?, e_mail=?, phone=?, role_id=? WHERE id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id=?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String GET_USER_QUERY = "SELECT * FROM users WHERE ";


    @Override
    public User authentication(String login, String password) throws DAOException {

        return getRegistrationUserData(login, password);
    }

    @Override
    public boolean registration(User user) throws DAOException {

        if (isLoginExist(user.getLogin())) {
            throw new DAOException(USER_EXISTS_MESSAGE);
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
            throw new DAOException(SAVE_USER_ERROR_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(CONNECTION_ERROR_MESSAGE, e);
        } finally {
            if (preparedStatement != null) {
                closeStatement(preparedStatement);
            }
            closeConnection(connection);
        }
        return true;
    }

    @Override
    public boolean editUserData(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            if (!user.getPassword().isEmpty()) {
                preparedStatement = connection.prepareStatement(EDIT_FULL_USER_DATA_QUERY);

                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getSurname());
                preparedStatement.setString(4, user.getName());
                preparedStatement.setString(5, user.getPassportIdNumber());
                preparedStatement.setString(6, user.getDriverLicense());
                preparedStatement.setDate(7, Date.valueOf(user.getDateOfBirth()));
                preparedStatement.setString(8, user.geteMail());
                preparedStatement.setString(9, user.getPhone().replace(" ", ""));
                preparedStatement.setInt(10, user.getRole());
                preparedStatement.setInt(11, user.getId());

                preparedStatement.executeUpdate();
            }else{
                preparedStatement = connection.prepareStatement(EDIT_USER_DATA_QUERY);

                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getPassportIdNumber());
                preparedStatement.setString(5, user.getDriverLicense());
                preparedStatement.setDate(6, Date.valueOf(user.getDateOfBirth()));
                preparedStatement.setString(7, user.geteMail());
                preparedStatement.setString(8, user.getPhone().replace(" ", ""));
                preparedStatement.setInt(9, user.getRole());
                preparedStatement.setInt(10, user.getId());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DAOException(SAVE_USER_ERROR_MESSAGE, e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(CONNECTION_ERROR_MESSAGE, e);
        } finally {
            if (preparedStatement != null) {
                closeStatement(preparedStatement);
            }
            if (resultSet != null) {
                closeResultSet(resultSet);
            }
            closeConnection(connection);
        }
        return true;
    }

    @Override
    public boolean deleteUser(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setInt(1, userId);

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                closeStatement(preparedStatement);
            }
            closeConnection(connection);
        }
        return false;
    }

    @Override
    public List<User> findUser(String searchCriteria) throws DAOException {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        List<User> users = new ArrayList<>();
        User user;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();

            if (!searchCriteria.isEmpty()) {
                resultSet = statement.executeQuery(GET_USER_QUERY + "(" + searchCriteria + ")");
            } else {
                resultSet = statement.executeQuery(GET_ALL_USERS_QUERY);
            }

            while (resultSet.next()) {
                int i = 0;
                user = new User();

                user.setId(resultSet.getInt(++i));
                user.setLogin(resultSet.getString(++i));
                user.setPassword(resultSet.getString(++i));
                user.setSurname(resultSet.getString(++i));
                user.setName(resultSet.getString(++i));
                user.setPassportIdNumber(resultSet.getString(++i));
                user.setDriverLicense(resultSet.getString(++i));
                user.setDateOfBirth(resultSet.getDate(++i).toLocalDate());
                user.seteMail(resultSet.getString(++i));
                user.setPhone(resultSet.getString(++i));
                user.setRole(resultSet.getInt(++i));

                users.add(user);
            }
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                closeStatement(statement);
            }
            if (resultSet != null) {
                closeResultSet(resultSet);
            }
            closeConnection(connection);
        }
        return users;
    }

    private User getRegistrationUserData(String login, String password) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(IS_USER_EXIST_QUERY);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
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
                user.setRole(resultSet.getInt(11));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            if (preparedStatement != null) {
                closeStatement(preparedStatement);
            }
            if (resultSet != null) {
                closeResultSet(resultSet);
            }
            closeConnection(connection);
        }
        return user;
    }

    private boolean isLoginExist(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(IS_LOGIN_EXISTS_QUERY);
            preparedStatement.setString(1, login);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } finally {
            if (preparedStatement != null) {
                closeStatement(preparedStatement);
            }
            if (resultSet != null) {
                closeResultSet(resultSet);
            }
            closeConnection(connection);
        }
        return false;
    }


    private void closeConnection(Connection connection) throws DAOException {
        try {
            connectionPool.returnConnection(connection);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Error return connection");
        }
    }

    private void closeStatement(Statement statement) throws DAOException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException("Statement close error", e);
            }
        }
    }

    private void closeResultSet(ResultSet resultSet) throws DAOException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DAOException("Result set close error", e);
            }
        }
    }
}
