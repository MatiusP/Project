package by.epamtc.protsko.rentcar.dao;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.user.User;
import by.epamtc.protsko.rentcar.dao.exception.UserDAOException;

public interface UserDAO {

    User authentication(String login, String password) throws UserDAOException;

    boolean registration(User user) throws UserDAOException;

    boolean editUserData(User user) throws UserDAOException;

    boolean deleteUser(int userId) throws UserDAOException;

    User getUserById(int userId) throws UserDAOException;

    List<User> findUser(String searchCriteria) throws UserDAOException;
}
