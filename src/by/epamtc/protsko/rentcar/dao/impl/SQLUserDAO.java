package by.epamtc.protsko.rentcar.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import by.epamtc.protsko.rentcar.bean.UserRegistrationDTO;
import by.epamtc.protsko.rentcar.bean.UserData;
import by.epamtc.protsko.rentcar.dao.UserDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPoolException;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;
import by.epamtc.protsko.rentcar.dao.util.UserUtil;

public class SQLUserDAO implements UserDAO {
    private final UserUtil userUtil = new UserUtil();
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private PreparedStatement preparedStatement;
    private static final String IS_USER_EXIST_QUERY = "SELECT * FROM users WHERE (login=? AND password=?)";
    private static final String INSERT_USER_TO_DATABASE_QUERY = "INSERT INTO users (login, password, surname,"
            + " name, passport_id_number, driver_license, date_of_birth, e_mail, phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String EDIT_USER_DATA_QUERY = "UPDATE users SET login=?, password=?, surname=?, name=?, passport_id_number=?, " +
            "driver_license=?, date_of_birth=?, e_mail=?, phone=?, role_id=? WHERE id=?";


    //Authentication method
    @Override
    public UserRegistrationDTO authentication(String login, String password) throws DAOException {
        UserRegistrationDTO user = null;

        if (userUtil.isAuthenticationDataValid(login, password)) {
            user = getUserFromDatabase(login, password);
        }
        return user;
    }


    //Registration method
    @Override
    public boolean registration(UserData userData) throws DAOException {
        String userLogin = userData.getLogin();
        String userPassword = userData.getPassword();

        if (getUserFromDatabase(userLogin, userPassword) != null) {
            throw new DAOException("Sorry, but the login you entered exists. Try a different login.");
        }

        if (userUtil.isRegistrationDataValid(userData)) {

            Connection connection = null;
            try {
                connection = connectionPool.takeConnection();
                preparedStatement = connection.prepareStatement(INSERT_USER_TO_DATABASE_QUERY);

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
                if (preparedStatement != null) {
                    closerStatement(preparedStatement);
                }
                closeConnection(connection);
            }
            return true;
        }
        return false;
    }


    //Edit user data method
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
    public List<UserRegistrationDTO> getUser(String criteria) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    private UserRegistrationDTO getUserFromDatabase(String login, String password) throws DAOException {
        UserRegistrationDTO user = null;
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(IS_USER_EXIST_QUERY);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new UserRegistrationDTO();
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
            if (preparedStatement != null) {
                closerStatement(preparedStatement);
            }
            if (resultSet != null) {
                closeResultSet(resultSet);
            }
            closeConnection(connection);
        }
        return user;
    }

    private void closeConnection(Connection connection) throws DAOException {
        try {
            connectionPool.returnConnection(connection);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Error return connection");
        }
    }

    private void closerStatement(Statement statement) throws DAOException {
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


//    class Main {
//
//        public static void main(String[] args) throws DAOException {
//            SQLUserDAO sqlUserDAO = new SQLUserDAO();
//
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate localDate = LocalDate.parse("1901-01-01", dtf);
//
//            Date date = Date.valueOf(localDate);
//
//
//            UserData userData = new UserData();
//            userData.setLogin("MotorR1");
//            userData.setPassword("MotorR1248%1");
//            userData.setSurname("Процко1");
//            userData.setName("Матвей1");
//            userData.setPassportIdNumber("3281281K004BB");
//            userData.setDriverLicense("OO123132");
//            userData.setDateOfBirth(localDate);
//            userData.seteMail("PM@PM.by");
//            userData.setPhone("+375555555555");
//            userData.setRole(2);
//            userData.setId(3);
//
//
//            System.out.println(sqlUserDAO.editUserData(userData));
//        }
//
//    }
