package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.dao.exception.UserDAOException;
import by.epamtc.protsko.rentcar.entity.user.User;

import java.util.List;

/**
 * This interface provides methods for working with user's data.
 *
 * @author Matvey Protsko
 */

public interface UserDAO {

    /**
     * Method {@code authentication} checked entered user's login and password.
     *
     * @param login    contains information about entered user's login
     * @param password contains information about entered user's password
     * @return User object which matching the entered login and password. If entered user's
     * login and (or) password does not exist in the database, method throws UserDAOException.
     * @throws UserDAOException when problems with database access occur.
     */
    User authentication(String login, String password) throws UserDAOException;

    /**
     * Method {@code add} adds a new user to database.
     *
     * @param user contains entered user's data from service layer.
     * @return true if the user was successfully added to database, false -
     * if has not been added to the database.
     * @throws UserDAOException when problems with database access occur.
     */
    boolean add(User user) throws UserDAOException;

    /**
     * Method {@code edit} edits user user's data in database.
     *
     * @param user contains entered new user's data from service layer.
     * @return true if the user's data was successfully edited in database, false -
     * if has not been edited.
     * @throws UserDAOException when problems with database access occur.
     */
    boolean edit(User user) throws UserDAOException;

    /**
     * Method {@code delete} removes a user from the system.
     * This method change user's parameter isDeleted from false to true.
     *
     * @param userId - id of the user we want to remove from the system.
     * @return true if the user was successfully removed from system.
     * @throws UserDAOException when problems with database access occur.
     */
    boolean delete(int userId) throws UserDAOException;

    /**
     * Method {@code findById} finds a user by user id.
     *
     * @param userId - id of the user we want to find in the system.
     * @return User object which id matches userId parameter value.
     * @throws UserDAOException when problems with database access occur.
     */
    User findById(int userId) throws UserDAOException;

    /**
     * Method {@code findBySearchCriteria} finds users by search criteria.
     *
     * @param searchCriteria - criteria that can contains one or more user's data parameters.
     * @return List of users whose data match the search criteria.
     * @throws UserDAOException when problems with database access occur.
     */
    List<User> findBySearchCriteria(String searchCriteria) throws UserDAOException;
}
