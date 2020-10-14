package by.epamtc.protsko.rentcar.service;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.user.EditUserDTO;
import by.epamtc.protsko.rentcar.bean.user.FullUserDTO;
import by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;

public interface UserService {
	
	RegistrationUserDTO authentication(String login, String password) throws UserServiceException;

	boolean registration(FullUserDTO fullUserDTO) throws UserServiceException;
	
	boolean editUserData(EditUserDTO user) throws UserServiceException;
	
	boolean deleteUser(int userId) throws UserServiceException;
	
	List<FullUserDTO> getUser(FullUserDTO userDTO) throws UserServiceException;

}
