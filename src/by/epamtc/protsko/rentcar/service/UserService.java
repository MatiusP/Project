package by.epamtc.protsko.rentcar.service;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.user.EditUserDTO;
import by.epamtc.protsko.rentcar.bean.user.FullUserDTO;
import by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

public interface UserService {
	
	RegistrationUserDTO authentication(String login, String password) throws ServiceException;
	
	boolean registration(FullUserDTO fullUserDTO) throws ServiceException;
	
	boolean editUserData(EditUserDTO user) throws ServiceException;
	
	boolean deleteUser(int userId) throws ServiceException;
	
	List<FullUserDTO> getUser(FullUserDTO userDTO) throws ServiceException;

	List<FullUserDTO> getAllUsers() throws ServiceException;

}
