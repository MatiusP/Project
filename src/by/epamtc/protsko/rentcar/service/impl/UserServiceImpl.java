package by.epamtc.protsko.rentcar.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epamtc.protsko.rentcar.entity.user.*;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.UserDAO;
import by.epamtc.protsko.rentcar.dao.exception.UserDAOException;
import by.epamtc.protsko.rentcar.dto.EditUserDTO;
import by.epamtc.protsko.rentcar.dto.FullUserDTO;
import by.epamtc.protsko.rentcar.dto.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;
import by.epamtc.protsko.rentcar.service.util.UserUtil;

/**
 * This class implementation of {@link UserService}. Methods execute business logic
 * for working with user layer.
 *
 * @author Matvey Protsko
 */

public class UserServiceImpl implements UserService {
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private UserDAO userDAO = daoFactory.getUserDAO();
    private static final String LOGIN_OR_PASSWORD_ERROR = "Invalid login or password";
    private static final String PASSWORD_ERROR = "Current password incorrect";
    private static final String REG_FORM_FILLING_ERROR = "Registration form filling error";
    private static final String USER_NOT_FOUND_EXCEPTION = "User not found for the given id";
    private static final String USER_ACTIVE_STATUS = "ACTIVE";
    private static final String USER_DELETE_STATUS = "DELETED";
    private static final String NEW_LOGIN_MARK = "#";

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
    @Override
    public RegistrationUserDTO authentication(String login, String password) throws UserServiceException {
        RegistrationUserDTO regUserData = null;

        try {
            if (!UserUtil.isAuthenticationDataValid(login, password)) {
                throw new UserServiceException(LOGIN_OR_PASSWORD_ERROR);
            }

            User authenticationData = userDAO.authentication(login, password);
            if (authenticationData != null) {
                regUserData = buildRegistrationUserDTOFromUser(authenticationData);
                authenticationData = null;
            }
        } catch (UserDAOException e) {
            throw new UserServiceException(e);
        }
        return regUserData;
    }

    /**
     * Method {@code add} provides validation entered user's data from
     * registration form and adding a new user.
     *
     * @param userData contains all entered user's registration information.
     * @return true if entered user's data is valid and has been adding to the database.
     * Otherwise method return false.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    @Override
    public boolean add(FullUserDTO userData) throws UserServiceException {

        try {
            if (!UserUtil.isRegistrationFormFilled(userData)) {
                throw new UserServiceException(REG_FORM_FILLING_ERROR);
            }
            if (UserUtil.isRegistrationDataValid(userData)) {
                User user = buildUserFromRegistrationData(userData);
                return userDAO.add(user);
            }
        } catch (UserDAOException e) {
            throw new UserServiceException(e.getMessage(), e);
        }
        return false;
    }

    /**
     * Method {@code edit} provides validation entered new user's profile data from
     * edit user data form and updating user's data.
     *
     * @param userForEdit {@link EditUserDTO} object, which contains new user's profile information.
     * @return true if entered new user's data is valid and has been adding to the database.
     * Otherwise method return false.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    @Override
    public boolean edit(EditUserDTO userForEdit) throws UserServiceException {
        String currentLogin = userForEdit.getCurrentLogin();
        String currentPassword = userForEdit.getCurrentPassword();
        String newPassword = userForEdit.getNewPassword();
        User user;

        try {
            if (UserUtil.isEditUserDataValid(userForEdit)) {
                if ((currentPassword.isEmpty()) && (newPassword.isEmpty())) {
                    user = buildUserFromEditData(userForEdit);
                    return userDAO.edit(user);
                }
                if (((!currentPassword.isEmpty()) && (authentication(currentLogin, currentPassword) == null))
                        || ((currentPassword.isEmpty() && !newPassword.isEmpty()))) {
                    throw new UserServiceException(PASSWORD_ERROR);
                }
                user = buildUserFromEditData(userForEdit);
                return userDAO.edit(user);
            }
        } catch (UserDAOException e) {
            throw new UserServiceException(e);
        }
        return false;
    }

    /**
     * Method {@code delete} provides business logic for removes a user from the system.
     *
     * @param userId - id of the user we want to remove from the system.
     * @return true if the user was successfully removed from system.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    @Override
    public boolean delete(int userId) throws UserServiceException {
        try {
            return userDAO.delete(userId);
        } catch (UserDAOException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Method {@code findByUserId} provides business logic for
     * finding a user by user id.
     *
     * @param userId - id of the user we want to find in the system.
     * @return {@link FullUserDTO} object which id matches userId parameter value.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    @Override
    public FullUserDTO findByUserId(int userId) throws UserServiceException {
        FullUserDTO user = new FullUserDTO();

        try {
            final User userById = userDAO.findById(userId);
            if (userById == null) {
                throw new UserServiceException(USER_NOT_FOUND_EXCEPTION);
            }

            user = buildFullUserDTOFromUser(userById);
        } catch (UserDAOException e) {
            throw new UserServiceException(e);
        }
        return user;
    }

    /**
     * Method {@code findByCriteria} provides validation and business
     * logic for finding users by search criteria.
     *
     * @param criteria - criteria that can contains one or more user's data parameters.
     * @return List of {@link FullUserDTO} objects.
     * @throws UserServiceException when problems in UserDAO {@code UserDAO} layer or
     *                              in business logic.
     */
    @Override
    public List<FullUserDTO> findByCriteria(FullUserDTO criteria) throws UserServiceException {
        List<FullUserDTO> usersFoundList = new ArrayList<>();
        String searchCriteria = UserUtil.createSearchUserQuery(criteria);
        FullUserDTO foundUser;

        try {
            final List<User> foundUsers = userDAO.findBySearchCriteria(searchCriteria);

            for (User user : foundUsers) {
                foundUser = buildFullUserDTOFromUser(user);
                usersFoundList.add(foundUser);
            }
        } catch (UserDAOException e) {
            throw new UserServiceException(e);
        }
        return usersFoundList;
    }

