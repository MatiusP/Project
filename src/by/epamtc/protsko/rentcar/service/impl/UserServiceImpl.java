package by.epamtc.protsko.rentcar.service.impl;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.UserRegistrationDTO;
import by.epamtc.protsko.rentcar.bean.UserData;
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
    private static final String REG_FORM_FILLING_ERROR = "Registration form filling error";

    @Override
    public UserRegistrationDTO authentication(String login, String password) throws ServiceException {
        UserRegistrationDTO user;

        try {
            if ((!login.isEmpty()) && (!password.isEmpty())) {
                user = userDAO.authentication(login, password);
            } else {
                throw new ServiceException(LOGIN_OR_PASSWORD_ERROR);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public boolean registration(UserData userData) throws ServiceException {
        try {
            if (userServiceValidator.isRegistrationDataFilled(userData)) {
                return userDAO.registration(userData);
            } else {
                throw new ServiceException(REG_FORM_FILLING_ERROR);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean editUserData(UserData userData) throws ServiceException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteUser(int userId) throws ServiceException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<UserRegistrationDTO> getUser(String criteria) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }
}
