package by.epamtc.protsko.rentcar.service.impl;

import java.time.LocalDate;
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
    public boolean editUserData(EditUserDTO editUserData) throws ServiceException {
        String currentUserLogin = editUserData.getCurrentLogin();
        String currentUserPassword = editUserData.getCurrentPassword();
        String newUserPassword = editUserData.getNewPassword();
        User user;

        try {
            if (!currentUserPassword.isEmpty()) {
                RegistrationUserDTO authentication = userDAO.authentication(currentUserLogin, currentUserPassword);

                if (authentication != null) {
                    user = buildUserFromEditData(editUserData);
                    return userDAO.editUserData(user);
                }
            } else if ((currentUserPassword.isEmpty()) && (newUserPassword.isEmpty())) {

                user = buildUserFromEditData(editUserData);
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
    public List<RegistrationUserDTO> getUser(String criteria) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
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
}
