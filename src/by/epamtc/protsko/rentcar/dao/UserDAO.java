package by.epamtc.protsko.rentcar.dao;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.bean.user.User;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;

public interface UserDAO {

    RegistrationUserDTO authentication(String login, String password) throws DAOException;

    boolean registration(User user) throws DAOException;

    boolean editUserData(User user) throws DAOException;

    boolean deleteUser(int userId) throws DAOException;

    List<User> getUser(String criteria) throws DAOException;

}