    private FullUserDTO buildFullUserDTOFromUser(User user) {
        final boolean isUserDeleted = user.isDeleted();
        FullUserDTO fullUserData = new FullUserDTO();

        fullUserData.setId(user.getId());
        fullUserData.setLogin(user.getLogin());
        fullUserData.setPassword(user.getPassword());
        fullUserData.setSurname(user.getSurname());
        fullUserData.setName(user.getName());
        fullUserData.setPassportIdNumber(user.getPassportIdNumber());
        fullUserData.setDriverLicense(user.getDriverLicense());
        fullUserData.setDateOfBirth(user.getDateOfBirth());
        fullUserData.seteMail(user.geteMail());
        fullUserData.setPhone(user.getPhone());
        if (isUserDeleted) {
            fullUserData.setStatus(USER_DELETE_STATUS);
        } else {
            fullUserData.setStatus(USER_ACTIVE_STATUS);
        }
        fullUserData.setRole(user.getRole().toString());

        return fullUserData;
    }

    private RegistrationUserDTO buildRegistrationUserDTOFromUser(User user) {
        RegistrationUserDTO registrationUserData = new RegistrationUserDTO();

        registrationUserData.setId(user.getId());
        registrationUserData.setSurname(user.getSurname());
        registrationUserData.setName(user.getName());
        registrationUserData.setPassportIdNumber(user.getPassportIdNumber());
        registrationUserData.setDriverLicense(user.getDriverLicense());
        registrationUserData.setDateOfBirth(user.getDateOfBirth());
        registrationUserData.seteMail(user.geteMail());
        registrationUserData.setPhone(user.getPhone());
        registrationUserData.setRole(user.getRole().toString());

        return registrationUserData;
    }

    private User buildUserFromRegistrationData(FullUserDTO fullUserDTO) {
        User user = new User();

        user.setLogin(fullUserDTO.getLogin());
        user.setPassword(fullUserDTO.getPassword());
        user.setSurname(fullUserDTO.getSurname());
        user.setName(fullUserDTO.getName());
        user.setPassportIdNumber(fullUserDTO.getPassportIdNumber());
        user.setDriverLicense(fullUserDTO.getDriverLicense());
        user.setDateOfBirth(fullUserDTO.getDateOfBirth());
        user.seteMail(fullUserDTO.geteMail());
        user.setPhone(fullUserDTO.getPhone());

        return user;
    }

    private User buildUserFromEditData(EditUserDTO editUserData) {
        final String editUserStatus = editUserData.getStatus();
        String newUserLogin = editUserData.getNewLogin();
        User user = new User();

        if (!editUserData.getCurrentLogin().equals(newUserLogin)) {
            newUserLogin = NEW_LOGIN_MARK + newUserLogin;
        }

        user.setId(editUserData.getId());
        user.setLogin(newUserLogin);
        user.setPassword(editUserData.getNewPassword());
        user.setSurname(editUserData.getSurname());
        user.setName(editUserData.getName());
        user.setPassportIdNumber(editUserData.getPassportIdNumber());
        user.setDriverLicense(editUserData.getDriverLicense());
        user.setDateOfBirth(editUserData.getDateOfBirth());
        user.seteMail(editUserData.geteMail());
        user.setPhone(editUserData.getPhone());
        if (editUserStatus == null || editUserStatus.equals(USER_ACTIVE_STATUS)) {
            user.setDeleted(false);
        } else {
            user.setDeleted(true);
        }
        user.setRole(Role.valueOf(editUserData.getRole()));

        return user;
    }
}
