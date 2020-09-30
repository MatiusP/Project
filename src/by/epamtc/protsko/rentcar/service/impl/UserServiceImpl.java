package by.epamtc.protsko.rentcar.service.impl;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.User;
import by.epamtc.protsko.rentcar.bean.UserDTO;
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

	@Override
	public User authentication(String login, String password) throws ServiceException {
		User user;

		try {
			if ((!login.isEmpty()) && (!password.isEmpty())) {
				user = userDAO.authentication(login, password);
			} else {
				throw new ServiceException("Incorrect login or password");
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return user;
	}

	@Override
	public boolean registration(UserDTO userDTO) throws ServiceException {

		try {
			if (userServiceValidator.isRegistrationDataFilled(userDTO)) {
				return userDAO.registration(userDTO);
			} else {
				throw new ServiceException("Not all fields of the registration form are filled");
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean editUserData(UserDTO userDTO) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(int userId) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getUser(String criteria) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
