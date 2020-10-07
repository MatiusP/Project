package by.epamtc.protsko.rentcar.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
import by.epamtc.protsko.rentcar.service.validator.UserServiceValidator;

public class UserServiceImpl implements UserService {
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private UserDAO userDAO = daoFactory.getUserDAO();
    private UserServiceValidator userServiceValidator = new UserServiceValidator();
    private static final String LOGIN_OR_PASSWORD_ERROR = "Incorrect login or password";
    private static final String PASSWORD_ERROR = "Incorrect current password";
    private static final String REG_FORM_FILLING_ERROR = "Registration form filling error";
    private static final String NO_USERS_FOUND = "No users found by the specified criteria";

    @Override
    public RegistrationUserDTO authentication(String login, String password) throws ServiceException {
        RegistrationUserDTO userRegistrationData;
        try {
            if ((!login.isEmpty()) && (!password.isEmpty())) {
                userRegistrationData = userDAO.authentication(login, password);
            } else {
                throw new ServiceException(LOGIN_OR_PASSWORD_ERROR);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userRegistrationData;
    }

    @Override
    public boolean registration(FullUserDTO userData) throws ServiceException {
        User user;
        try {
            if (userServiceValidator.isRegistrationDataFilled(userData)) {
                user = buildUserFromRegistrationData(userData);

                return userDAO.registration(user);
            } else {
                throw new ServiceException(REG_FORM_FILLING_ERROR);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean editUserData(EditUserDTO userForEdit) throws ServiceException {
        String currentUserLogin = userForEdit.getCurrentLogin();
        String currentUserPassword = userForEdit.getCurrentPassword();
        String newUserPassword = userForEdit.getNewPassword();
        User user;

        try {
            if (!currentUserPassword.isEmpty()) {
                RegistrationUserDTO authentication = userDAO.authentication(currentUserLogin, currentUserPassword);

                if (authentication != null) {
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<FullUserDTO> getUser(FullUserDTO userSearchCriteria) throws ServiceException {
        List<FullUserDTO> usersFoundList = new ArrayList<>();
        FullUserDTO foundUser;

        try {
            final List<User> foundUsers = userDAO.getUser(buildUserFromFullUserDTO(userSearchCriteria));

            for (User user : foundUsers) {
                foundUser = buildFullUserData(user);
                usersFoundList.add(foundUser);
            }
        } catch (DAOException e) {
            //logger
            e.printStackTrace();
        }
        if (usersFoundList.isEmpty()) {
            throw new ServiceException(NO_USERS_FOUND);
        }
        return usersFoundList;
    }

    @Override
    public List<FullUserDTO> getAllUsers() throws ServiceException {
        List<FullUserDTO> usersList = new ArrayList<>();
        FullUserDTO fullUserData;

        try {
            List<User> users = userDAO.getAllUsers();

            for (User user : users) {
                fullUserData = buildFullUserData(user);
                usersList.add(fullUserData);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return usersList;
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
        user.setDateOfBirth(LocalDate.parse(editUserData.getDateOfBirth()));
        user.seteMail(editUserData.geteMail());
        user.setPhone(editUserData.getPhone());
        user.setRole(editUserData.getRole());

        return user;
    }

    private User buildUserFromFullUserDTO(FullUserDTO fullUserDTO) {
        User user = new User();

        user.setId(fullUserDTO.getId());
        user.setLogin(fullUserDTO.getLogin());
        user.setPassword(fullUserDTO.getPassword());
        user.setSurname(fullUserDTO.getSurname());
        user.setName(fullUserDTO.getName());
        user.setPassportIdNumber(fullUserDTO.getPassportIdNumber());
        user.setDriverLicense(fullUserDTO.getDriverLicense());
        user.setDateOfBirth(fullUserDTO.getDateOfBirth());
        user.seteMail(fullUserDTO.geteMail());
        user.setPhone(fullUserDTO.getPhone());
        user.setRole(fullUserDTO.getRole());

        return user;
    }

}
