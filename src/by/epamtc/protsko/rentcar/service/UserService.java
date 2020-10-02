package by.epamtc.protsko.rentcar.service;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.User;
import by.epamtc.protsko.rentcar.bean.UserData;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

public interface UserService {
	
	User authentication(String login, String password) throws ServiceException;
	
	boolean registration(UserData userData) throws ServiceException;
	
	boolean editUserData(UserData userData) throws ServiceException;
	
	boolean deleteUser(int userId) throws ServiceException;
	
	List<User> getUser(String criteria) throws ServiceException;

}
