package by.epamtc.protsko.rentcar.service;

import java.util.List;

import by.epamtc.protsko.rentcar.dto.EditUserDTO;
import by.epamtc.protsko.rentcar.dto.FullUserDTO;
import by.epamtc.protsko.rentcar.dto.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;

/**
 * This interface provides methods for user data business logic
 * and providing access to user's information.
 *
 * @author Matvey Protsko
 */

public interface UserService {

    /**
     * Method {@code authentication} provides validation entered login and password
     * and access to user's registration information.
     *
     * @param login    contains information about entered user's login.
     * @param password contains information about entered user's password.
     * @return RegistrationUserDTO {@link RegistrationUserDTO} object which contains
     * the user's registration data according to the entered login and password.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    RegistrationUserDTO authentication(String login, String password) throws UserServiceException;

    /**
     * Method {@code add} provides validation entered user's data from
     * registration form.
     *
     * @param fullUserDTO contains all entered user's registration information.
     * @return true if entered user's data is valid and has been adding to the database.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    boolean add(FullUserDTO fullUserDTO) throws UserServiceException;

    /**
     * Method {@code edit} provides validation entered new user's profile data from
     * edit user data form.
     *
     * @param user {@link EditUserDTO} object, which contains new user's profile information.
     * @return true if entered new user's data is valid and has been adding to the database.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    boolean edit(EditUserDTO user) throws UserServiceException;

    /**
     * Method {@code delete} provides business logic for removes a user from the system.
     *
     * @param userId - id of the user we want to remove from the system.
     * @return true if the user was successfully removed from system.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    boolean delete(int userId) throws UserServiceException;

    /**
     * Method {@code findByUserId} provides business logic for
     * finding a user by user id.
     *
     * @param userId - id of the user we want to find in the system.
     * @return {@link FullUserDTO} object which id matches userId parameter value.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    FullUserDTO findByUserId(int userId) throws UserServiceException;

    /**
     * Method {@code findByCriteria} provides validation and business
     * logic for finding users by search criteria.
     *
     * @param criteria - criteria that can contains one or more user's data parameters.
     * @return List of {@link FullUserDTO} objects.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    List<FullUserDTO> findByCriteria(FullUserDTO criteria) throws UserServiceException;
}
