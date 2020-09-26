package by.epamtc.protsko.rentcar.dao;

import java.util.List;

import by.epamtc.protsko.rentcar.bean.User;
import by.epamtc.protsko.rentcar.bean.UserDTO;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;

public interface UserDAO {
	
	User authentication(String login, String password) throws DAOException;
	
	boolean registration(UserDTO userDTO) throws DAOException;
	
	boolean editUserData(UserDTO userDTO) throws DAOException;
	
	boolean deleteUser(int userId) throws DAOException;
	
	List<User> getUser(String criteria) throws DAOException;

}
