package by.epamtc.protsko.rentcar.dao;

import java.util.List;

import by.epamtc.protsko.rentcar.entity.user.User;
import by.epamtc.protsko.rentcar.dao.exception.UserDAOException;

public interface UserDAO {

    User authentication(String login, String password) throws UserDAOException;

    boolean add(User user) throws UserDAOException;

    boolean edit(User user) throws UserDAOException;

    boolean delete(int userId) throws UserDAOException;

    User findById(int userId) throws UserDAOException;

    List<User> findBySearchCriteria(String searchCriteria) throws UserDAOException;
}
