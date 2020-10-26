package by.epamtc.protsko.rentcar.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epamtc.protsko.rentcar.bean.user.*;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.UserDAO;
import by.epamtc.protsko.rentcar.dao.exception.UserDAOException;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;
import by.epamtc.protsko.rentcar.service.util.UserUtil;

public class UserServiceImpl implements UserService {
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private UserDAO userDAO = daoFactory.getUserDAO();
    private static final String LOGIN_OR_PASSWORD_ERROR = "Invalid login or password";
    private static final String PASSWORD_ERROR = "Current password incorrect";
    private static final String REG_FORM_FILLING_ERROR = "Registration form filling error";
    private static final String USER_NOT_FOUND_EXCEPTION = "User not found for the given id";
    private static final String USER_ACTIVE_STATUS = "ACTIVE";
    private static final String USER_DELETE_STATUS = "DELETE";

    @Override
    public RegistrationUserDTO authentication(String login, String password) throws UserServiceException {
        RegistrationUserDTO regUserData = null;

        try {
            if (!UserUtil.isAuthenticationDataValid(login, password)) {
                throw new UserServiceException(LOGIN_OR_PASSWORD_ERROR);
            }

            User authenticationData = userDAO.authentication(login, password);
            regUserData = buildRegistrationUserDTOFromUser(authenticationData);
            authenticationData = null;
        } catch (UserDAOException e) {
            throw new UserServiceException(e);
        }
        return regUserData;
    }

    @Override
    public boolean registration(FullUserDTO userData) throws UserServiceException {

        try {
            if (!UserUtil.isRegistrationFormFilled(userData)) {
                throw new UserServiceException(REG_FORM_FILLING_ERROR);
            }
            if (UserUtil.isRegistrationDataValid(userData)) {
                User user = buildUserFromRegistrationData(userData);
                return userDAO.registration(user);
            }
        } catch (UserDAOException e) {
            throw new UserServiceException(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean editUserData(EditUserDTO userForEdit) throws UserServiceException {
        String currentLogin = userForEdit.getCurrentLogin();
        String currentPassword = userForEdit.getCurrentPassword();
        String newPassword = userForEdit.getNewPassword();
        User user;

        try {
            if (UserUtil.isEditUserDataValid(userForEdit)) {
                if ((currentPassword.isEmpty()) && (newPassword.isEmpty())) {
                    user = buildUserFromEditData(userForEdit);
                    return userDAO.editUserData(user);
                }
                if (((!currentPassword.isEmpty()) && (authentication(currentLogin, currentPassword) == null))
                        || ((currentPassword.isEmpty() && !newPassword.isEmpty()))) {
                    throw new UserServiceException(PASSWORD_ERROR);
                }
                user = buildUserFromEditData(userForEdit);
                return userDAO.editUserData(user);
            }
        } catch (UserDAOException e) {
            throw new UserServiceException(e);
        }
        return false;
    }

    @Override
    public boolean deleteUser(int userId) throws UserServiceException {

        try {

            return userDAO.deleteUser(userId);
        } catch (UserDAOException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public FullUserDTO getUserById(int userId) throws UserServiceException {
        FullUserDTO user = new FullUserDTO();

        try {
            final User userById = userDAO.getUserById(userId);
            if (userById == null) {
                throw new UserServiceException(USER_NOT_FOUND_EXCEPTION);
            }

            user = buildFullUserDTOFromUser(userById);
        } catch (UserDAOException e) {
            throw new UserServiceException(e);
        }
        return user;
    }

    @Override
    public List<FullUserDTO> getUser(FullUserDTO userSearchCriteria) throws UserServiceException {
        List<FullUserDTO> usersFoundList = new ArrayList<>();
        String searchCriteria = UserUtil.createSearchUserQuery(userSearchCriteria);
        FullUserDTO foundUser;

        try {
            final List<User> foundUsers = userDAO.findUser(searchCriteria);

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
        User user = new User();

        user.setId(editUserData.getId());
        user.setLogin(editUserData.getNewLogin());
        user.setPassword(editUserData.getNewPassword());
        user.setSurname(editUserData.getSurname());
        user.setName(editUserData.getName());
        user.setPassportIdNumber(editUserData.getPassportIdNumber());
        user.setDriverLicense(editUserData.getDriverLicense());
        user.setDateOfBirth(editUserData.getDateOfBirth());
        user.seteMail(editUserData.geteMail());
        user.setPhone(editUserData.getPhone());
        if (editUserStatus.equals(USER_ACTIVE_STATUS)) {
            user.setDeleted(false);
        } else {
            user.setDeleted(true);
        }
        user.setRole(Role.valueOf(editUserData.getRole()));

        return user;
    }
}
