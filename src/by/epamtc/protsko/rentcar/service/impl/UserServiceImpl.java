package by.epamtc.protsko.rentcar.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epamtc.protsko.rentcar.bean.user.EditUserDTO;
import by.epamtc.protsko.rentcar.bean.user.FullUserDTO;
import by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.bean.user.User;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.UserDAO;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;
import by.epamtc.protsko.rentcar.service.util.UserUtil;

public class UserServiceImpl implements UserService {
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private UserDAO userDAO = daoFactory.getUserDAO();
    private static final String LOGIN_OR_PASSWORD_ERROR = "Incorrect login or password";
    private static final String PASSWORD_ERROR = "Incorrect current password";
    private static final String REG_FORM_FILLING_ERROR = "Registration form filling error";

    @Override
    public RegistrationUserDTO authentication(String login, String password) throws ServiceException {
        RegistrationUserDTO regUserData = null;

        try {
            if (!UserUtil.isAuthenticationDataValid(login, password)) {
                throw new ServiceException(LOGIN_OR_PASSWORD_ERROR);
            }

            User authenticationData = userDAO.authentication(login, password);
            if (authenticationData != null) {
                regUserData = buildRegistrationUserDTOFromUser(authenticationData);
                authenticationData = null;

                return regUserData;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return regUserData;
    }


    @Override
    public boolean registration(FullUserDTO userData) throws ServiceException {
        User user;

        try {
            if (!isRegistrationFormFilled(userData)) {
                throw new ServiceException(REG_FORM_FILLING_ERROR);
            }

            if (UserUtil.isRegistrationDataValid(userData)) {
                user = buildUserFromRegistrationData(userData);

                return userDAO.registration(user);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return false;
    }


    @Override
    public boolean editUserData(EditUserDTO userForEdit) throws ServiceException {
        String currentUserLogin = userForEdit.getCurrentLogin();
        String currentUserPassword = userForEdit.getCurrentPassword();
        String newUserPassword = userForEdit.getNewPassword();
        User user;

        try {
            if (!currentUserPassword.isEmpty()) {
                User authentication = userDAO.authentication(currentUserLogin, currentUserPassword);
                RegistrationUserDTO authenticationData = buildRegistrationUserDTOFromUser(authentication);
                authentication = null;

                if (authenticationData != null) {
                    user = buildUserFromEditData(userForEdit);
                    return userDAO.editUserData(user);
                }
            } else if ((currentUserPassword.isEmpty()) && (newUserPassword.isEmpty())) {

                user = buildUserFromEditData(userForEdit);
                return userDAO.editUserData(user);
            }
            throw new ServiceException(PASSWORD_ERROR);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteUser(int userId) throws ServiceException {

        try {
            return userDAO.deleteUser(userId);
        } catch (DAOException e) {
            throw new ServiceException("Delete user error", e);
        }
    }

    @Override
    public List<FullUserDTO> getUser(FullUserDTO userSearchCriteria) throws ServiceException {
        List<FullUserDTO> usersFoundList = new ArrayList<>();
        FullUserDTO foundUser;
        String searchCriteria = UserUtil.createSearchUserQuery(userSearchCriteria);

        try {
            final List<User> foundUsers = userDAO.findUser(searchCriteria);

            for (User user : foundUsers) {
                foundUser = buildFullUserData(user);
                usersFoundList.add(foundUser);
            }
        } catch (DAOException e) {
            //logger
            throw new ServiceException(e);
        }
        return usersFoundList;
    }

    private boolean isRegistrationFormFilled(FullUserDTO userData) {
        String userLogin = userData.getLogin();
        String userPassword = userData.getPassword();
        String userSurname = userData.getSurname();
        String userName = userData.getName();
        String userPassportID = userData.getPassportIdNumber();
        String userDriverLicense = userData.getDriverLicense();
        String userDateOfBirth = String.valueOf(userData.getDateOfBirth());
        String userEMail = userData.geteMail();
        String userPhone = userData.getPhone();

        boolean isLoginFilled = ((userLogin != null) && (!userLogin.isEmpty()));
        boolean isPasswordFilled = ((userPassword != null) && (!userPassword.isEmpty()));
        boolean isSurnameFilled = ((userSurname != null) && (!userSurname.isEmpty()));
        boolean isNameFilled = ((userName != null) && (!userName.isEmpty()));
        boolean isPassportIDFilled = ((userPassportID != null) && (!userPassportID.isEmpty()));
        boolean isDriverLicenseFilled = ((userDriverLicense != null) && (!userDriverLicense.isEmpty()));
        boolean isDateOfBirthFilled = ((userDateOfBirth != null) && (!userDateOfBirth.isEmpty()));
        boolean isEMailFilled = ((userEMail != null) && (!userEMail.isEmpty()));
        boolean isPhoneFilled = ((userPhone != null) && (!userPhone.isEmpty()));

        return (isLoginFilled && isPasswordFilled && isSurnameFilled && isNameFilled && isPassportIDFilled
                && isDriverLicenseFilled && isDateOfBirthFilled && isEMailFilled && isPhoneFilled);
    }

    private FullUserDTO buildFullUserData(User user) {
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
        fullUserData.setRole(user.getRole());

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
        registrationUserData.setRole(user.getRole());

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
        user.setRole(editUserData.getRole());

        return user;
    }

//    private User buildUserFromFullUserData(FullUserDTO fullUserDTO) {
//        User user = new User();
//
//        user.setId(fullUserDTO.getId());
//        user.setLogin(fullUserDTO.getLogin());
//        user.setPassword(fullUserDTO.getPassword());
//        user.setSurname(fullUserDTO.getSurname());
//        user.setName(fullUserDTO.getName());
//        user.setPassportIdNumber(fullUserDTO.getPassportIdNumber());
//        user.setDriverLicense(fullUserDTO.getDriverLicense());
//        user.setDateOfBirth(fullUserDTO.getDateOfBirth());
//        user.seteMail(fullUserDTO.geteMail());
//        user.setPhone(fullUserDTO.getPhone());
//        user.setRole(fullUserDTO.getRole());
//
//        return user;
//    }
}
