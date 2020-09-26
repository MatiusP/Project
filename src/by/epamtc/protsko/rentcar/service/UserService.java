package by.epamtc.protsko.rentcar.service;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.User;
import by.epamtc.protsko.rentcar.bean.UserDTO;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

public interface UserService {
	
	User authentication(String login, String password) throws ServiceException;
	
	boolean registration(UserDTO userDTO) throws ServiceException;
	
	boolean editUserData(UserDTO userDTO) throws ServiceException;
	
	boolean deleteUser(int userId) throws ServiceException;
	
	List<User> getUser(String criteria) throws ServiceException;

}
