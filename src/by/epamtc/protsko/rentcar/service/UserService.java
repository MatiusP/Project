package by.epamtc.protsko.rentcar.service;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.EditUserDTO;
import by.epamtc.protsko.rentcar.bean.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.bean.User;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

public interface UserService {
	
	RegistrationUserDTO authentication(String login, String password) throws ServiceException;
	
	boolean registration(User user) throws ServiceException;
	
	boolean editUserData(EditUserDTO user) throws ServiceException;
	
	boolean deleteUser(int userId) throws ServiceException;
	
	List<RegistrationUserDTO> getUser(String criteria) throws ServiceException;

}
